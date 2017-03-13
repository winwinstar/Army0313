package com.wss.study.army.dao;

import com.wss.study.army.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017-01-15.
 */
public interface SeckillDao {
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
    Seckill queryById(long seckillId);
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
