package com.wss.study.army.exception;

/**
 * 运行期异常,springmvc声明式失误异常只支持运行期异常
 * Created by Administrator on 2017-01-22.
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
