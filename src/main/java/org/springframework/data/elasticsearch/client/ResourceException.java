package org.springframework.data.elasticsearch.client;

public class ResourceException extends RuntimeException {

	public ResourceException(String response) {
		super(response);
	}

	public ResourceException(String response, Throwable error) {
		super(response, error);
	}
}
