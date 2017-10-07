package com.mello.domain;

import java.util.Date;

/**
 * Created by MelloChan on 2017/10/7.
 */
public class SuccessKilled {
    /*
    秒杀产品id
     */
    private long seckillId;
    /*
    用户手机号
     */
    private long userPhone;
    /*
    秒杀状态
     */
    private short state;
    /*
    秒杀时间
     */
    private Date createTime;
    /*
    一个秒杀产品可拥有多个秒杀记录
     */
    private Seckill seckill;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", seckill=" + seckill +
                '}';
    }
}
