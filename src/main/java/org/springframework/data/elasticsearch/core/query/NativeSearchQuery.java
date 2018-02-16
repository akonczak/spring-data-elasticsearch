/*
 * Copyright 2013-2017 the original author or authors.
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
package org.springframework.data.elasticsearch.core.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * NativeSearchQuery
 *
 * @author Rizwan Idrees
 * @author Mohsin Husen
 * @author Artur Konczak
 */
public class NativeSearchQuery extends AbstractQuery implements SearchQuery {

	private QueryBuilder query;
	private QueryBuilder filter;
	private List<SortBuilder> sorts;
    private final List<ScriptField> scriptFields = new ArrayList<>();
	private List<IndexBoost> indicesBoost;


	public NativeSearchQuery(QueryBuilder query) {
		this.query = query;
	}

	public NativeSearchQuery(QueryBuilder query, QueryBuilder filter) {
		this.query = query;
		this.filter = filter;
	}

	public NativeSearchQuery(QueryBuilder query, QueryBuilder filter, List<SortBuilder> sorts) {
		this.query = query;
		this.filter = filter;
		this.sorts = sorts;
	}

	public QueryBuilder getQuery() {
		return query;
	}

	public QueryBuilder getFilter() {
		return filter;
	}

	public List<SortBuilder> getElasticsearchSorts() {
		return sorts;
	}

    @Override
    public List<ScriptField> getScriptFields() { return scriptFields; }

    public void setScriptFields(List<ScriptField> scriptFields) {
        this.scriptFields.addAll(scriptFields);
    }

    public void addScriptField(ScriptField... scriptField) {
        scriptFields.addAll(Arrays.asList(scriptField));
    }

	@Override
	public List<IndexBoost> getIndicesBoost() {
		return indicesBoost;
	}

	public void setIndicesBoost(List<IndexBoost> indicesBoost) {
		this.indicesBoost = indicesBoost;
	}

}
