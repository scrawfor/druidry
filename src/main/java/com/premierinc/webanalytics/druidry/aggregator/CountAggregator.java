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
import lombok.NonNull;
import lombok.experimental.Wither;

@EqualsAndHashCode(callSuper = true)
public class CountAggregator extends DruidAggregator {

    private static final String COUNT_TYPE_AGGREGATOR = "count";

    public CountAggregator(@NonNull String name) {
        this.type = COUNT_TYPE_AGGREGATOR;
        this.name = name;
    }

    @Override
    public DruidAggregator withName(String name) {
        return new CountAggregator(name);
    }
}