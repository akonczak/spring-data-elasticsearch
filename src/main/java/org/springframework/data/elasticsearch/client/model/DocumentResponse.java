package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DocumentResponse {

    @JsonProperty(value = "_index")
    private String index;
    @JsonProperty(value = "_type")
    private String type;
    @JsonProperty(value = "_id")
    private String id;
    @JsonProperty(value = "_version")
    private Integer version;
    @JsonProperty(value = "result")
    private String result;
    @JsonProperty(value = "_seq_no")
    private Integer seqNo;
    @JsonProperty(value = "_primary_term")
    private Integer primaryTerm;
    @JsonProperty(value = "_shards")
    private Shards shards;


    @Override
    public String toString() {
        return "DocumentResponse{" +
                "index='" + index + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", version=" + version +
                ", result='" + result + '\'' +
                ", seqNo=" + seqNo +
                ", primaryTerm=" + primaryTerm +
                ", shards=" + shards +
                '}';
    }
}

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
class Shards {
    @JsonProperty(value = "total")
    private Integer total;
    @JsonProperty(value = "successful")
    private Integer successful;
    @JsonProperty(value = "failed")
    private Integer failed;

    @Override
    public String toString() {
        return "Shards{" +
                "total=" + total +
                ", successful=" + successful +
                ", failed=" + failed +
                '}';
    }
}
