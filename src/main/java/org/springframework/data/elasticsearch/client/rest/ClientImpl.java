package org.springframework.data.elasticsearch.client.rest;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.data.elasticsearch.client.Client;
import org.springframework.data.elasticsearch.client.IndexAPI;
import org.springframework.data.elasticsearch.client.model.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;

@Service
public class ClientImpl implements Client {

    private IndexAPI indexAPI;

    private org.elasticsearch.client.RestClient nativeClient;

    static final String COLON = ":";


    public ClientImpl(ModelMapper mapper, String... hosts) {

        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int i = 0; i < hosts.length; i++) {
            String[] parts = hosts[i].split(COLON);
            Assert.hasText(parts[0], "[Assertion failed] missing host name in 'clusterNodes'");
            Assert.hasText(parts[1], "[Assertion failed] missing port in 'clusterNodes'");

            httpHosts[i] = new HttpHost(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }

        nativeClient = RestClient.builder(httpHosts).build();

        indexAPI = new IndexAPIImpl(mapper, nativeClient);
    }

    public ClientImpl(String... hosts) {
        this(new ModelMapper(), hosts);
    }

    @Override
    public IndexAPI getIndexAPI() {
        return indexAPI;
    }

    @Override
    public void close() throws IOException {
        nativeClient.close();
    }
}
