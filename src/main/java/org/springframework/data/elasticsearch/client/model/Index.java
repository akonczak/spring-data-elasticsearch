package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Index {
    @JsonProperty(value = "creation_date")
    private String creationDate;
    @JsonProperty(value = "number_of_shards")
    private int numberOfShards;
    @JsonProperty(value = "number_of_replicas")
    private int numberOfReplicas;
    @JsonProperty(value = "refresh_interval")
    private String refreshInterval;
    private String uuid;
    @JsonProperty(value = "provided_name")
    private String providedName;

    private Map<String,Object> version;

    private Store store;

    @Override
    public String toString() {
        return "Index{" +
                "creation_date='" + creationDate + '\'' +
                ", numberOfShards='" + numberOfShards + '\'' +
                ", numberOfReplicas='" + numberOfReplicas + '\'' +
                ", uuid='" + uuid + '\'' +
                ", provided_name='" + providedName + '\'' +
                ", version=" + version +
                '}';
    }
}
