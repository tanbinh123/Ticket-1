package com.woniuxy.cinema.exception;

public class CinemaInfoExistException extends ServiceException{
    public CinemaInfoExistException() {
    }

    public CinemaInfoExistException(String msg) {
        super(msg);
    }

    public CinemaInfoExistException(Throwable cause) {
        super(cause);
    }

    public CinemaInfoExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
