package com.kgc.consumer.controller;

import com.kgc.consumer.config.custom.CurrentUser;
import com.kgc.consumer.config.custom.LoginRequired;
import com.kgc.consumer.contants.UserContant;
import com.kgc.consumer.utils.ActiveMQUtils;
import com.kgc.consumer.utils.CommonUtil;
import com.kgc.consumer.utils.RedisUtils;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.vo.OrderVo;
import com.kgc.provider.dto.Order;
import com.kgc.provider.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/21 12:02
 * @since
 */
@Controller
@Api(tags = "抢购商品")
@RequestMapping(value = "/rush")
public class rushController {
    @Autowired
    private RedisUtils redisUtils;
    @Reference
    private OrderService orderService;
    @Autowired
    private ActiveMQUtils activeMQUtils;

    @ResponseBody
    @ApiOperation("秒杀商品")
    @GetMapping("/kill")
    @LoginRequired
    public ReturnResult KillItems(@Valid OrderVo orderVo) {

        boolean islock = redisUtils.lock(String.valueOf(orderVo.getGoodId()), "1", null);
        while (islock) {
            boolean isExit = orderService.isExist(orderVo.getGoodId()).getGoodStock() > 0 ? true : false;
            if (isExit) {
                String userId = orderVo.getPhone();
                Object result = redisUtils.get(UserContant.USER_BUY + userId + orderVo.getGoodId());
                if (null == result) {
                    //（生成订单,减库存）实体类
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
                    order.setGoodAmount(1);
                    order.setGoodPrice(orderVo.getGoodPrice());
                    activeMQUtils.sendQueueMesage("order", order);
                    return ReturnResultUtils.returnSuccess(UserContant.BUY_SUCCESS);
                }
                return ReturnResultUtils.returnSuccess(UserContant.BUY_FAIL);
            }
            return ReturnResultUtils.returnSuccess(UserContant.STOCK_NULL);
        }
        return ReturnResultUtils.returnFail(1090, "抢购失败");
    }

    /*@JmsListener(destination = "itemKillSuccess")
    public void listener(Order order) {
        //生成订单
        orderService.addOrder(order);
        //限制抢购
        redisUtils.set(UserContant.USER_BUY +order.getPhone() + order.getGoodId(), order.getGoodId());
        //减少库存
        orderService.
        int i = itemKillSuccess.getItemId().intValue();
        redisUtils.del(String.valueOf(i));
    }*/
}
