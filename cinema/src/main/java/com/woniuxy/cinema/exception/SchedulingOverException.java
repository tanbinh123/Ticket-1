package com.woniuxy.cinema.exception;

public class SchedulingOverException extends ServiceException{
    public SchedulingOverException() {
    }

    public SchedulingOverException(String msg) {
        super(msg);
    }

    public SchedulingOverException(Throwable cause) {
        super(cause);
    }

    public SchedulingOverException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
