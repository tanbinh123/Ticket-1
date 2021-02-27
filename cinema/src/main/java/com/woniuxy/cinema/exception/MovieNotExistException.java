package com.woniuxy.cinema.exception;

public class MovieNotExistException extends ServiceException{
    public MovieNotExistException() {
    }

    public MovieNotExistException(String msg) {
        super(msg);
    }

    public MovieNotExistException(Throwable cause) {
        super(cause);
    }

    public MovieNotExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
