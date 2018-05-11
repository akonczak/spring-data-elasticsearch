package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.data.elasticsearch.json.SourceDeserialiser;

import java.util.List;


/*
"_index": "sg_index_checker",
"_type": "sg_index_checker",
"_id": "10.1007/978-1-349-09251-2",
"_score": 0.67360467,
"_source": {
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SearchEntry {

    @JsonProperty(value = "_index")
    private String index;
    @JsonProperty(value = "_type")
    private String type;
    @JsonProperty(value = "_id")
    private String id;
    @JsonProperty(value = "_score")
    private String score;
    @JsonRawValue
    @JsonProperty(value = "_source")
    @JsonDeserialize(using = SourceDeserialiser.class)
    private String source;

    @Override
    public String toString() {
        return "SearchEntry{" +
                "index='" + index + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", score='" + score + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
