package com.wss.study.army.service.impl;

import com.wss.study.army.dao.SeckillDao;
import com.wss.study.army.dao.SuccessKilledDao;
import com.wss.study.army.dto.Exposer;
import com.wss.study.army.dto.SeckillExecution;
import com.wss.study.army.entity.Seckill;
import com.wss.study.army.entity.SuccessKilled;
import com.wss.study.army.enums.SeckillStateEnum;
import com.wss.study.army.exception.RepeatKillException;
import com.wss.study.army.exception.SeckillCloseException;
import com.wss.study.army.exception.SeckillException;
import com.wss.study.army.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017-01-22.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    private final String slat = "wieiahjd*&*%.;~df~_rtyrtydfg+_(hdhd(fdghfgh";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,10);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if(seckill == null){
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //转化特定字符串的过程
        String md5 = getMd5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getMd5(long seckillId){
        String base = seckillId + "/" +slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    @Transactional
    /*
    *使用注解控制事物方法
     */
    public SeckillExecution excuteSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if(md5 == null || !md5.equals(getMd5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        Date nowTime = new Date();
        int updateCount = seckillDao.reduceNumber(seckillId,nowTime);

        try{
            if(updateCount <= 0){
                throw new SeckillCloseException("seckill closed");
            }else{
                int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
                if(insertCount <= 0){
                    throw new RepeatKillException("seckill repeated");
                }else{
                    SuccessKilled successKilled = successKilledDao.queryByIdWidthSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
                }
            }
        }catch (SeckillCloseException e1){
            throw e1;
        }catch (SeckillException e2){
            throw e2;
        }
        catch(Exception e){
            logger.error(e.getMessage(),e);
            throw new SeckillException("seckill inner error"+e.getMessage());
        }
    }
}
