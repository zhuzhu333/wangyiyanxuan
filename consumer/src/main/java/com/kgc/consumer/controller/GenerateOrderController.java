package com.kgc.consumer.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.kgc.consumer.utils.CommonUtil;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.vo.OrderVo;
import com.kgc.provider.dto.Order;
import com.kgc.provider.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Date;

/**
 * Created BY: WYM
 * Created on: 2019/10/17 18:54
 */
@Api(tags = "去付款")
@RestController
@RequestMapping(value = "/goPay")
public class GenerateOrderController {

    @Reference
    private OrderService orderService;


    @ApiOperation("生成订单")
    @GetMapping(value = "/generateOrder")
    public ReturnResult generateOrder(@Valid OrderVo orderVo) {
        Order order = new Order();
        order.setGoodId(orderVo.getGoodId());
        order.setAddress(orderVo.getAddress());
        order.setGoodName(orderVo.getGoodName());
        order.setUserId(orderVo.getUsername());
        order.setCode(CommonUtil.createUUID(16));
        order.setPhone(orderVo.getPhone());
        order.setStatus(0);
        order.setCreateTime(new Date());
        order.setIsDelete(0);
        order.setGoodIntegral(9);
        order.setGoodAmount(orderVo.getGoodsAmount());
        order.setGoodPrice(orderVo.getGoodPrice() * orderVo.getGoodsAmount());
        orderService.addOrder(order);
        return ReturnResultUtils.returnSuccess(order);
    }


}
