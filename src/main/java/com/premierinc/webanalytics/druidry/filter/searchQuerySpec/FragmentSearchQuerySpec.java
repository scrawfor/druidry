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

package com.premierinc.webanalytics.druidry.filter.searchQuerySpec;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class FragmentSearchQuerySpec extends SearchQuerySpec {

    private final static String FRAGMENT_SEARCH_QUERY_SPEC = "fragment";

    private List<String> values;
    private Boolean caseSensitive;

    public FragmentSearchQuerySpec(@NonNull List<String> values) {
        this(values, null);
    }

    public FragmentSearchQuerySpec(@NonNull List<String> values, Boolean isCaseSensitive) {
        this.type = FRAGMENT_SEARCH_QUERY_SPEC;
        this.values = values;
        this.caseSensitive = isCaseSensitive;
    }
}