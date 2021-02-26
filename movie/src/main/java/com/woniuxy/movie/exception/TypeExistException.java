package com.woniuxy.movie.exception;

public class TypeExistException extends ServiceException{
    public TypeExistException() {
    }

    public TypeExistException(String msg) {
        super(msg);
    }

    public TypeExistException(Throwable cause) {
        super(cause);
    }

    public TypeExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
