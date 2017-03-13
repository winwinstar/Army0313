package com.wss.study.army.service;

import com.wss.study.army.dto.Exposer;
import com.wss.study.army.dto.SeckillExecution;
import com.wss.study.army.entity.Seckill;
import com.wss.study.army.exception.RepeatKillException;
import com.wss.study.army.exception.SeckillCloseException;
import com.wss.study.army.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017-01-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:spring/spring-dao.xml",
    "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1001;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("expore={}",exposer);
            long phone = 13699545496L;
            String md5 = exposer.getMd5();

            try {
                SeckillExecution seckillExecution = seckillService.excuteSeckill(id,phone,md5);
                logger.info("seckillExecution={}",seckillExecution);
            }catch (RepeatKillException e1){
                logger.info(e1.getMessage());
            }catch (SeckillCloseException e2){
                logger.info(e2.getMessage());
            }

        }else{
            logger.warn("exposer={}",exposer);
        }

        //Exposer{exposed=true, md5='2431bb78c4d776528666cb3646a8aa7d', seckillId=1000, now=0, start=0, end=0}
    }
}