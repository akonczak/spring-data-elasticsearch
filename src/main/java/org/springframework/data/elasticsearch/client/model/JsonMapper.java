package org.springframework.data.elasticsearch.client.model;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.type.TypeReference;

public interface JsonMapper {

	String toJson(Object obj) throws IOException;

	<T> T mapToObject(String json, Class<T> aClass) throws IOException;

	<T> T mapToObject(String json, TypeReference<T> aClass) throws IOException;

	<T> T mapToObject(InputStream json, Class<T> aClass) throws IOException;

	<T> T mapToObject(InputStream json, TypeReference<T> aClass) throws IOException;
}
