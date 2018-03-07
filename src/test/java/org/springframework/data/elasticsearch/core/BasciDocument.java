package org.springframework.data.elasticsearch.core;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Document(indexName = "unit_test_basic_document", shards = 2, replicas = 2)
public class BasciDocument {

    @Id
    private String id;
    private String title;
    private String description;
    private Date created;
}