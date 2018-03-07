package org.springframework.data.elasticsearch.client;

import java.io.IOException;

/**
 * Abstraction to hide any clint behind it - and be independent from clients
 */
public interface Client {

    public IndexAPI getIndexAPI();

    public void close() throws IOException;

}
