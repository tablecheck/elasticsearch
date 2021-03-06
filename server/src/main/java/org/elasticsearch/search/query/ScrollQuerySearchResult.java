/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.search.query;

import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.search.SearchPhaseResult;
import org.elasticsearch.search.SearchShardTarget;

import java.io.IOException;

import static org.elasticsearch.search.query.QuerySearchResult.readQuerySearchResult;

public final class ScrollQuerySearchResult extends SearchPhaseResult {

    private QuerySearchResult result;

    public ScrollQuerySearchResult() {
    }

    public ScrollQuerySearchResult(StreamInput in) throws IOException {
        readFrom(in);
    }

    public ScrollQuerySearchResult(QuerySearchResult result, SearchShardTarget shardTarget) {
        this.result = result;
        setSearchShardTarget(shardTarget);
    }

    @Override
    public void setSearchShardTarget(SearchShardTarget shardTarget) {
        super.setSearchShardTarget(shardTarget);
        result.setSearchShardTarget(shardTarget);
    }

    @Override
    public void setShardIndex(int shardIndex) {
        super.setShardIndex(shardIndex);
        result.setShardIndex(shardIndex);
    }

    @Override
    public QuerySearchResult queryResult() {
        return result;
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        SearchShardTarget shardTarget = new SearchShardTarget(in);
        result = readQuerySearchResult(in);
        setSearchShardTarget(shardTarget);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        getSearchShardTarget().writeTo(out);
        result.writeTo(out);
    }
}
