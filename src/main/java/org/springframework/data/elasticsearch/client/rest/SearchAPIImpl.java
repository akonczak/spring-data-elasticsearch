package org.springframework.data.elasticsearch.client.rest;

import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.ElasticsearchException;
import org.springframework.data.elasticsearch.client.ResourceException;
import org.springframework.data.elasticsearch.client.SearchAPI;
import org.springframework.data.elasticsearch.client.model.DocumentNotFoundException;
import org.springframework.data.elasticsearch.client.model.DocumentResponse;
import org.springframework.data.elasticsearch.client.model.JsonMapper;
import org.springframework.data.elasticsearch.client.model.SearchResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.springframework.data.elasticsearch.client.IndicesAPI.DEFAULT_INDEX_TYPE_NAME;
import static org.springframework.data.elasticsearch.client.rest.RestUtil.asString;
import static org.springframework.util.StringUtils.hasText;

public class SearchAPIImpl implements SearchAPI{

    private static final Logger LOG = LoggerFactory.getLogger(SearchAPIImpl.class);
    private final JsonMapper mapper;
    private final RestClient nativeClient;

    public SearchAPIImpl(JsonMapper mapper, RestClient nativeClient) {
        this.mapper = mapper;
        this.nativeClient = nativeClient;
    }

    @Override
    public SearchResponse searchRaw(String indexName, String json) {
        try {
            String method = "POST";
            String endpoint = RestUtil.endpoint(indexName, "_search");
            Map<String, String> params = Collections.emptyMap();
            NStringEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
            final Response response = nativeClient.performRequest(method, endpoint, params, entity);
            InputStream content = response.getEntity().getContent();
            String tmp = StreamUtils.copyToString(content, Charset.defaultCharset());
            System.out.println(tmp);
            return mapper.mapToObject(tmp, SearchResponse.class);
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
