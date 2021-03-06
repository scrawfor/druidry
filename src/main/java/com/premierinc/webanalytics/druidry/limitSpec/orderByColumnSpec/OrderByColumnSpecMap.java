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

package com.premierinc.webanalytics.druidry.limitSpec.orderByColumnSpec;

import com.premierinc.webanalytics.druidry.SortingOrder;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class OrderByColumnSpecMap extends OrderByColumnSpec {

    private static final String ASCENDING_ORDER = "ascending";
    private static final String DESCENDING_ORDER = "descending";

    private String dimension;
    private String direction;
    private SortingOrder dimensionOrder;

    public OrderByColumnSpecMap(String dimension, boolean isAscending, SortingOrder dimensionOrder) {
        this.dimension = dimension;
        this.direction = isAscending ? ASCENDING_ORDER : DESCENDING_ORDER;
        this.dimensionOrder = dimensionOrder;
    }
}
