package org.springframework.data.elasticsearch.client.rest;

import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.ElasticsearchException;
import org.springframework.data.elasticsearch.client.DocumentAPI;
import org.springframework.data.elasticsearch.client.ResourceException;
import org.springframework.data.elasticsearch.client.model.DocumentNotFoundException;
import org.springframework.data.elasticsearch.client.model.DocumentResponse;
import org.springframework.data.elasticsearch.client.model.JsonMapper;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.springframework.data.elasticsearch.client.IndicesAPI.DEFAULT_INDEX_TYPE_NAME;
import static org.springframework.data.elasticsearch.client.rest.RestUtil.asString;
import static org.springframework.data.elasticsearch.client.rest.RestUtil.endpoint;
import static org.springframework.util.StringUtils.hasText;

public class DocumentAPIImpl implements DocumentAPI {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentAPIImpl.class);
    private final JsonMapper mapper;
    private final RestClient nativeClient;

    public DocumentAPIImpl(JsonMapper mapper, RestClient nativeClient) {
        this.mapper = mapper;
        this.nativeClient = nativeClient;
    }

    @Override
    public String index(String indexName, @Nullable String documentId, String json) {
        try {
            String method = hasText(documentId) ? "PUT" : "POST";
            String endpoint = RestUtil.endpoint(indexName, DEFAULT_INDEX_TYPE_NAME, documentId);
            Map<String, String> params = Collections.emptyMap();
            NStringEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
            final Response response = nativeClient.performRequest(method, endpoint, params, entity);
            DocumentResponse documentResponse = mapper.mapToObject(response.getEntity().getContent(), DocumentResponse.class);
            if (asList("created", "udpated").contains(documentResponse.getResult())){
                return documentResponse.getId();
            }
            throw new ElasticsearchException(String.format("Unknown operation: %s", documentResponse));
        } catch (IOException e) {
            throw mapErrors(LOG, e);
        }
    }

    @Override
    public String index(String indexName, String json) {
        return index(indexName, null, json);
    }

    @Override
    public boolean delete(String indexName, String documentId) {
        try {
            final Response response = nativeClient.performRequest("DELETE", endpoint(indexName, DEFAULT_INDEX_TYPE_NAME, documentId));
            DocumentResponse documentResponse = mapper.mapToObject(response.getEntity().getContent(), DocumentResponse.class);
            if ("deleted".equals(documentResponse.getResult())) {
                return true;
            }
            throw new ElasticsearchException(String.format("Unknown operation: %s", documentResponse));
        } catch (IOException e) {
            throw mapErrors(LOG, e);
        }
    }

    @Override
    public <T> T get(String indexName, String documentId, Class<T> aClass) {
        try {
            String method = "GET";
            String endpoint = RestUtil.endpoint(indexName, DEFAULT_INDEX_TYPE_NAME, documentId);
            Map<String, String> params = Collections.emptyMap();
            final Response response = nativeClient.performRequest(method, endpoint, params);
            if (aClass.isAssignableFrom(String.class)) {
                return (T) asString(response.getEntity().getContent());
            } else {
                return mapper.mapToObject(response.getEntity().getContent(), aClass);
            }
        } catch (IOException e) {
            throw mapErrors(LOG, e);
        }
    }


    public ResourceException mapErrors(Logger LOG, IOException error) {
        //TODO ako: make this better them checking string
        String responseAsString = error.toString();
        if(responseAsString.contains("\"result\":\"not_found\"") || responseAsString.contains("\"found\":false")){
            return new DocumentNotFoundException(responseAsString, error);
        }
        //fall back - errors on index level
        return IndicesAPIImpl.mapErrors(LOG, error);
    }

}
