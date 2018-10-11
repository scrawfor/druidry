package com.premierinc.webanalytics.druidry.client.exception;

import com.premierinc.webanalytics.druidry.client.DruidError;
import lombok.Getter;

public class QueryException extends DruidryException {

    @Getter
    private DruidError druidError;

    public QueryException(Exception e) {
        super(e);
    }

    public QueryException(String message) {
        super(message);
    }

    public QueryException(DruidError error) {
        super(error.getErrorMessage());
        this.druidError = error;
    }
}
