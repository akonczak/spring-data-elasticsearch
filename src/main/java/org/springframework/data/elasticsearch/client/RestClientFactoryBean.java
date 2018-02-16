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

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.apache.commons.lang.StringUtils.*;

/**
 * RestClientFactoryBean
 *
 * @author Artur Konczak
 */

public class RestClientFactoryBean implements FactoryBean<RestClient>, InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(RestClientFactoryBean.class);
    private RestClient client;

    private String clusterNodes = "127.0.0.1:9200";
    private Properties properties;
    static final String COLON = ":";
    static final String COMMA = ",";

    @Override
    public RestClient getObject() throws Exception {
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
        List<HttpHost> nodes = new ArrayList<>();
        for (String clusterNode : split(clusterNodes, COMMA)) {
            String hostName = substringBeforeLast(clusterNode, COLON);
            String port = substringAfterLast(clusterNode, COLON);

            Assert.hasText(hostName, "[Assertion failed] missing host name in 'clusterNodes'");
            Assert.hasText(port, "[Assertion failed] missing port in 'clusterNodes'");

            logger.info("adding cluster node : " + clusterNode);
            nodes.add(new HttpHost(hostName, Integer.parseInt(port), "http"));
        }

        client = RestClient.builder(nodes.toArray(new HttpHost[nodes.size()])).build();
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
