package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kgc.consumer.contants.OrderContant;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.vo.OrderVo;
import com.kgc.consumer.vo.Page;
import com.kgc.provider.dto.Order;
import com.kgc.provider.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @ApiOperation("逻辑删除订单")
    @GetMapping(value = "/isDel")
    public ReturnResult isDel(@ApiParam(value = "订单id", required = true) @RequestParam(value = "id") int id) {
        boolean isDel = orderService.isDel(id);
        if (isDel) {
            return ReturnResultUtils.returnSuccess();
        }
        return ReturnResultUtils.returnFail(OrderContant.ORDER_IS_DEL_FAIL_CODE, "删除失败！");
    }

    @ApiOperation("查看回收站")
    @GetMapping(value = "/recycle")
    public ReturnResult recycle(){
        List<Order> recycleList = orderService.recycle();
        return ReturnResultUtils.returnSuccess(recycleList);
    }

    @ApiOperation("删除回收站")
    @GetMapping(value = "/delRecycle")
    public ReturnResult delRecycle(){
        int delCount = orderService.delRecycle();
        return ReturnResultUtils.returnSuccess(delCount);
    }



}
