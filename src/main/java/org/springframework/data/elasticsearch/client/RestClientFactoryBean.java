/*
 * Copyright 2015-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.elasticsearch.client;

import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.elasticsearch.client.rest.ClientImpl;

import java.util.Properties;

import static org.apache.commons.lang.StringUtils.split;

/**
 * RestClientFactoryBean
 *
 * @author Artur Konczak
 */

public class RestClientFactoryBean implements FactoryBean<Client>, InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(RestClientFactoryBean.class);
    private Client client;

    private String clusterNodes = "127.0.0.1:9200";
    static final String COMMA = ",";
    private Properties properties;

    @Override
    public Client getObject() throws Exception {
        if (client == null) {
            afterPropertiesSet();
        }
        return client;
    }

    @Override
    public Class<? extends RestClient> getObjectType() {
        return RestClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {
        try {
            logger.info("Closing ElasticSearch Rest client");
            if (client != null) {
                client.close();
            }
        } catch (final Exception e) {
            logger.error("Error closing ElasticSearch Rest client: ", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildClient();
    }

    protected void buildClient() throws Exception {
        client = new ClientImpl(split(clusterNodes, COMMA));
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
