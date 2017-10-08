package com.mello.dao;

import com.mello.domain.Seckill;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by MelloChan on 2017/10/8.
 * 配置spring与junit整合
 * spring-test junit 加载相应的ioc容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDaoTest {

    //注入dao实现类依赖
    @Resource
    private SecKillDao secKillDao;

    @Test
    public void reduceNumber() throws Exception {
        Date date=new Date();
        int updateCount=secKillDao.reduceNumber(1000L,date);
        System.out.println("updateCount="+updateCount);
    }

    @Test
    public void queryById() throws Exception {
        long id=1000;
        Seckill seckill=secKillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill>seckills=secKillDao.queryAll(0,100);
        for (Seckill s :
                seckills) {
            System.out.println(s);
        }
    }

}