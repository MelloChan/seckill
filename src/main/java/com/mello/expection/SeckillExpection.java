package com.mello.expection;

/**
 * 统一秒杀异常
 * Created by MelloChan on 2017/10/9.
 */
public class SeckillExpection extends RuntimeException {
    public SeckillExpection(String message) {
        super(message);
    }

    public SeckillExpection(String message, Throwable cause) {
        super(message, cause);
    }
}
