package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kgc.consumer.contants.GoodContant;
import com.kgc.consumer.utils.RedisUtils;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.vo.GoodDetails;
import com.kgc.consumer.vo.GoodsVo;
import com.kgc.consumer.vo.PageVo;
import com.kgc.provider.dto.Good;
import com.kgc.provider.dto.GoodsGroup;
import com.kgc.provider.dto.Shoppingcart;
import com.kgc.provider.mapper.GoodMapper;
import com.kgc.provider.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/17 17:32
 * @since
 */
@RestController
@Api(tags = "首页展示")
@RequestMapping(value = "/index")
public class indexController {
    @Reference
    private IndexService indexService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "showGroup")
    @GetMapping(value = "/showGroup")
    public ReturnResult<List> showGroup() {
        List<GoodsGroup> list = indexService.showGroup();
        List showGroup = new ArrayList();
        list.forEach(obj -> {
            showGroup.add(obj.getGroupName());
        });
        return ReturnResultUtils.returnSuccess(showGroup);
    }

    @ApiOperation(value = "showGoods")
    @GetMapping(value = "/showGoods")
    public ReturnResult<List> showGoods(@ApiParam(value = "group", required = true) @RequestParam(value = "group", required = true) String group, @Valid PageVo pageVo,
                                        @ApiParam(value = "ip", required = true) @RequestParam(value = "ip", required = true)String ip) {
        GoodsGroup goodsGroup = indexService.selectByName(group);
        String goodSort = goodsGroup.getGoodSort();
        List<Good> goods = indexService.selectBySort(Integer.parseInt(goodSort), pageVo.getsPage(), pageVo.getpSize());
        List goodNames = new ArrayList();
        goods.forEach(obj -> {
            goodNames.add(obj.getGoodName());
        });
        GoodDetails goodDetails=new GoodDetails();
        //插入商品分页信息
        goodDetails.setGoodNames(goodNames);
        //插入商品搜索框默认商品
        goodDetails.setDefaultName(indexService.randomGood().getGoodName());
        //计算购物车商品数量
        String token = (String) redisUtils.get(ip);
        List<Shoppingcart> list = JSONArray.parseArray(token, Shoppingcart.class);
        goodDetails.setGoodNumber(list.size());
        return ReturnResultUtils.returnSuccess(goodNames);
    }

    @ApiOperation(value = "添加商品到购物车")
    @GetMapping(value = "/setShoppingCart")
    public void setShoppingCart(@ApiParam(value = "goodName", required = true) @RequestParam(value = "goodName", required = true) String goodName, HttpServletRequest request) {
        //获取商品信息
        Good good = indexService.selectByGoodName(goodName);
        Shoppingcart shoppingcart = new Shoppingcart();
        BeanUtils.copyProperties(good, shoppingcart);
        //
        HttpSession session = request.getSession();

        String sessionId = session.getId();
        String shoppingCartStr = (String) redisUtils.get(GoodContant.GOU_WU_CHE + sessionId);

        List<Shoppingcart> list = JSONArray.parseArray(shoppingCartStr, Shoppingcart.class);
        boolean isExit = false;
        if (null != list) {
            //遍历集合，如果有相同商品那么表示存在并且增加价格和数量
            for (Shoppingcart obj : list) {
                if (obj.getGoodName().equals(goodName)) {
                    obj.setGoodAmount(obj.getGoodAmount() + 1);
                    isExit = true;
                    obj.setGoodPrice(obj.getGoodPrice() + good.getGoodPrice());
                }
            }
            //如果不存在就增加一个商品
            if (isExit == false) {
                shoppingcart.setGoodAmount(1);
                list.add(shoppingcart);
                String relist = JSON.toJSONString(list);
                redisUtils.set(GoodContant.GOU_WU_CHE + sessionId, relist);
            } else {
                String relist = JSON.toJSONString(list);
                redisUtils.set(GoodContant.GOU_WU_CHE + sessionId, relist);
            }
        } else {
            List<Shoppingcart> flist = new ArrayList<>();
            shoppingcart.setGoodAmount(1);
            flist.add(shoppingcart);
            String relist = JSON.toJSONString(flist);
            redisUtils.set(GoodContant.GOU_WU_CHE + sessionId, relist, 3000);
        }
    }

    @ApiOperation("展示购物车")
    @GetMapping(value = "/showShoppingCart")
    public ReturnResult<List> showShoppingCart(HttpServletRequest request) {
        String str = request.getSession().getId();
        String token = (String) redisUtils.get(GoodContant.GOU_WU_CHE + str);
        List<Shoppingcart> list = JSONArray.parseArray(token, Shoppingcart.class);
        return ReturnResultUtils.returnSuccess(list);
    }

    @ApiOperation("火爆商品")
    @GetMapping(value = "/showHotGood")
    public ReturnResult showHotGood() {
        Good good = indexService.selectHotGood();
        return ReturnResultUtils.returnSuccess(good.getGoodName());
    }

    @ApiOperation("模糊查询")
    @GetMapping(value = "/getAllGoods")
    public ReturnResult<List> getAllGoods(@ApiParam(value = "goodName", required = true) @RequestParam(value = "goodName", required = true) String goodName,
                                          @Valid PageVo pageVo) {
        List<Good> list = indexService.getAllGoods(goodName, pageVo.getsPage(), pageVo.getpSize());

        List goodsVoList = new ArrayList();
        list.forEach(obj -> {
            GoodsVo goodsVo = new GoodsVo();
            BeanUtils.copyProperties(obj, goodsVo);
            goodsVoList.add(goodsVo);
        });
        return ReturnResultUtils.returnSuccess(goodsVoList);
    }

}


