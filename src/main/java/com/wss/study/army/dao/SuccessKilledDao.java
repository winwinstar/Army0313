package com.wss.study.army.dao;

import com.wss.study.army.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017-01-15.
 */
public interface SuccessKilledDao {
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
    SuccessKilled queryByIdWidthSeckill(@Param("seckillId") long seckillId,@Param("userPhone")long userPhone);
}
