package org.springframework.data.elasticsearch.client.rest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.elasticsearch.client.Client;
import org.springframework.data.elasticsearch.client.IndicesAPI;
import org.springframework.data.elasticsearch.client.RestClientFactoryBean;
import org.springframework.data.elasticsearch.client.model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IndexAPIImplTest {

    JsonMapper mapper;
    RestClientFactoryBean factoryBean = new RestClientFactoryBean();
    Client client;
    IndicesAPI indexAPI;

    final String indexName = "index_api_test";

    @Before
    public void init() throws Exception {
        mapper = new JsonMapperImpl();
        factoryBean.setClusterNodes("localhost:9260");
        client = factoryBean.getObject();
        indexAPI = client.getIndicesAPI();
        if (indexAPI.isExists(indexName)) {
            indexAPI.delete(indexName);
        }

    }

    @Test
    public void shouldCheckIfIndexExists() throws IOException {
        //given
        //when
        //then
        assertThat(indexAPI.isExists(indexName), is(false));
        assertThat(indexAPI.create(indexName, new Settings(), null), is(true));
        assertThat(indexAPI.isExists(indexName), is(true));
    }

    @Test
    public void shouldCreateIndex() throws IOException {
        //given
        //when
        //then
        final Index index = Index.builder()
                .numberOfReplicas(1)
                .numberOfShards(1)
                .build();
        final Settings settings = Settings.builder().index(index).build();
        assertThat(indexAPI.create(indexName, settings, null), is(true));
        final IndexDescription indexDescription = indexAPI.getIndex(indexName);
        assertThat(indexDescription.getSettings().getIndex().getProvidedName(), is(indexName));
        assertThat(indexDescription.getSettings().getIndex().getNumberOfReplicas(), is(1));
        assertThat(indexDescription.getSettings().getIndex().getNumberOfShards(), is(1));

    }

    @Test(expected = IndexAlreadyExistsException.class)
    public void shouldThrowErrorOnIndexCreation() throws IOException {
        //given
        //when
        //then
        assertThat(indexAPI.create(indexName, new Settings(), null), is(true));
        assertThat(indexAPI.create(indexName, new Settings(), null), is(true));
    }

    @Test
    public void shouldDeleteIndex() throws IOException {
        //given
        //when
        //then
        assertThat(indexAPI.create(indexName, new Settings(), null), is(true));
        assertThat(indexAPI.isExists(indexName), is(true));
        assertThat(indexAPI.delete(indexName), is(true));
        assertThat(indexAPI.isExists(indexName), is(false));
    }

    @Test
    public void shouldGetIndexDetails() throws IOException {
        //given
        //when
        //then
        assertThat(indexAPI.create(indexName, new Settings(), null), is(true));
        final IndexDescription indexDescription = indexAPI.getIndex(indexName);
        assertThat(indexDescription.getSettings().getIndex().getProvidedName(), is(indexName));
        assertThat(indexDescription.getSettings().getIndex().getNumberOfReplicas(), is(1));
        assertThat(indexDescription.getSettings().getIndex().getNumberOfShards(), is(5));
    }

    @Test
    public void shouldAddMappings() throws IOException {
        //given

        //when
        Map<String,Property> propertyMap = new HashMap<>();
        propertyMap.put("name", Property.builder().type("text").build());
        //then
        final Mappings mappings = Mappings.builder()
                .properties(propertyMap)
                .build();
        assertThat(indexAPI.create(indexName, new Settings(), mappings), is(true));
        final IndexDescription indexDescription = indexAPI.getIndex(indexName);
        final Mappings mappingForDefaultType = indexDescription.getMappings().get(IndicesAPI.DEFAULT_INDEX_TYPE_NAME);
        final Property nameField = mappingForDefaultType.getProperties().get("name");
        assertThat(nameField.getType(), is("text"));
    }


}