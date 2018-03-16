package org.springframework.data.elasticsearch.client.model;


import org.springframework.data.elasticsearch.client.ResourceException;

public class IndexNotFoundException extends ResourceException {
    public IndexNotFoundException(String response) {
        super(response);
    }

    public IndexNotFoundException(String response, Throwable e) {
        super(response, e);
    }
}
