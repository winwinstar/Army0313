package com.wss.study.army.exception;

/**秒杀关闭异常
 * Created by Administrator on 2017-01-22.
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
