package com.techmix.fisher.exeptions;

import org.springframework.http.HttpStatus;

/**
 * @author fisher
 * @since 4/8/16
 */
public class DivisionZeroException extends BaseException {

    private static final HttpStatus status = HttpStatus.BAD_REQUEST;

    public DivisionZeroException(String message) {
        super(status, message);
    }
}
