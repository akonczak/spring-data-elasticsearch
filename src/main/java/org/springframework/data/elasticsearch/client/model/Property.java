package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Map;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Property {

    private String type;
    private Map<String, Property> fields;

    @Override
    public String toString() {
        return "Property{" +
                ", type='" + type + '\'' +
                ", fields=" + fields +
                '}';
    }
}
