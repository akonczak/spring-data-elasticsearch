package org.springframework.data.elasticsearch.client.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.client.ElasticsearchServerException;
import org.springframework.data.elasticsearch.client.IndexAPI;
import org.springframework.data.elasticsearch.client.ResourceException;
import org.springframework.data.elasticsearch.client.model.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IndexAPIImpl implements IndexAPI {

    private static final Logger LOG = LoggerFactory.getLogger(IndexAPIImpl.class);
    private ModelMapper mapper;
    private RestClient nativeClient;

    public IndexAPIImpl(ModelMapper mapper, RestClient nativeClient) {
        this.nativeClient = nativeClient;
        this.mapper = mapper;
    }

    @Override
    public boolean create(String indexName, Settings settings, Mappings mappings) {
        try {
            Map<String, String> params = Collections.emptyMap();
            Map<String, Object> config = new HashMap<>();
            config.put("settings", settings);
            Map<String, Object> mappingMap = new HashMap<>();
            mappingMap.put(DEFAULT_INDEX_TYPE_NAME, mappings);
            config.put("mappings", mappingMap);
            NStringEntity entity = new NStringEntity(mapper.toJson(config), ContentType.APPLICATION_JSON);
            final Response response = nativeClient.performRequest("PUT", indexName, params, entity);
            return response.getStatusLine().getStatusCode() == 200;
        } catch (IOException e) {
            throw mapErrors(e);
        }
    }

    @Override
    public boolean isExists(String indexName) {
        try {
            final Response response = nativeClient.performRequest("HEAD", indexName);
            return response.getStatusLine().getStatusCode() == 200;
        } catch (Exception e) {
            throw mapErrors(e);
        }
    }

    @Override
    public boolean delete(String indexName) {
        try {
            final Response response = nativeClient.performRequest("DELETE", indexName);
            return response.getStatusLine().getStatusCode() == 200;
        } catch (Exception e) {
            throw mapErrors(e);
        }
    }

    @Override
    public boolean addMappings(String indexName, Mappings mappings) {
        try {
            Map<String, String> params = Collections.emptyMap();
            NStringEntity entity = new NStringEntity(mapper.toJson(mappings), ContentType.APPLICATION_JSON);
            final Response response = nativeClient.performRequest("PUT", indexName + "_mapping/" + DEFAULT_INDEX_TYPE_NAME, params, entity);
            return response.getStatusLine().getStatusCode() == 200;
        } catch (Exception e) {
            throw mapErrors(e);
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
            throw mapErrors(e);
        }
    }


    protected ResourceException mapErrors(Exception error) {
        //TODO ako: test all cases
        String responseBodyAsString = error.getMessage();
        if (LOG.isDebugEnabled()) {
            LOG.debug(responseBodyAsString);
        }
        if (responseBodyAsString.contains("index_not_found_exception")) {
            return new IndexNotFoundException(responseBodyAsString);
        }

        if (responseBodyAsString.contains("resource_already_exists_exception")) {
            return new IndexAlreadyExistsException(responseBodyAsString);
        }

        if (responseBodyAsString.contains("404")) {
            LOG.error(responseBodyAsString);
            return new IndexNotFoundException(responseBodyAsString);
        }

        return new ElasticsearchServerException(responseBodyAsString);
    }

}
