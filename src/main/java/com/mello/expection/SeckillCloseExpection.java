package com.mello.expection;

/**
 * 秒杀关闭异常
 * Created by MelloChan on 2017/10/9.
 */
public class SeckillCloseExpection extends SeckillExpection {
    public SeckillCloseExpection(String message) {
        super(message);
    }

    public SeckillCloseExpection(String message, Throwable cause) {
        super(message, cause);
    }
}
