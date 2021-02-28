package com.woniuxy.cinema.exception;

public class SeatUnavailableException extends ServiceException{
    public SeatUnavailableException() {
    }

    public SeatUnavailableException(String msg) {
        super(msg);
    }

    public SeatUnavailableException(Throwable cause) {
        super(cause);
    }

    public SeatUnavailableException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
