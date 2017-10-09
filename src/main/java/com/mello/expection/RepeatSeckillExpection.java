package com.mello.expection;

/**
 * 用户重复秒杀异常
 * Created by MelloChan on 2017/10/9.
 */
public class RepeatSeckillExpection extends SeckillExpection {
    public RepeatSeckillExpection(String message) {
        super(message);
    }

    public RepeatSeckillExpection(String message, Throwable cause) {
        super(message, cause);
    }
}
