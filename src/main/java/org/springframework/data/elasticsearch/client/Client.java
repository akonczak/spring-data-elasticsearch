package org.springframework.data.elasticsearch.client;

import org.springframework.data.elasticsearch.client.model.JsonMapper;

import java.io.IOException;

/**
 * Abstraction to hide any clint behind it - and be independent from clients
 */
public interface Client {

    public IndicesAPI getIndicesAPI();

    public DocumentAPI getDocumentAPI();

    public SearchAPI getSearchAPI();

    public void close() throws IOException;

    public JsonMapper getMapper();

}
