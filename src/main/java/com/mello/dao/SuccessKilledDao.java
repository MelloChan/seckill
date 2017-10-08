package com.mello.dao;

import com.mello.domain.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by MelloChan on 2017/10/7.
 * 秒杀成功记录类DAO接口
 */
public interface SuccessKilledDao {
    /**
     * 插入购买明细,过滤重复秒杀
     * @param seckillId
     * @param userPhone
     * @return 0:插入失败  1:插入成功
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /**
     * 根据id查询秒杀成功记录,并携带秒杀产品对象实体类
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
