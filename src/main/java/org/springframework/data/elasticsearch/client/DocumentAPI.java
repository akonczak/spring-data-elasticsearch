package org.springframework.data.elasticsearch.client;

/**
 * All CRUD operation on documents
 */
public interface DocumentAPI {

	public String index(String indexName, String json);

	public <T> T index(T document);

	public boolean delete(String indexName, String documentId);

	public <T> boolean delete(Class<T> documentClass, String documentId);

	public <T> boolean delete(T document);
}
