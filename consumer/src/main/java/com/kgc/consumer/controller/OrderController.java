package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.vo.OrderVo;
import com.kgc.consumer.vo.Page;
import com.kgc.provider.dto.Order;
import com.kgc.provider.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Campbell on 2019/10/19 14:18
 */
@Api(tags = "订单测试")
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @ApiOperation("查询订单")
    @GetMapping(value = "/getOrderList")
    public ReturnResult<Page> getOrderList(OrderVo orderVo){
        Order order = new Order();
        BeanUtils.copyProperties(orderVo,order);
        order.setStartPage((orderVo.getStartPage() - 1) * orderVo.getPageSize());
        order.setPageSize(orderVo.getPageSize());
        List<Order> orderList = orderService.getOrderList(order);
        int count = orderService.count(order);

        Page page = new Page();
        page.setCurrentPage(orderVo.getStartPage());
        page.setPagesize(orderVo.getPageSize());
        page.setTotalCount(count);
        page.setCurrList(orderList);
        return ReturnResultUtils.returnSuccess(page);


    }

}
