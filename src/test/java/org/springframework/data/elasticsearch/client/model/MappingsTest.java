package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MappingsTest {

    final static int ES_VERSION = 6;

    private final JsonMapperImpl modelMapper = new JsonMapperImpl();

    private String readJsonFile(String filePath) throws IOException {
        try (InputStreamReader in = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filePath))) {
            return FileCopyUtils.copyToString(in);
        }
    }

    private String requestFile(String fileName) throws IOException {
        return readJsonFile(String.format("es-model/%s/request/%s", ES_VERSION, fileName));
    }

    private String responseFile(String fileName) throws IOException {
        return readJsonFile(String.format("es-model/%s/response/%s", ES_VERSION, fileName));
    }

    @Test
    public void shouldMapMappingsToModel() throws IOException {
        //given

        //when
        final Map<String, IndexDescription> map = modelMapper.mapToObject(responseFile("big-mappings.json"), new TypeReference<Map<String, IndexDescription>>() {
        });
        //then
        assertThat(map.size(), is(1));
        IndexDescription indexDescription = map.get("index_name");
        assertThat(indexDescription.getMappings().get("doc").getProperties().size(), is(14));
    }


}