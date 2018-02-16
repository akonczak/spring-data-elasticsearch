package org.springframework.data.elasticsearch.client;

import org.junit.Test;

import static org.junit.Assert.*;

public class RestClientFactoryBeanTest {

    @Test
    public void shouldBuildRestClient() throws Exception {
        //given
        RestClientFactoryBean factory = new RestClientFactoryBean();
        factory.setClusterNodes("localhost:9250");
        //when
        factory.afterPropertiesSet();

        //then
        assertNotNull(factory.getObject());
    }

}