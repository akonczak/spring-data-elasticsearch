package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ModelMapper {

    final private ObjectMapper mapper;

    public ModelMapper() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public String toJson(Object obj) throws IOException {
        return new String(mapper.writeValueAsBytes(obj));
    }

    public <T> T mapToObject(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public <T> T mapToObject(String json, TypeReference<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public <T> T mapToObject(InputStream json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public <T> T mapToObject(InputStream json, TypeReference<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }


}
