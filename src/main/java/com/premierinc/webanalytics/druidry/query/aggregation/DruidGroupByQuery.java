/*
 * Copyright (c) 2017-present, Red Brick Lane Marketing Solutions Pvt. Ltd.
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.premierinc.webanalytics.druidry.query.aggregation;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.premierinc.webanalytics.druidry.datasource.Datasource;
import com.premierinc.webanalytics.druidry.filter.DruidFilter;
import com.premierinc.webanalytics.druidry.Context;
import com.premierinc.webanalytics.druidry.Interval;
import com.premierinc.webanalytics.druidry.aggregator.DruidAggregator;
import com.premierinc.webanalytics.druidry.dimension.DruidDimension;

import java.util.List;

import com.premierinc.webanalytics.druidry.granularity.Granularity;
import com.premierinc.webanalytics.druidry.limitSpec.DefaultLimitSpec;
import com.premierinc.webanalytics.druidry.postAggregator.DruidPostAggregator;
import com.premierinc.webanalytics.druidry.query.QueryType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class DruidGroupByQuery extends DruidAggregationQuery {

    private DefaultLimitSpec limitSpec;
    private String having;

    @NonNull
    private List<DruidDimension> dimensions;

    @Builder
    private DruidGroupByQuery(@NonNull Datasource dataSource,
                              @NonNull List<DruidDimension> dimensions,
                              DefaultLimitSpec limitSpec,
                              @NonNull Granularity granularity,
                              DruidFilter filter,
                              List<DruidAggregator> aggregators,
                              List<DruidPostAggregator> postAggregators,
                              @NonNull List<Interval> intervals,
                              Context context) {

        this.queryType = QueryType.GROUP_BY;
        this.dataSource = dataSource;
        this.dimensions = dimensions;
        this.limitSpec = limitSpec;
        this.granularity = granularity;
        this.filter = filter;
        this.aggregations = aggregators;
        this.postAggregations = postAggregators;
        this.intervals = intervals;
        this.context = context;
    }
}