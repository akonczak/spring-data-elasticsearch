package org.springframework.data.elasticsearch.client.rest;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.springframework.data.elasticsearch.client.*;
import org.springframework.data.elasticsearch.client.model.IndexAlreadyExistsException;
import org.springframework.data.elasticsearch.client.model.IndexNotFoundException;
import org.springframework.data.elasticsearch.client.model.JsonMapperImpl;
import org.springframework.data.elasticsearch.client.model.JsonMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;

@Service
public class ClientImpl implements Client, AutoCloseable {

    static final String COLON = ":";

    private IndicesAPI indexAPI;
    private DocumentAPI documentAPI;
    private RestClient nativeClient;
    private SearchAPI searchAPI;
    private JsonMapper mapper;

    public ClientImpl(JsonMapper mapper, String... hosts) {

        this.mapper = mapper;
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int i = 0; i < hosts.length; i++) {
            String[] parts = hosts[i].split(COLON);
            Assert.hasText(parts[0], "[Assertion failed] missing host name in 'clusterNodes'");
            Assert.hasText(parts[1], "[Assertion failed] missing port in 'clusterNodes'");

            httpHosts[i] = new HttpHost(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }

        nativeClient = RestClient.builder(httpHosts).build();

        indexAPI = new IndicesAPIImpl(this.mapper, nativeClient);
        documentAPI = new DocumentAPIImpl(this.mapper, nativeClient);
        searchAPI = new SearchAPIImpl(this.mapper, nativeClient);
    }

    public ClientImpl(String... hosts) {
        this(new JsonMapperImpl(), hosts);
    }

    @Override
    public IndicesAPI getIndicesAPI() {
        return indexAPI;
    }

    @Override
    public DocumentAPI getDocumentAPI() {
        return documentAPI;
    }

    @Override
    public SearchAPI getSearchAPI() {
        return searchAPI;
    }

    @Override
    public JsonMapper getMapper(){
        return this.mapper;
    }

    @Override
    public void close() throws IOException {
        nativeClient.close();
    }

}
