package org.springframework.data.elasticsearch.client.rest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.elasticsearch.TestBase;
import org.springframework.data.elasticsearch.client.*;
import org.springframework.data.elasticsearch.client.model.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class SearchAPIImplTest extends TestBase{


    JsonMapper mapper;
    RestClientFactoryBean factoryBean = new RestClientFactoryBean();
    Client client;
    IndicesAPI indicesAPI;
    DocumentAPI documentAPI;
    SearchAPI searchAPI;

    @Before
    public void init() throws Exception {
        mapper = new JsonMapperImpl();
        factoryBean.setClusterNodes("localhost:9260");
        client = factoryBean.getObject();
        indicesAPI = client.getIndicesAPI();
        documentAPI = client.getDocumentAPI();
        searchAPI = client.getSearchAPI();
        if (indicesAPI.isExists(getIndexName())) {
            indicesAPI.delete(getIndexName());
        }
        indicesAPI.create(getIndexName());
        documentAPI.index(getIndexName(), "1", "{\"title\":\"test title\"}");
        indicesAPI.refresh(getIndexName());
    }

    @Test
    public void shouldSearchUsingJsonQuery() {
        //given
        //when
        //then
        SearchResponse searchResponse = searchAPI.searchRaw(getIndexName(), "{\n" +
                "    \"query\": {\n" +
                "        \"bool\" : {\n" +
                "            \"must\" : {\n" +
                "                \"query_string\" : {\n" +
                "                    \"query\" : \"test\"\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}");
        assertThat(searchResponse, is(notNullValue()));
        assertThat(searchResponse.getShards().getSkipped(), is(0));
        assertThat(searchResponse.getShards().getFailed(), is(0));
        assertThat(searchResponse.isTimedOut(), is(false));
        assertThat(searchResponse.getHits().getTotal(), is(1L));
    }

}
