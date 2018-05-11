package org.springframework.data.elasticsearch.client.rest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.elasticsearch.TestBase;
import org.springframework.data.elasticsearch.client.Client;
import org.springframework.data.elasticsearch.client.DocumentAPI;
import org.springframework.data.elasticsearch.client.IndicesAPI;
import org.springframework.data.elasticsearch.client.RestClientFactoryBean;
import org.springframework.data.elasticsearch.client.model.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class DocumentAPIImplTest extends TestBase {


    JsonMapper mapper;
    RestClientFactoryBean factoryBean = new RestClientFactoryBean();
    Client client;
    IndicesAPI indicesAPI;
    DocumentAPI documentAPI;

    @Before
    public void init() throws Exception {
        mapper = new JsonMapperImpl();
        factoryBean.setClusterNodes("localhost:9260");
        client = factoryBean.getObject();
        indicesAPI = client.getIndicesAPI();
        documentAPI = client.getDocumentAPI();
        if (indicesAPI.isExists(getIndexName())) {
            indicesAPI.delete(getIndexName());
        }
    }

    @Test
    public void shouldIndexJsonDocument() {
        //given
        //when
        //then
        assertThat(documentAPI.index(getIndexName(), "{\"title\":\"test title\"}"), is(notNullValue()));
    }

    @Test
    public void shouldIndexJsonDocumentWithId() {
        //given
        //when
        //then
        assertThat(documentAPI.index(getIndexName(), "1", "{\"title\":\"test title\"}"),is("1"));
    }

    @Test
    public void shouldDeleteSelectedDocument() {
        //given
        documentAPI.index(getIndexName(), "1", "{\"title\":\"test title\"}");
        //when
        //then
        assertThat(documentAPI.delete(getIndexName(), "1"), is(true));
    }

    @Test(expected = IndexNotFoundException.class)
    public void shouldGetIndexNotExist() {
        //given
        //when
        //then
        assertThat(documentAPI.delete(getIndexName(), "1"), is(true));
        fail("Delete operation should throw exception");
    }

    @Test(expected = DocumentNotFoundException.class)
    public void shouldGetDocumentNotExist() {
        //given
        indicesAPI.create(getIndexName());
        //when
        //then
        assertThat(documentAPI.delete(getIndexName(), "1"), is(true));
        fail("Delete operation should throw exception");
    }

    @Test
    public void shouldLoadDocument() {
        //given
        documentAPI.index(getIndexName(), "1", "{\"title\":\"test title\"}");
        //when
        //then
        String result = documentAPI.get(getIndexName(), "1", String.class);
        assertThat(result, containsString("\"title\":\"test title\""));
    }

    @Test(expected = DocumentNotFoundException.class)
    public void shouldReturnDocNotExistsForGet() {
        //given
        indicesAPI.create(getIndexName());
        //when
        //then
        String result = documentAPI.get(getIndexName(), "1", String.class);
        fail("Get operation should throw exception");
    }

}
