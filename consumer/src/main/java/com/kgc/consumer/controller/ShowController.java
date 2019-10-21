package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kgc.consumer.config.custom.CurrentUser;
import com.kgc.consumer.config.custom.LoginRequired;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.vo.GoodsVo;
import com.kgc.consumer.vo.SumVo;
import com.kgc.provider.dto.Good;
import com.kgc.provider.service.ShowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created BY: WYM
 * Created on: 2019/10/17 18:54
 */
@Api(tags = "展示商品")
@RestController
@RequestMapping(value = "show")
public class ShowController {

    @Reference
    private ShowService showService;

    @LoginRequired
    @GetMapping(value = "showGoods")
    @ApiOperation("商品")
    public ReturnResult showGoods(@ApiParam(value = "商品id", required = true)
                                  @RequestParam(value = "gid") String gid, @CurrentUser SumVo sumVo) {
        Good good = showService.showGoods(gid);
        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(good, goodsVo);
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

        Double CurrentStock = Double.valueOf(good.getCurrentStock());
        Double GoodStock = Double.valueOf(good.getGoodStock());
        if ((CurrentStock / GoodStock) >= 0.5) {
            goodsVo.setCurrentStock(good.getCurrentStock());
            goodsVo.setStockMsg("库存充足");
        } else if ((CurrentStock / GoodStock) < 0.5 && (CurrentStock / GoodStock) > 0) {
            goodsVo.setCurrentStock(good.getCurrentStock());
            goodsVo.setStockMsg("尽快选购");
        } else {
            goodsVo.setCurrentStock(good.getCurrentStock());
            goodsVo.setStockMsg("库存不足");
        }

        return ReturnResultUtils.returnSuccess(goodsVo);
    }

}
