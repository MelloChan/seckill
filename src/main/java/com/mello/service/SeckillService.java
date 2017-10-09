package com.mello.service;

import com.mello.domain.Seckill;
import com.mello.dto.Expose;
import com.mello.dto.SeckillExecution;
import com.mello.expection.RepeatSeckillExpection;
import com.mello.expection.SeckillCloseExpection;
import com.mello.expection.SeckillExpection;

import java.util.List;

/**
 * Created by MelloChan on 2017/10/9.
 * 业务秒杀服务接口:
 */
public interface SeckillService {
    /**
     * 获取秒杀产品列表
     * @return
     */
    List<Seckill>getSeckillList();

    /**
     * 获取单个秒杀产品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启则输出秒杀md5接口
     * 否则输出系统当前时间与秒杀开启时间
     * @param seckillId
     * @return
     */
    Expose exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀服务
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return 秒杀执行结果
     * @throws SeckillExpection
     * @throws RepeatSeckillExpection
     * @throws SeckillCloseExpection
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)throws SeckillExpection,RepeatSeckillExpection,SeckillCloseExpection;
}
