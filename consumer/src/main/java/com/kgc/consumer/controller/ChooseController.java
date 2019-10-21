package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.vo.ChooseGoodsVo;
import com.kgc.provider.dto.Good;
import com.kgc.provider.dto.User;
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
 * Created on: 2019/10/18 14:15
 */
@Api(tags = "商品选购")
@RestController
@RequestMapping(value = "choose")
public class ChooseController {

    @Reference
    private ShowService showService;


    @ApiOperation("买")
    @GetMapping(value = "/chooseGood")
    public ReturnResult chooseGood(@ApiParam(value = "商品id", required = true)
                                   @RequestParam(value = "gid") String gid) {
        Good good = showService.showGoods(gid);
        ChooseGoodsVo chooseVo = new ChooseGoodsVo();
        BeanUtils.copyProperties(good, chooseVo);

        //积分抵扣
        User user = new User();
        chooseVo.setSubPrice(user.getIntegral() / 100);
        //运费
        if (user.getSuperman() == 1) {
            chooseVo.setFreight(0);
        } else if (user.getUserLevel() < 3) {
            chooseVo.setFreight(5);
        } else {
            chooseVo.setFreight(10);
        }

        chooseVo.setTotalPrice(good.getGoodPrice() * 0.98 + chooseVo.getFreight() - chooseVo.getSubPrice());
        return ReturnResultUtils.returnSuccess(chooseVo);
    }
}
