package com.premierinc.webanalytics.druidry.extensions.datasketches.aggregator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import com.premierinc.webanalytics.druidry.aggregator.DruidAggregator;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HLLSketchMergeAggregator extends DruidAggregator {

    private static final String HLL_SKETCH_TYPE_AGGREGATOR = "HLLSketchMerge";

    private String fieldName;

    private Integer lgK;

    private String tgtHllType;

    @Builder
    public HLLSketchMergeAggregator(
            @NonNull String name,
            @NonNull String fieldName,
            Integer lgK,
            String tgtHllType
    ) {
        this.type = HLL_SKETCH_TYPE_AGGREGATOR;
        this.name = name;
        this.fieldName = fieldName;
        this.lgK = lgK;
        this.tgtHllType = tgtHllType;

        if (lgK != null) {
            Preconditions.checkArgument(LongMath.isPowerOfTwo(lgK), "lgk must be a power of 2");
        }
    }


    public DruidAggregator withName(String name) {
        return HLLSketchMergeAggregator.builder()
                .name(name)
                .fieldName(this.fieldName)
                .lgK(this.lgK)
                .tgtHllType(this.tgtHllType)
                .build();
    }

}