package org.springframework.data.elasticsearch.client.model;

import lombok.*;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mappings {

    private Map<String, Object> dynamicTemplates;
    private Map<String, Property> properties;

    @Override
    public String toString() {
        return "Mappings{" +
                "dynamicTemplates=" + dynamicTemplates +
                ", properties=" + properties +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mappings)) return false;

        Mappings mappings = (Mappings) o;

        if (dynamicTemplates != null ? !dynamicTemplates.equals(mappings.dynamicTemplates) : mappings.dynamicTemplates != null)
            return false;
        return properties != null ? properties.equals(mappings.properties) : mappings.properties == null;
    }

    @Override
    public int hashCode() {
        int result = dynamicTemplates != null ? dynamicTemplates.hashCode() : 0;
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }
}
