package org.springframework.data.elasticsearch.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class SourceDeserialiser extends StdDeserializer {

    private Class<?> vcc;

    public SourceDeserialiser() {
        this(null);
    }

    public SourceDeserialiser(Class<?> vc) {
        super(vc);
        vcc=vc;
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext context)
        throws IOException, JsonProcessingException {

        JsonNode productNode = jsonParser.getCodec().readTree(jsonParser);
//        product.setId(productNode.get("id").textValue());
//        product.setName(productNode.get("name").textValue());
//        product.setBrandName(productNode.get("brand")
//            .get("name").textValue());
//        product.setOwnerName(productNode.get("brand").get("owner")
//            .get("name").textValue());
//        return product;
        return productNode.toString();
    }
}