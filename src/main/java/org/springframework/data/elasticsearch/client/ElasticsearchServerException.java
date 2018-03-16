package org.springframework.data.elasticsearch.client;

public class ElasticsearchServerException extends ResourceException {
    public ElasticsearchServerException(String response) {
        super(response);
    }

    public ElasticsearchServerException(String response, Throwable error) {
        super(response, error);
    }
}
