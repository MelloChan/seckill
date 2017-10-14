package com.mello.dao.cache;

import com.mello.dao.SecKillDao;
import com.mello.domain.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * redis单元测试
 * Created by MelloChan on 2017/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class RedisDaoTest {
    @Resource
    private RedisDao redisDao;
    @Resource
    private SecKillDao secKillDao;

    @Test
    public void getSeckill() throws Exception {
        Seckill seckill=redisDao.getSeckill(1001);
        if(seckill==null){
            seckill=secKillDao.queryById(1001);
            if(seckill!=null){
                String result=redisDao.putSeckill(seckill);
                System.out.println(result);
                seckill=redisDao.getSeckill(1001);
                System.out.println(seckill);
            }
        }
    }

    @Test
    public void putSeckill() throws Exception {
    }

}