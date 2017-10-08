package com.mello.dao;

import com.mello.domain.Seckill;
import org.apache.ibatis.annotations.Param;

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
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 产品查询
     *
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * 多个参数时要指定参数名 否则mybatis无法识别[因为在运行时形参被翻译为 arg0 arg1]
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);
}
