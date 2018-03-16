package org.springframework.data.elasticsearch.client.model;


import org.springframework.data.elasticsearch.client.ResourceException;

public class DocumentNotFoundException extends ResourceException {
    public DocumentNotFoundException(String response) {
        super(response);
    }

    public DocumentNotFoundException(String response, Throwable e) {
        super(response, e);
    }
}
