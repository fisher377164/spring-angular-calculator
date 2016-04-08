package com.techmix.fisher.exeptions;

import org.springframework.http.HttpStatus;

/**
 * @author fisher
 * @since 4/8/16
 */
public abstract class BaseException extends Exception {
    protected HttpStatus status;

    public BaseException() {
    }

    public BaseException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public BaseException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public BaseException(HttpStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    public BaseException(HttpStatus status, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
