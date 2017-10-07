package com.mello.domain;

import java.util.Date;

/**
 * Created by MelloChan on 2017/10/7.
 * 秒杀库存表
 */
public class Seckill {
    /*
    秒杀产品id
     */
    private long seckillId;
    /*
    秒杀产品名称
     */
    private String name;
    /*
    产品库存
     */
    private int number;
    /*
    秒杀开始时间
     */
    private Date startTime;
    /*
    秒杀结束时间
     */
    private Date endTime;
    /*
    秒杀产品创建时间
     */
    private Date createTime;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Seckill{" +
                "seckillId=" + seckillId +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                '}';
    }
}
