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

package com.premierinc.webanalytics.druidry.aggregator;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.premierinc.webanalytics.druidry.filter.DruidFilter;
import com.premierinc.webanalytics.druidry.filter.TrueFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class FilteredAggregator extends DruidAggregator {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String FILTERED_AGGREGATOR_TYPE = "filtered";

    private DruidFilter filter;
    private DruidAggregator aggregator;
    private String name;

    public FilteredAggregator(@NonNull DruidFilter filter, @NonNull DruidAggregator aggregator) {
        this.type = FILTERED_AGGREGATOR_TYPE;
        this.filter = filter;
        this.aggregator = aggregator;
    }

    public FilteredAggregator(@NonNull DruidFilter filter, @NonNull DruidAggregator aggregator, String name) {
        this.type = FILTERED_AGGREGATOR_TYPE;
        this.filter = filter;
        this.aggregator = aggregator.withName(name);
        this.name = name;
    }

    public static void main(String[] args) throws Exception {
        DruidAggregator agg = new HyperUniqueAggregator("example","abc");
        DruidFilter filter = new TrueFilter();
        FilteredAggregator fagg = new FilteredAggregator(filter, agg, "abc:" +agg.getName());

        FilteredAggregator fagg2 = new FilteredAggregator(filter, agg, "def:" +agg.getName());
        ObjectMapper m = new ObjectMapper();
        System.out.println(m.writeValueAsString(fagg)+"\n\n"+m.writeValueAsString(fagg2));
    }

    public DruidAggregator withName(String name) {
        return new FilteredAggregator(filter, aggregator, name);
    }


}
