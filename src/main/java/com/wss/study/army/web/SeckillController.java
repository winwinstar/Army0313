package com.wss.study.army.web;

import com.wss.study.army.dto.Exposer;
import com.wss.study.army.dto.SeckillExecution;
import com.wss.study.army.dto.SeckillResult;
import com.wss.study.army.entity.Seckill;
import com.wss.study.army.exception.RepeatKillException;
import com.wss.study.army.exception.SeckillCloseException;
import com.wss.study.army.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017-01-23.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId,Model model ){
        if(seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if(seckill == null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long seckillId){
        SeckillResult<Exposer> seckillResult;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            seckillResult = new SeckillResult<Exposer>(true,exposer);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            seckillResult = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return seckillResult;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId, @PathVariable("md5") String md5, @CookieValue(value="killPhone", required = false) Long phone){
        if(phone == null){
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }
        SeckillResult<SeckillExecution> seckillResult;

        try {
            SeckillExecution execution = seckillService.excuteSeckill(seckillId,phone,md5);
            return new SeckillResult<SeckillExecution>(true,execution);
        }catch (RepeatKillException e){
            SeckillExecution execution = seckillService.excuteSeckill(seckillId,phone,md5);
            return new SeckillResult<SeckillExecution>(false,execution);
        }catch (SeckillCloseException e){
            SeckillExecution execution = seckillService.excuteSeckill(seckillId,phone,md5);
            return new SeckillResult<SeckillExecution>(false,execution);

        }catch(Exception e){
            logger.error(e.getMessage(),e);
            SeckillExecution execution = seckillService.excuteSeckill(seckillId,phone,md5);
            return new SeckillResult<SeckillExecution>(false,execution);
        }

    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    public SeckillResult<Long> time(){
        Date  now = new Date();
        return new SeckillResult(true,now.getTime());
    }
}
