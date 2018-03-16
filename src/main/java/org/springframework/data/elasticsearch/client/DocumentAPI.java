package org.springframework.data.elasticsearch.client;

import org.springframework.data.elasticsearch.client.model.DocumentResponse;

/**
 * All CRUD operation on documents
 */
public interface DocumentAPI {

	public String index(String indexName, String json);

	public String index(String indexName, String documentId ,String json);

	public boolean delete(String indexName, String documentId);

    public <T> T get(String indexName, String documentId, Class<T> aClass);

}
