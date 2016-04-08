package com.techmix.fisher.exeptions;

import org.springframework.http.HttpStatus;

/**
 * @author fisher
 * @since 4/8/16
 */
public class NoSuchOperationException extends BaseException {

    private static final HttpStatus status = HttpStatus.NOT_FOUND;

    public NoSuchOperationException(int operationId) {
        super(status, "No such operation with id: " + operationId);
    }

    public NoSuchOperationException(String operation) {
        super(status, "No such operation: " + operation);
    }
}
