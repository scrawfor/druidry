package com.premierinc.webanalytics.druidry.extensions.datasketches.postAggregator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import com.premierinc.webanalytics.druidry.postAggregator.DruidPostAggregator;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThetaSketchSetOpPostAggregator extends DruidPostAggregator {

    private static final String THETA_SKETCH_SET_OP_POST_AGGREGATOR_TYPE = "thetaSketchSetOp";

    @JsonProperty("func")
    private ThetaSketchFunction function;
    private List<DruidPostAggregator> fields;
    private Long size;

    @Builder
    private ThetaSketchSetOpPostAggregator(@NonNull String name,
                                           @NonNull ThetaSketchFunction function,
                                           @NonNull List<DruidPostAggregator> fields,
                                           Long size) {
        this.type = THETA_SKETCH_SET_OP_POST_AGGREGATOR_TYPE;
        this.name = name;
        this.function = function;
        this.fields = fields;
        this.size = size;
    }

}
