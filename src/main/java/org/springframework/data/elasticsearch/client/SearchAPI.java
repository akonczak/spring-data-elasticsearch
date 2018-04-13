package org.springframework.data.elasticsearch.client;

import org.springframework.data.elasticsearch.client.model.SearchResponse;

/**
 * All CRUD operation on search
 */
public interface SearchAPI {

	public SearchResponse searchRaw(String indexName, String json);

}
