package org.springframework.data.elasticsearch.client.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class IndexTest {

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
    public void shouldMapIndexRequestToModel() throws IOException {
        //given
        //when
        final IndexDescription indexDescription = modelMapper.mapToObject(requestFile("create-index.json"), IndexDescription.class);
        //then
        assertThat(indexDescription.getAliases().size(), is(2));
        assertThat(indexDescription.getSettings().getIndex().getNumberOfReplicas(), is(1));
        assertThat(indexDescription.getSettings().getIndex().getNumberOfShards(), is(1));
        assertThat(indexDescription.getMappings().get("_doc").getProperties().size(), is(2));
    }

    @Test
    public void shouldMapIndexDetails() throws IOException {
        //given

        //when
        final Map<String, IndexDescription> map = modelMapper.mapToObject(responseFile("index-details.json"), new TypeReference<Map<String, IndexDescription>>() {
        });
        //then
        assertThat(map.size(), is(1));
        IndexDescription indexDescription = map.get("twitter");
        assertThat(indexDescription.getAliases().size(), is(2));
        assertThat(indexDescription.getSettings().getIndex().getNumberOfReplicas(), is(1));
        assertThat(indexDescription.getSettings().getIndex().getNumberOfShards(), is(1));
        assertThat(indexDescription.getMappings().get("_doc").getProperties().size(), is(2));
    }


}