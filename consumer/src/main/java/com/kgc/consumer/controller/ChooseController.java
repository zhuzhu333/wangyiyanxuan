package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.kgc.consumer.config.custom.CurrentUser;
import com.kgc.consumer.config.custom.LoginRequired;
import com.kgc.consumer.model.WXPayModel;
import com.kgc.consumer.service.WXPayServiceApi;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.utils.wxPayUtils.WxPayUtils;
import com.kgc.consumer.vo.ChooseGoodsVo;
import com.kgc.consumer.vo.OrderVo;
import com.kgc.consumer.vo.SumVo;
import com.kgc.provider.dto.Good;
import com.kgc.provider.service.ChooseService;
import com.kgc.provider.service.ShowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private WXPayServiceApi wxPayServiceApi;
    @Autowired
    private WXPayModel wxPayModel;


    @ApiOperation("买")
    @GetMapping(value = "/chooseGood")
    @LoginRequired
    public ReturnResult chooseGood(@ApiParam(value = "商品id", required = true) @RequestParam(value = "gid") String gid,
                                   @ApiParam(value = "购买数量", required = true) @RequestParam(value = "amount") int amount, @CurrentUser SumVo sumVo) {
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
        chooseService.updateIntergral(phone,new Double(chooseVo.getTotalPrice()*0.1).intValue());
        return ReturnResultUtils.returnSuccess(chooseVo);
    }


    @ApiOperation(value = "统一下单")
    @GetMapping(value = "/unifiedWxpay")                /*unifiedWxpay统一下单*/
    public String unifiedWXPay(OrderVo orderVo) throws Exception {
        return wxPayServiceApi.unifiedWxpay(orderVo);
    }

    @ApiOperation(value = "回调")
    @RequestMapping(value = "/wxPayNotify")
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream = request.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();
        inputStream.close();
        Map<String, String> resultMap = WxPayUtils.xmlToMap(sb.toString());
        //成功回调了
        if ("SUCCESS".equals(resultMap.get("return_code"))) {
            //验证签名与金额
            boolean isCheckSign = WxPayUtils.checkSign(resultMap, wxPayModel.getKey());
            if (isCheckSign) {
                //todo
                //xxxx();

                Map<String, String> rMap = Maps.newHashMap();
                rMap.put("return_code", "SUCCESS");
                rMap.put("return_msg", "OK");
                String xml = WxPayUtils.mapToXml(rMap);
                response.getWriter().write(xml);
            }
        }
    }

}