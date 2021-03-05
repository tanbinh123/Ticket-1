package com.woniuxy.auth.exception;

public class AccountExistedException extends ServiceException{
    public AccountExistedException() {
    }

    public AccountExistedException(String msg) {
        super(msg);
    }

    public AccountExistedException(Throwable cause) {
        super(cause);
    }

    public AccountExistedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
