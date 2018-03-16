package org.springframework.data.elasticsearch.core;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "unit_test_basic_document", shards = 2, replicas = 2)
public class BasicDocument {

    @Id
    private String id;
    @Field(type = FieldType.text)
    private String title;
    private String description;
    private Date created;
}