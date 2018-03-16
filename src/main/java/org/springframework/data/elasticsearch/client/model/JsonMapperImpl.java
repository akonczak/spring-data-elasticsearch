package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;

public class JsonMapperImpl implements JsonMapper {

    final private ObjectMapper mapper;

    public JsonMapperImpl() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public String toJson(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    @Override
    public <T> T mapToObject(String json, Class<T> aClass) throws IOException {
        return mapper.readValue(json, aClass);
    }

    @Override
    public <T> T mapToObject(String json, TypeReference<T> aClass) throws IOException {
        return mapper.readValue(json, aClass);
    }

    @Override
    public <T> T mapToObject(InputStream json, Class<T> aClass) throws IOException {
        return mapper.readValue(json, aClass);
    }

    @Override
    public <T> T mapToObject(InputStream json, TypeReference<T> aClass) throws IOException {
        return mapper.readValue(json, aClass);
    }


}
