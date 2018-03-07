package org.springframework.data.elasticsearch.client.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class IndexDescription {


    private Map<String,Object> aliases;
    private Map<String, Mappings> mappings;
    private Settings settings;


    public Map<String,Object> getAliases() {
        return aliases;
    }

    public void setAliases(Map<String,Object> aliases) {
        this.aliases = aliases;
    }

    public Map<String, Mappings> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, Mappings> mappings) {
        this.mappings = mappings;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "IndexDescription{" +
                "aliases=" + aliases +
                ", mappings=" + mappings +
                ", settings=" + settings +
                '}';
    }

}
