package com.mello.dao;

import com.mello.domain.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by MelloChan on 2017/10/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        /**
         * 首次执行 insertCount=1;
         * 第二次 insertCount=0
         * ignore保证了主键重复键入时不报错而返回0
         */
        long id=1001L;
        long userPhone=13192265851L;
        int insertCount=successKilledDao.insertSuccessKilled(id,userPhone);
        System.out.println("insertCount="+insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id=1001L;
        long userPhone=13192265851L;
        SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(id,userPhone);
        System.out.println(successKilled);
    }

}