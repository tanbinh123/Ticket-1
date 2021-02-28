package com.woniuxy.cinema.exception;

public class SeatAvailableException extends ServiceException{
    public SeatAvailableException() {
    }

    public SeatAvailableException(String msg) {
        super(msg);
    }

    public SeatAvailableException(Throwable cause) {
        super(cause);
    }

    public SeatAvailableException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
