package com.mello.controller;

import com.mello.domain.Seckill;
import com.mello.dto.Expose;
import com.mello.dto.SeckillExecution;
import com.mello.dto.SeckillResult;
import com.mello.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 秒杀接口控制器
 * Created by MelloChan on 2017/10/9.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private final SeckillService seckillService;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SeckillController(SeckillService seckillService) {
        this.seckillService = seckillService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Seckill> seckills = seckillService.getSeckillList();
        model.addAttribute("list", seckills);
        return "list";
    }

    @GetMapping("/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
//            return "forward:/seckill/list";
            return "redirect:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    //ajax 接口 返回json
    @PostMapping(value = "/{seckillId}/expose",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Expose> export(Long seckillId) {
        SeckillResult<Expose> result;
        try {
            Expose expose = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Expose>(true, expose);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = new SeckillResult<Expose>(false, e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/{seckillId}/{md5}/execution",produces = {"application/json;charset=UTF-8"})
    public SeckillResult<SeckillExecution>execute(@PathVariable("seckillId") Long seckillId,@PathVariable("md5") String md5,
                                                  @CookieValue(value = "killPhone",required = false) Long phone){
       //springmvc valid 此处较为简单不需要使用
        if(phone==null){
           return new SeckillResult<SeckillExecution>(false,"未注册");
       }
        SeckillResult<SeckillExecution>result;
        try {
            SeckillExecution execution=seckillService.executeSeckill(seckillId,phone,md5);
            result=new SeckillResult<SeckillExecution>(true,execution);
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            result=new SeckillResult<SeckillExecution>(false,e.getMessage());
        }
        return result;
    }

    /**
     * 获取系统时间
     * @return json结果集
     */
    @GetMapping(value = "/time/now",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Long>time(){
        return new SeckillResult<Long>(true,new Date().getTime());
    }
}
