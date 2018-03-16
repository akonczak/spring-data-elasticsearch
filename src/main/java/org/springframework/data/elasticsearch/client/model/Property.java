package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.DateFormat;

import java.util.Map;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Property {

    private String type;
    private Map<String, Property> properties;
    @JsonProperty(value = "search_analyzer")
    private String searchAnalyzer;
    private String analyzer;
    private Boolean index;
    private Boolean store;
    private Boolean fielddata;
    private String format;
    @JsonProperty(value = "include_in_parent")
    private Boolean includeInParent;

    @JsonProperty(value = "preserve_separators")
    private Boolean preserveSeparators;
    @JsonProperty(value = "preserve_position_increments")
    private Boolean preservePositionIncrements;
    @JsonProperty(value = "max_input_length")
    private Integer maxInputLength;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Property{");
        sb.append("type='").append(type).append('\'');
        sb.append(", properties=").append(properties);
        sb.append(", searchAnalyzer='").append(searchAnalyzer).append('\'');
        sb.append(", analyzer='").append(analyzer).append('\'');
        sb.append(", index=").append(index);
        sb.append(", store=").append(store);
        sb.append(", fielddata=").append(fielddata);
        sb.append(", format='").append(format).append('\'');
        sb.append(", includeInParent=").append(includeInParent);
        sb.append(", preserveSeparators=").append(preserveSeparators);
        sb.append(", preservePositionIncrements=").append(preservePositionIncrements);
        sb.append(", maxInputLength=").append(maxInputLength);
        sb.append('}');
        return sb.toString();
    }
}
