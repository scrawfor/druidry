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

package com.premierinc.webanalytics.druidry.query.search;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import com.premierinc.webanalytics.druidry.Interval;
import com.premierinc.webanalytics.druidry.filter.DruidFilter;
import com.premierinc.webanalytics.druidry.filter.searchQuerySpec.SearchQuerySpec;
import com.premierinc.webanalytics.druidry.query.DruidQuery;
import com.premierinc.webanalytics.druidry.Context;
import com.premierinc.webanalytics.druidry.SortingOrder;
import com.premierinc.webanalytics.druidry.dimension.DruidDimension;
import com.premierinc.webanalytics.druidry.granularity.Granularity;
import com.premierinc.webanalytics.druidry.query.QueryType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class DruidSearchQuery extends DruidQuery {

    private Granularity granularity;
    private DruidFilter filter;
    private Integer limit;
    private List<Interval> intervals;
    private List<DruidDimension> searchDimensions;
    private SearchQuerySpec query;
    private SearchSortSpec sort;

    @Builder
    private DruidSearchQuery(@NonNull String dataSource,
                             @NonNull Granularity granularity,
                             DruidFilter filter,
                             Integer limit,
                             @NonNull List<Interval> intervals,
                             List<DruidDimension> searchDimensions,
                             @NonNull SearchQuerySpec query,
                             SortingOrder sort,
                             Context context) {

        this.queryType = QueryType.SEARCH;
        this.dataSource = dataSource;
        this.granularity = granularity;
        this.filter = filter;
        this.limit = limit;
        this.intervals = intervals;
        this.searchDimensions = searchDimensions;
        this.query = query;
        if (sort != null) {
            this.sort = new SearchSortSpec(sort);
        }
        this.context = context;
    }
}
