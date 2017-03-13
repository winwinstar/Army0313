package com.wss.study.army.service;

import com.wss.study.army.dto.Exposer;
import com.wss.study.army.dto.SeckillExecution;
import com.wss.study.army.entity.Seckill;
import com.wss.study.army.exception.RepeatKillException;
import com.wss.study.army.exception.SeckillCloseException;
import com.wss.study.army.exception.SeckillException;

import java.util.List;

/**
 * Created by Administrator on 2017-01-22.
 * 三个方面：方法定义粒度，参数，返回类型（类型、异常）
 */
public interface SeckillService {
    List<Seckill> getSeckillList();
    Seckill getById(long seckillId);
    Exposer exportSeckillUrl(long seckillId);
    SeckillExecution excuteSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;
}
