package org.springframework.data.elasticsearch.client.model;

import org.springframework.data.elasticsearch.client.ResourceException;

public class IndexAlreadyExistsException extends ResourceException {
    public IndexAlreadyExistsException(String response) {
        super(response);
    }

    public IndexAlreadyExistsException(String response, Throwable error) {
        super(response, error);
    }
}
