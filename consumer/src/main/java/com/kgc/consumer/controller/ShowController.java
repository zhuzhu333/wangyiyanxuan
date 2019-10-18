package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.vo.GoodsVo;
import com.kgc.provider.dto.Good;
import com.kgc.provider.service.ShowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created BY: WYM
 * Created on: 2019/10/17 18:54
 */
@Api("展示商品")
@RestController
@RequestMapping(value = "show")
public class ShowController {

    @Reference
    private ShowService showService;


    @GetMapping(value = "showGoods")
    @ApiOperation("展示商品")
    public ReturnResult showGoods(String gid) {
        Good good = showService.showGoods(gid);
        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(good, goodsVo);
        //User user = new User();
        goodsVo.setReIntegral(new Double(good.getGoodPrice() * 0.1).intValue());
        //goodsVo.setSubPrice(user.getIntegral() / 100);
        if (showService.highPraise(gid) != 0 && showService.totalOrder(gid) != 0) {
            goodsVo.setRate(showService.highPraise(gid) / showService.totalOrder(gid) * 100);
            if (goodsVo.getRate() >= 90) {
                goodsVo.setColor("red");
            } else if (goodsVo.getRate() <= 90 && goodsVo.getRate() >= 60) {
                goodsVo.setColor("green");
            } else {
                goodsVo.setColor("black");
            }
        } else {
            goodsVo.setColor("还没有评价");
        }

        if (good.getCurrentStock() / good.getGoodStock() >= 0.5) {
            goodsVo.setCurrentStock(good.getCurrentStock());
            goodsVo.setStockMsg("库存充足");
        } else if (good.getCurrentStock() / good.getGoodStock() < 0.5 && good.getCurrentStock() / good.getGoodStock() > 0) {
            goodsVo.setCurrentStock(good.getCurrentStock());
            goodsVo.setStockMsg("尽快选购");
        } else {
            goodsVo.setCurrentStock(good.getCurrentStock());
            goodsVo.setStockMsg("库存不足");
        }

        return ReturnResultUtils.returnSuccess(goodsVo);
    }


}
