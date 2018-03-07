package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Settings {

    private Index index;

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "index=" + index +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Settings)) return false;

        Settings settings = (Settings) o;

        return index != null ? index.equals(settings.index) : settings.index == null;
    }

    @Override
    public int hashCode() {
        return index != null ? index.hashCode() : 0;
    }
}

