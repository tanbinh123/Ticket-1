package com.woniuxy.auth.exception;

public class TelExistedException extends ServiceException{
    public TelExistedException() {
    }

    public TelExistedException(String msg) {
        super(msg);
    }

    public TelExistedException(Throwable cause) {
        super(cause);
    }

    public TelExistedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
