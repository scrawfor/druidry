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

package com.premierinc.webanalytics.druidry.extractionFunctions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.premierinc.webanalytics.druidry.granularity.Granularity;

import java.text.SimpleDateFormat;
import java.util.Locale;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeFormatExtractionFunction extends ExtractionFunction {

    private SimpleDateFormat format;

    private Locale locale;
    private Granularity granularity;
    private Boolean asMillis;

    //    TODO: search for a timezone library
    private String timeZone;

    @Builder
    private TimeFormatExtractionFunction(SimpleDateFormat format, Locale locale, Granularity granularity, String
            timeZone, Boolean asMillis) {
        this.type = TIME_FORMAT_TYPE;
        this.format = format;
        this.locale = locale;
        this.granularity = granularity;
        this.timeZone = timeZone;
        this.asMillis = asMillis;

    }

    public String getLocale() {
        return locale == null ? null : locale.toLanguageTag();
    }

    public String getFormat() {
        return format == null ? null : format.toPattern();
    }
}
