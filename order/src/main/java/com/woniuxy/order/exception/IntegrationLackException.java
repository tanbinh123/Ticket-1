package com.woniuxy.order.exception;

public class IntegrationLackException extends ServiceException{
    public IntegrationLackException() {
    }

    public IntegrationLackException(String msg) {
        super(msg);
    }

    public IntegrationLackException(Throwable cause) {
        super(cause);
    }

    public IntegrationLackException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
