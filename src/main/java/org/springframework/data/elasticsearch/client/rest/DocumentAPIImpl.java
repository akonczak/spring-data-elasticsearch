package org.springframework.data.elasticsearch.client.rest;

import org.elasticsearch.client.RestClient;
import org.springframework.data.elasticsearch.client.DocumentAPI;
import org.springframework.data.elasticsearch.client.model.JsonMapper;

public class DocumentAPIImpl implements DocumentAPI {

	private final JsonMapper mapper;
	private final RestClient nativeClient;

	public DocumentAPIImpl(JsonMapper mapper, RestClient nativeClient) {
		this.mapper = mapper;
		this.nativeClient = nativeClient;
	}

	@Override
	public String index(String indexName, String json) {
		return null;
	}

	@Override
	public <T> T index(T document) {
		return null;
	}

	@Override
	public boolean delete(String indexName, String documentId) {
		return false;
	}

	@Override
	public <T> boolean delete(Class<T> documentClass, String documentId) {
		return false;
	}

	@Override
	public <T> boolean delete(T document) {
		return false;
	}
}
