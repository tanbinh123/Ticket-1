package com.woniuxy.cinema.exception;

public class HallExistException extends ServiceException{
    public HallExistException() {
    }

    public HallExistException(String msg) {
        super(msg);
    }

    public HallExistException(Throwable cause) {
        super(cause);
    }

    public HallExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
