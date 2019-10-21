package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.kgc.consumer.custom.CurrentUser;
import com.kgc.consumer.custom.LoginRequired;
import com.kgc.consumer.vo.ShoppingcartVo;
import com.kgc.provider.dto.Shoppingcart;
import com.kgc.provider.dto.User;
import com.kgc.provider.service.ShoppingcartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By: YunCeng
 * Created on: 2019/10/17:19:26
 */
@Api(tags = "购物车")
@RestController
@RequestMapping(value = "/shoppingcart")
public class ShoppingcartController {
    @Reference
    private ShoppingcartService shoppingcartService;





    @ApiOperation("查询用户购物车所有商品")
    @GetMapping(value = "/queryAll")
    /*@LoginRequired*/
    public List<ShoppingcartVo> queryAll(/*@CurrentUser*/ User user){

        List<Shoppingcart> shoppingcartList=shoppingcartService.selectAll();
       /* List<Shoppingcart> shoppingcartList=shoppingcartService.selectByName(user.getUserName());*/

        List<ShoppingcartVo> shoppingcartVos=new ArrayList<ShoppingcartVo>();

        for(Shoppingcart shoppingcart : shoppingcartList) {


            ShoppingcartVo shoppingcartVo=new ShoppingcartVo();
            shoppingcartVo.setGoodAmount(shoppingcart.getGoodAmount());
            shoppingcartVo.setGoodContent(shoppingcart.getGoodContent());
            shoppingcartVo.setGoodImage(shoppingcart.getGoodImage());
            shoppingcartVo.setGoodName(shoppingcart.getGoodName());
            shoppingcartVo.setGoodPrice(shoppingcart.getGoodPrice());
            shoppingcartVo.setId(shoppingcart.getId());
            shoppingcartVo.setUsername(shoppingcart.getUserName());

            shoppingcartVo.setTotalPrice(shoppingcart.getGoodAmount()*shoppingcart.getGoodPrice());

            shoppingcartVos.add(shoppingcartVo);


        }
        return shoppingcartVos;
    }

    /*@ApiOperation("添加商品到购物车")
    @GetMapping(value = "/insert")
    public int insert(@Valid Shoppingcart shoppingcart){
        return shoppingcartService.insert(shoppingcart);
    }*/

    @ApiOperation("删除购物车的商品")
    @GetMapping(value = "/delete")
    public int delete(@ApiParam(value = "id", required = true) @RequestParam(value = "id", required = true)long id){
        return shoppingcartService.deleteByPrimaryKey(id);
    }


    @ApiOperation("修改数量")
    @GetMapping(value = "/updateByGoodAmount")
    public int updateByGoodAmount(@ApiParam(value = "商品名", required = true) @RequestParam(value = "goodname", required = true)String goodname,
                                  @ApiParam(value = "数量", required = true) @RequestParam(value = "amount", required = true)Integer amount){

        return shoppingcartService.updateByGoodName(goodname,amount);

    }



}
