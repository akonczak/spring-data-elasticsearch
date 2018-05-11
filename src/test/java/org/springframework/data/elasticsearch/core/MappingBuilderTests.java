/*
 * Copyright 2013-2016 the original author or authors.
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

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.springframework.data.elasticsearch.TestBase;
import org.springframework.data.elasticsearch.client.model.Mappings;
import org.springframework.data.elasticsearch.client.model.Property;
import org.springframework.data.old.entities.*;

//import org.springframework.data.elasticsearch.entities.*;

/**
 * @author Stuart Stevenson
 * @author Jakub Vavrik
 * @author Mohsin Husen
 * @author Keivn Leturc
 */
public class MappingBuilderTests extends TestBase{

	private MappingBuilder mappingBuilder = new MappingBuilder();

	@Test
	public void testInfiniteLoopAvoidance() throws IOException {
		Mappings mappings = MappingBuilder.buildMapping(SampleTransientEntity.class, "mapping", "id", null);
		assertThat(mappings.getProperties().size(), is(1));
		final Property message = mappings.getProperties().get("message");
		assertThat(message.getStore(), is(true));
		assertThat(message.getType(), is("text"));
		assertThat(message.getIndex(), is(false));
		assertThat(message.getAnalyzer(), is("standard"));
	}

	@Test
	public void shouldUseValueFromAnnotationType() throws IOException {
		//Given
		//When
		Mappings mappings = MappingBuilder.buildMapping(StockPrice.class, "mapping", "id", null);

		//Then
		final Property message = mappings.getProperties().get("price");
		assertThat(message.getStore(), is(false));
		assertThat(message.getType(), is("double"));
		System.out.println(mappings);
	}

	/*
	 * DATAES-76
	 */
	@Test
	public void shouldBuildMappingWithSuperclass() throws IOException {
		final String expected = "{\"mapping\":{\"properties\":{\"message\":{\"store\":true,\"" +
				"type\":\"text\",\"index\":false,\"analyzer\":\"standard\"}" +
				",\"createdDate\":{\"store\":false," +
				"\"type\":\"date\",\"index\":false}}}}";

		Mappings mappings = MappingBuilder.buildMapping(SampleInheritedEntity.class, "mapping", "id", null);
		assertThat(mappings.getProperties().size(), is(2));
		Property message = mappings.getProperties().get("message");
		assertThat(message.getType(), is("text"));
		assertThat(message.getStore(), is(true));
		assertThat(message.getIndex(), is(false));
		assertThat(message.getAnalyzer(), is("standard"));

		Property createdDate = mappings.getProperties().get("createdDate");
		assertThat(createdDate.getType(), is("date"));
		assertThat(createdDate.getStore(), is(false));
		assertThat(createdDate.getIndex(), is(false));
		assertThat(createdDate.getFielddata(), is(nullValue()));
	}

	/**
	 * TODO ako: move this test
	 * DATAES-76
	 */
	/*
	@Test
	public void shouldAddSampleInheritedEntityDocumentToIndex() throws IOException {
		//Given

		//When
		elasticsearchTemplate.deleteIndex(SampleInheritedEntity.class);
		elasticsearchTemplate.createIndex(SampleInheritedEntity.class);
		elasticsearchTemplate.putMapping(SampleInheritedEntity.class);
		Date createdDate = new Date();
		String message = "msg";
		String id = "abc";
		elasticsearchTemplate.index(new SampleInheritedEntityBuilder(id).createdDate(createdDate).message(message).buildIndex());
		elasticsearchTemplate.refresh(SampleInheritedEntity.class);

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
		List<SampleInheritedEntity> result = elasticsearchTemplate.queryForList(searchQuery, SampleInheritedEntity.class);
		//Then
		assertThat(result.size(), is(1));
		SampleInheritedEntity entry = result.get(0);
		assertThat(entry.getCreatedDate(), is(createdDate));
		assertThat(entry.getMessage(), is(message));
	}*/
	@Test
	public void shouldBuildMappingsForGeoPoint() throws IOException {
		//given
		//when
		Mappings mappings = MappingBuilder.buildMapping(GeoEntity.class, "mapping", "id", null);
		//then
		assertThat(mappings.getProperties().size(), is(4));
		Property pointA = mappings.getProperties().get("pointA");
		assertThat(pointA.getType(), is("geo_point"));

		Property pointB = mappings.getProperties().get("pointB");
		assertThat(pointB.getType(), is("geo_point"));

		Property pointC = mappings.getProperties().get("pointC");
		assertThat(pointC.getType(), is("geo_point"));

		Property pointD = mappings.getProperties().get("pointD");
		assertThat(pointD.getType(), is("geo_point"));
	}

	/**
	 * TODO ako: move this test
	 * DATAES-260 - StacOverflow when two reverse relationship.
	 */
	/*
	@Test
	public void shouldHandleReverseRelationship() {
		//given
		elasticsearchTemplate.createIndex(User.class);
		elasticsearchTemplate.putMapping(User.class);
		elasticsearchTemplate.createIndex(Group.class);
		elasticsearchTemplate.putMapping(Group.class);
		//when

		//then

	}*/
	@Test
	public void shouldCreateMappingsForBooks() throws IOException {
		//given
		//when
		Mappings mappings = MappingBuilder.buildMapping(Book.class, "remove_me", "id", null);

		//then
		assertThat(mappings.getProperties().size(), is(2));
		Property author = mappings.getProperties().get("author");
		assertThat(author.getType(), is("object"));
		Property buckets = mappings.getProperties().get("buckets");
		assertThat(buckets.getType(), is("nested"));
	}
}
