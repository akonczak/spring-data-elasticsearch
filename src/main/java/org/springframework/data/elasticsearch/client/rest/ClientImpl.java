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

    private IndexAPI indexAPI;
    private DocumentAPI documentAPI;

    private RestClient nativeClient;

    static final String COLON = ":";


    public ClientImpl(JsonMapper mapper, String... hosts) {

        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int i = 0; i < hosts.length; i++) {
            String[] parts = hosts[i].split(COLON);
            Assert.hasText(parts[0], "[Assertion failed] missing host name in 'clusterNodes'");
            Assert.hasText(parts[1], "[Assertion failed] missing port in 'clusterNodes'");

            httpHosts[i] = new HttpHost(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }

        nativeClient = RestClient.builder(httpHosts).build();

        indexAPI = new IndexAPIImpl(mapper, nativeClient);
        documentAPI = new DocumentAPIImpl(mapper, nativeClient);
    }

    public ClientImpl(String... hosts) {
        this(new JsonMapperImpl(), hosts);
    }

    @Override
    public IndexAPI getIndexAPI() {
        return indexAPI;
    }

    @Override
    public DocumentAPI getDocumentAPI() {
        return documentAPI;
    }

    @Override
    public void close() throws IOException {
        nativeClient.close();
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
}
