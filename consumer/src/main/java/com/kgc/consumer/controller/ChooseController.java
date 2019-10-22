package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.kgc.consumer.config.custom.CurrentUser;
import com.kgc.consumer.config.custom.LoginRequired;
import com.kgc.consumer.contants.RushContant;
import com.kgc.consumer.contants.UserContant;
import com.kgc.consumer.model.WXPayModel;
import com.kgc.consumer.service.WXPayServiceApi;
import com.kgc.consumer.utils.ActiveMQUtils;
import com.kgc.consumer.utils.CommonUtil;
import com.kgc.consumer.utils.RedisUtils;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.utils.wxPayUtils.WxPayUtils;
import com.kgc.consumer.vo.ChooseGoodsVo;
import com.kgc.consumer.vo.OrderVo;
import com.kgc.consumer.vo.SumVo;
import com.kgc.provider.dto.Good;
import com.kgc.provider.dto.Order;
import com.kgc.provider.service.ChooseService;
import com.kgc.provider.service.OrderService;
import com.kgc.provider.service.ShowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

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
    @Reference
    private ChooseService chooseService;
    @Autowired
    private RedisUtils redisUtils;
    @Reference
    private OrderService orderService;
    @Autowired
    private ActiveMQUtils activeMQUtils;


    @ApiOperation("购买")
    @GetMapping(value = "/chooseGood")
    @LoginRequired
    public ReturnResult chooseGood(@ApiParam(value = "商品id", required = true) @RequestParam(value = "gid") String gid,
                                   @ApiParam(value = "购买数量", required = true) @RequestParam(value = "amount") int amount,
                                   @CurrentUser SumVo sumVo) {
        Good good = showService.showGoods(gid);
        ChooseGoodsVo chooseVo = new ChooseGoodsVo();
        BeanUtils.copyProperties(good, chooseVo);
        //积分可抵扣额度
        String phone;
        if (null == sumVo.getPhone()) {
            phone = sumVo.getUserPhone();
        } else {
            phone = sumVo.getPhone();
        }
        boolean local = redisUtils.lock(String.valueOf(gid), phone, null);
        while (local) {
            Object result = redisUtils.get(UserContant.USER_BUY + phone + gid);
            if (null == result) {
                boolean isExit = orderService.isExist(gid).getGoodStock() > 0 ? true : false;
                if (isExit) {
                    //积分可减金额
                    int integral = showService.selectIntegral(phone);
                    chooseVo.setSubPrice(integral / 100);
                    //运费
                    if (sumVo.getSuperman() == 1) {
                        chooseVo.setFreight(0);
                    } else if (sumVo.getUserLevel() < 3) {
                        chooseVo.setFreight(5);
                    } else {
                        chooseVo.setFreight(10);
                    }
                    chooseVo.setAmount(amount);
                    chooseVo.setTotalPrice(good.getGoodPrice() * amount + chooseVo.getFreight() - chooseVo.getSubPrice());
                    //更新积分
                    //chooseService.updateIntergral(phone,new Double(chooseVo.getTotalPrice()*0.1).intValue());
                    Order order = new Order();
                    order.setGoodId(gid);
                    order.setAddress("丑利坚合众国");
                    order.setGoodName(good.getGoodName());
                    order.setUserId(sumVo.getUserName());
                    order.setCode(CommonUtil.createUUID(16));
                    order.setPhone(phone);
                    order.setStatus(0);
                    order.setCreateTime(new Date());
                    order.setIsDelete(0);
                    order.setGoodIntegral(chooseVo.getSubPrice());
                    order.setGoodAmount(amount);
                    order.setGoodPrice(chooseVo.getTotalPrice());
                    String orderJson = JSONObject.toJSONString(order);
                    activeMQUtils.sendQueueMesage("orderJson", orderJson);
                    return ReturnResultUtils.returnSuccess(chooseVo);
                } else {
                    return ReturnResultUtils.returnFail(RushContant.BUY_IS_FAIL_CODE, RushContant.BUY_IS_FAIL_MASSAGE);
                }
            } else {
                return ReturnResultUtils.returnFail(RushContant.BUY_IS_FAIL_CODE, RushContant.GOODS_IS_FAIL_MASSAGE);
            }
        }
        return ReturnResultUtils.returnFail(RushContant.BUY_IS_FAIL_CODE, RushContant.BUYG_IS_FAIL_MASSAGE);
    }

    @JmsListener(destination = "orderJson")
    public void listener(String order) {
        Order orders = JSONObject.parseObject(order,Order.class);
        //生成订单
        orderService.addOrder(orders);
        //减库存
        orderService.delStock(orders.getGoodId());
        //去锁
        redisUtils.delLock(String.valueOf(orders.getGoodId()));
    }

}