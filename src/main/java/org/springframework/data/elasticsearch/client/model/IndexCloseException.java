package org.springframework.data.elasticsearch.client.model;


import org.springframework.data.elasticsearch.client.ResourceException;

public class IndexCloseException extends ResourceException {
    public IndexCloseException(String response) {
        super(response);
    }
}
