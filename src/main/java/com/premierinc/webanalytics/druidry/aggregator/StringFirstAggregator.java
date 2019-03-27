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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@EqualsAndHashCode(callSuper = true)
public class StringFirstAggregator extends DruidAggregator {
    private static final String STRING_FIRST_TYPE_AGGREGATOR = "stringFirst";
    private String fieldName;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int maxStringBytes;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean filterNullValues;

    @Builder
    public StringFirstAggregator(@NonNull String name, @NonNull String fieldName, int maxStringBytes, boolean filterNullValues) {
        this.type = STRING_FIRST_TYPE_AGGREGATOR;
        this.name = name;
        this.fieldName = fieldName;
        this.maxStringBytes = maxStringBytes;
        this.filterNullValues = filterNullValues;
    }

    public DruidAggregator withName(String name) {
        return new StringFirstAggregator(name, this.fieldName, this.maxStringBytes, this.filterNullValues);
    }
}
