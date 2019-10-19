package com.kgc.consumer.controller;

import com.kgc.consumer.vo.OrderVo;
import com.kgc.provider.dto.Shoppingcart;
import com.kgc.provider.service.ShoppingcartService;
import io.swagger.annotations.Api;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created By: YunCeng
 * Created on: 2019/10/19:11:32
 * 生成订单
 */
@Api(tags = "去付款")
@RestController
@RequestMapping(value = "/goPay")
public class GenerateOrderController {

    @Reference
    private ShoppingcartService shoppingcartService;




    public String generateOrder(long id){
        Shoppingcart shoppingcart=shoppingcartService.selectByPrimaryKey(id);
        OrderVo orderVo=new OrderVo();




        return "";
    }


}
