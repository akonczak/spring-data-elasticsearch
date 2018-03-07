package org.springframework.data.elasticsearch.client;

public class ElasticsearchServerException extends ResourceException {
    public ElasticsearchServerException(String response) {
        super(response);
    }
}
