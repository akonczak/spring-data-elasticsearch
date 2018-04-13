package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Shards {
    @JsonProperty(value = "total")
    private Integer total;
    @JsonProperty(value = "successful")
    private Integer successful;
    @JsonProperty(value = "failed")
    private Integer failed;
    @JsonProperty(value = "skipped")
    private Integer skipped;

    @Override
    public String toString() {
        return "Shards{" +
                "total=" + total +
                ", successful=" + successful +
                ", failed=" + failed +
                ", skipped=" + skipped +
                '}';
    }
}
