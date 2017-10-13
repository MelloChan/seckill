package com.mello.service.impl;

import com.mello.dao.SecKillDao;
import com.mello.dao.SuccessKilledDao;
import com.mello.domain.Seckill;
import com.mello.domain.SuccessKilled;
import com.mello.dto.Expose;
import com.mello.dto.SeckillExecution;
import com.mello.enums.SeckillStateEnum;
import com.mello.expection.RepeatSeckillExpection;
import com.mello.expection.SeckillCloseExpection;
import com.mello.expection.SeckillExpection;
import com.mello.service.SeckillService;
import com.mello.utils.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 秒杀服务接口实现
 * Created by MelloChan on 2017/10/9.
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final SecKillDao secKillDao;
    private final SuccessKilledDao successKilledDao;
    private final static String SLAT = "sdasdadasd54$5646)(2opkopSQE.";

    @Autowired
    public SeckillServiceImpl(SecKillDao secKillDao, SuccessKilledDao successKilledDao) {
        this.secKillDao = secKillDao;
        this.successKilledDao = successKilledDao;
    }

    /**
     * 获取秒杀列表
     *
     * @return 秒杀商品对象列表
     */
    @Override
    public List<Seckill> getSeckillList() {
        return secKillDao.queryAll(0, 100);
    }

    /**
     * 获取单个秒杀对象
     *
     * @param seckillId 秒杀商品id
     * @return 秒杀商品对象数据
     */
    @Override
    public Seckill getById(long seckillId) {
        return secKillDao.queryById(seckillId);
    }

    /**
     * 获取秒杀接口
     *
     * @param seckillId 秒杀商品id
     * @return 秒杀接口dto
     */
    @Override
    public Expose exportSeckillUrl(long seckillId) {
        Seckill seckill = getById(seckillId);
        if (seckill == null) {
            return new Expose(false, seckillId);
        }
        long nowTime = new Date().getTime();
        if (nowTime < seckill.getStartTime().getTime() || nowTime > seckill.getEndTime().getTime()) {
            return new Expose(false, seckillId, nowTime, seckill.getStartTime().getTime(), seckill.getEndTime().getTime());
        }
        String md5 = DigestUtils.getMD5(seckillId + "/" + SLAT);
        return new Expose(true, md5, seckillId);
    }

    /**
     * 执行秒杀
     *
     * @param seckillId 秒杀商品id
     * @param userPhone 用户手机
     * @param md5 加密的秒杀接口
     * @return 秒杀执行结果
     * @throws SeckillExpection 秒杀运行时异常
     * @throws RepeatSeckillExpection 重复秒杀异常
     * @throws SeckillCloseExpection 秒杀关闭异常
     */
    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillExpection{

        if (md5 == null || !DigestUtils.getMD5(seckillId + "/" + SLAT).equals(md5)) {
            LOG.info("md5:"+md5);
            throw new SeckillExpection("secKill data rewrite"); //秒杀数据被重写
        }
        try {
            //执行秒杀 插入数据到秒杀记录表
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                //插入失败 用户重复插入秒杀
                throw new RepeatSeckillExpection("secKill repeated");
            } else {
                //更新商品库存
                int updateCount = secKillDao.reduceNumber(seckillId, new Date());
                if (updateCount <= 0) {
                    //更新库存失败 回滚数据
                    throw new SeckillCloseExpection("secKill is closed");
                } else {
                    // 秒杀成功 得到成功插入的数据 并返回成功秒杀的对象信息
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    LOG.debug("successKilled={}",successKilled);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseExpection | RepeatSeckillExpection e1){
            throw  e1;
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            throw new SeckillExpection("secKill inner error:"+e.getMessage());
        }
    }
}
