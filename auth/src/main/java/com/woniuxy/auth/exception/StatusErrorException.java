package com.woniuxy.auth.exception;

public class StatusErrorException extends ServiceException{
    public StatusErrorException() {
    }

    public StatusErrorException(String msg) {
        super(msg);
    }

    public StatusErrorException(Throwable cause) {
        super(cause);
    }

    public StatusErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
