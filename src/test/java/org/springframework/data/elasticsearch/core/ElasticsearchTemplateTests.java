/*
 * Copyright 2014-2017 the original author or authors.
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
package org.springframework.data.elasticsearch.core;

//import org.elasticsearch.action.get.MultiGetItemResponse;
//import org.elasticsearch.action.get.MultiGetResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.index.engine.DocumentMissingException;
//import org.elasticsearch.script.Script;
//import org.elasticsearch.script.ScriptType;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.sort.FieldSortBuilder;
//import org.elasticsearch.search.sort.SortOrder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.model.Mappings;
import org.springframework.data.elasticsearch.client.model.Property;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

//import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
//import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
//import static org.elasticsearch.index.query.QueryBuilders.*;


/**
 * @author Rizwan Idrees
 * @author Mohsin Husen
 * @author Franck Marchand
 * @author Abdul Mohammed
 * @author Kevin Leturc
 * @author Mason Chan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:elasticsearch-template-test.xml")
public class ElasticsearchTemplateTests {

	private final static String TEST_INDEX_NAME = "unit_test_elasticsearch_template_tests";

	@Autowired
	private ElasticsearchTemplate template;

	@Before
	public void before() {
		template.deleteIndex(TEST_INDEX_NAME);
		template.deleteIndex(BasicDocument.class);
	}

	@Test
	public void shouldCreateBasicIndexBaseOnIndexName() throws IOException {
		//given

		//when
		template.createIndex(TEST_INDEX_NAME);
		//then
		assertThat(template.indexExists(TEST_INDEX_NAME), is(true));
	}

	@Test
	public void shouldCreateBasicIndexBaseOnClass() throws IOException {
		//given

		//when
		template.createIndex(BasicDocument.class);
		//then
		assertThat(template.indexExists(BasicDocument.class), is(true));
	}

	@Test
	public void shouldCreateBasicIndexBaseOnClassWithMappings() throws IOException {
		//given

		//when
		template.createIndex(BasicDocument.class);
		template.putMapping(BasicDocument.class);
		//then
		assertThat(template.indexExists(BasicDocument.class), is(true));
		final Mappings mapping = template.getMapping(BasicDocument.class);
		assertThat(mapping.getProperties().size(), is(1));
		final Property title = mapping.getProperties().get("title");
		assertThat(title.getType(), is("text"));
	}


}
