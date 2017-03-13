package com.wss.study.army.exception;

/**
 * Created by Administrator on 2017-01-22.
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
