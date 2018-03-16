package org.springframework.data.elasticsearch.client.rest;

import static java.util.Optional.ofNullable;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.client.ElasticsearchServerException;
import org.springframework.data.elasticsearch.client.IndicesAPI;
import org.springframework.data.elasticsearch.client.ResourceException;
import org.springframework.data.elasticsearch.client.model.*;
import org.springframework.lang.Nullable;

public class IndicesAPIImpl implements IndicesAPI {

	private static final Logger LOG = LoggerFactory.getLogger(IndicesAPIImpl.class);
	private JsonMapper mapper;
	private RestClient nativeClient;

	public IndicesAPIImpl(JsonMapper mapper, RestClient nativeClient) {
		this.nativeClient = nativeClient;
		this.mapper = mapper;
	}

	@Override
	public boolean create(String indexName) {
		try {
			return create(indexName,
					mapper.toJson(new Settings()),
					mapper.toJson(new Mappings()));
		} catch (IOException e) {
			throw mapErrors(LOG, e);
		}
	}

	@Override
	public boolean create(String indexName, @Nullable Settings settings, @Nullable Mappings mappings) {
		try {
			return create(indexName,
					mapper.toJson(ofNullable(settings).orElse(new Settings())),
					mapper.toJson(ofNullable(mappings).orElse(new Mappings())));
		} catch (IOException e) {
			throw mapErrors(LOG, e);
		}
	}

	@Override
	public boolean create(String indexName, @Nullable String settings, @Nullable String mappings) {
		try {
			String template = "{ \"settings\": %s, \"%s\":{ \"mappings\": %s }}";
			Map<String, String> params = Collections.emptyMap();
			final String formattedString = String.format(
					template,
					ofNullable(settings).orElse("{}"),
					DEFAULT_INDEX_TYPE_NAME,
					ofNullable(mappings).orElse("{}")
			);
			NStringEntity entity = new NStringEntity(formattedString, ContentType.APPLICATION_JSON);
			final Response response = nativeClient.performRequest("PUT", indexName, params, entity);
			return response.getStatusLine().getStatusCode() == 200;
		} catch (IOException e) {
			throw mapErrors(LOG, e);
		}
	}

	@Override
	public boolean isExists(String indexName) {
		try {
			final Response response = nativeClient.performRequest("HEAD", indexName);
			return response.getStatusLine().getStatusCode() == 200;
		} catch (Exception e) {
			throw mapErrors(LOG, e);
		}
	}

	@Override
	public boolean delete(String indexName) {
		try {
			final Response response = nativeClient.performRequest("DELETE", indexName);
			return response.getStatusLine().getStatusCode() == 200;
		} catch (Exception e) {
			throw mapErrors(LOG, e);
		}
	}

	@Override
	public boolean addMappings(String indexName, Mappings mappings) {
		try {
			return addMappings(indexName, mapper.toJson(mappings));
		} catch (Exception e) {
			throw mapErrors(LOG, e);
		}
	}

	@Override
	public boolean addMappings(String indexName, String mappings) {
		try {
			Map<String, String> params = Collections.emptyMap();
			NStringEntity entity = new NStringEntity(mappings, ContentType.APPLICATION_JSON);
			final String endpoint = String.format("%s/_mapping/%s", indexName, DEFAULT_INDEX_TYPE_NAME);
			final Response response = nativeClient.performRequest("POST", endpoint, params, entity);
			return response.getStatusLine().getStatusCode() == 200;
		} catch (Exception e) {
			throw mapErrors(LOG, e);
		}
	}

	@Override
	public IndexDescription getIndex(String indexName) {
		try {
			Map<String, String> params = Collections.emptyMap();
			final Response response = nativeClient.performRequest("GET", indexName, params);
			Map<String, IndexDescription> output = mapper.mapToObject(response.getEntity().getContent(), new TypeReference<Map<String, IndexDescription>>() {
			});
			if (output.isEmpty()) {
				throw new IndexNotFoundException(String.format("Index %s not found", indexName));
			}

			return output.get(indexName);
		} catch (Exception e) {
			throw mapErrors(LOG, e);
		}
	}

	static ResourceException mapErrors(Logger LOG, Exception error) {
		//TODO ako: test all cases
		String responseBodyAsString = error.getMessage();
		if (LOG.isDebugEnabled()) {
			LOG.debug(responseBodyAsString);
		}
		if (responseBodyAsString.contains("index_not_found_exception")) {
			return new IndexNotFoundException(responseBodyAsString, error);
		}

		if (responseBodyAsString.contains("resource_already_exists_exception")) {
			return new IndexAlreadyExistsException(responseBodyAsString, error);
		}

		if (responseBodyAsString.contains("404")) {
			LOG.error(responseBodyAsString);
			return new IndexNotFoundException(responseBodyAsString, error);
		}

		return new ElasticsearchServerException(responseBodyAsString, error);
	}

	@Override
	public boolean refresh(String indexName) {
		try{
			final String endpoint = String.format("%s/_refresh", indexName);
			final Response response = nativeClient.performRequest("POST", endpoint);
			return response.getStatusLine().getStatusCode() == 200;
		}catch (Exception e) {
			throw mapErrors(LOG, e);
		}
	}

}
