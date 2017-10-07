package com.mello.dao;

import com.mello.domain.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by MelloChan on 2017/10/7.
 * 秒杀产品类DAO接口
 */
public interface SecKillDao {
    /**
     * 减库存
     *
     * @param seckillId
     * @param killTime
     * @return >=1:表示更新的记录行数 0:更新失败
     */
    int reduceNumber(long seckillId, Date killTime);

    /**
     * 产品查询
     *
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     *
     * @param offet
     * @param limit
     * @return
     */
    List<Seckill> queryAll(int offet, int limit);
}
