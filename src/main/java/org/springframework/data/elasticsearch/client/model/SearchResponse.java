package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


/*
 {
     "took" : 73,
     "timed_out" : false,
     "_shards" : {
         "total" : 1,
         "successful" : 1,
         "skipped" : 0,
         "failed" : 0
     },
     "hits" : {
         "total" : 0,
         "max_score" : null,
         "hits" : [ ]
     }
 }
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SearchResponse {

    @JsonProperty(value = "took")
    private long took;
    @JsonProperty(value = "timed_out")
    private boolean timedOut;
    @JsonProperty(value = "_shards")
    private Shards shards;

    private Hits hits;

    @Override
    public String toString() {
        return "SearchResponse{" +
                "took=" + took +
                ", timed_out=" + timedOut +
                ", shards=" + shards +
                '}';
    }
}

