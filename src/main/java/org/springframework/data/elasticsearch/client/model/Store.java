package org.springframework.data.elasticsearch.client.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Store {
    private String type;
}
