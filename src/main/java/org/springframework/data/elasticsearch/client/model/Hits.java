package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Hits {
    private long total;
    @JsonProperty(value = "max_score")
    private Double maxScore;

    private List<SearchEntry> hits;

    @Override
    public String toString() {
        return "Hits{" +
                "total=" + total +
                ", maxScore=" + maxScore +
                ", hits=" + hits +
                '}';
    }
}
