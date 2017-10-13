package com.mello.controller;

import com.mello.domain.Seckill;
import com.mello.dto.Expose;
import com.mello.dto.SeckillExecution;
import com.mello.dto.SeckillResult;
import com.mello.enums.SeckillStateEnum;
import com.mello.expection.RepeatSeckillExpection;
import com.mello.expection.SeckillCloseExpection;
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

    /**
     * 暴露秒杀接口 json格式
     *
     * @param seckillId
     * @return
     */
    @GetMapping(value = "/{seckillId}/expose", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Expose> export(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Expose> result;
        try {
            Expose expose = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<>(true, expose);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = new SeckillResult<>(false, e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/{seckillId}/{md5}/execution", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId, @PathVariable("md5") String md5,
                                                   @CookieValue(value = "userPhone", required = false) Long userPhone) {
        //没使用springmvc valid 参数较少
        if (userPhone == null) {
            return new SeckillResult<>(false, "未注册");
        }
        SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
            result = new SeckillResult<>(false, execution);
        } catch (RepeatSeckillExpection e1) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<>(false, execution);
        } catch (SeckillCloseExpection e2) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<>(false, execution);
        } catch (Exception e) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            result = new SeckillResult<>(false, execution);
        }
        LOG.debug("result={}", result);
        return result;
    }

    /**
     * 获取系统时间
     *
     * @return json结果集
     */
    @GetMapping(value = "/time/now")
    @ResponseBody
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult<>(true, now.getTime());
    }
}
