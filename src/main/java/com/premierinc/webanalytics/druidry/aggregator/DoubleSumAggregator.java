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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Wither;

@Getter
@EqualsAndHashCode(callSuper = true)
public class DoubleSumAggregator extends DruidAggregator {

    private static final String DOUBLE_SUM_TYPE_AGGREGATOR = "doubleSum";
    private String fieldName;
    private String name;

    public DoubleSumAggregator(@NonNull String name, @NonNull String fieldName) {
        this.type = DOUBLE_SUM_TYPE_AGGREGATOR;
        this.name = name;
        this.fieldName = fieldName;
    }

    public DruidAggregator withName(String name) {
        return new DoubleSumAggregator(name, this.fieldName);
    }
}