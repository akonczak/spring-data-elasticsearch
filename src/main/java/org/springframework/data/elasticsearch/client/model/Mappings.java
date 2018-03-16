package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mappings {

    private Map<String, Object> dynamicTemplates;
    private Map<String, Property> properties;
    private Boolean dynamic;
    @JsonProperty(value = "date_detection")
    private Boolean dateDetection;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Mappings{");
        sb.append("dynamicTemplates=").append(dynamicTemplates);
        sb.append(", properties=").append(properties);
        sb.append(", dynamic=").append(dynamic);
        sb.append(", dateDetection=").append(dateDetection);
        sb.append('}');
        return sb.toString();
    }
}
