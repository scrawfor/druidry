package com.premierinc.webanalytics.druidry.query.search;

import com.premierinc.webanalytics.druidry.SortingOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class SearchSortSpec {

    private SortingOrder type;

    public SearchSortSpec(SortingOrder sortingOrder) {
        this.type = sortingOrder;
    }
}
