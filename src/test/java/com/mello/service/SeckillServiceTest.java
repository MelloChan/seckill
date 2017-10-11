package com.mello.service;

import com.mello.dto.Expose;
import com.mello.dto.SeckillExecution;
import com.mello.utils.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * 秒杀服务接口集成测试
 * Created by MelloChan on 2017/10/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    @Resource
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        LOG.info("list={}", seckillService.getSeckillList());
    }

    @Test
    public void getById() throws Exception {
        long id = 1000;
        LOG.info("seckill={}", seckillService.getById(id));
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1001;
        LOG.info("expose={}",seckillService.exportSeckillUrl(id));
    }

    @Test
    public void executeSeckill() throws Exception {
        long id1 = 1005;
        long usePhone = 13192265851L;
        String md5 = seckillService.exportSeckillUrl(id1).getMd5();
        LOG.info("result={}",seckillService.executeSeckill(id1, usePhone, md5));
    }

    @Test
    public void secKillLogic() throws Exception {
        long id = 1002;
        Expose expose = seckillService.exportSeckillUrl(id);
        if (expose.isExposed()) {
            LOG.info("expose={}", expose);
            long usePhone = 13192265851L;
            String md5 = expose.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSeckill(id, usePhone, md5);
            LOG.info("result={}", seckillExecution);
        } else {
            LOG.warn("expose={}", expose);
        }
    }

}