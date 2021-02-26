package com.woniuxy.movie.exception;

public class TypeNotExistException extends ServiceException{
    public TypeNotExistException() {
    }

    public TypeNotExistException(String msg) {
        super(msg);
    }

    public TypeNotExistException(Throwable cause) {
        super(cause);
    }

    public TypeNotExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
