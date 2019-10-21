package com.kgc.consumer.controller;

import com.google.common.collect.Maps;
import com.kgc.consumer.model.WXPayModel;
import com.kgc.consumer.service.WXPayServiceApi;
import com.kgc.consumer.utils.wxPayUtils.WxPayUtils;
import com.kgc.consumer.vo.OrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created By: YunCeng
 * Created on: 2019/10/18:11:16
 */
@Api(tags = "微信支付")
@RestController
@RequestMapping(value ="/wxPay")
public class WXPayController {
    @Autowired
    private WXPayServiceApi wxPayServiceApi;
    @Autowired
    private WXPayModel wxPayModel;


    @ApiOperation(value = "统一下单")
    @GetMapping(value = "/unifiedWxpay")                /*unifiedWxpay统一下单*/
    public String unifiedWXPay(OrderVo orderVo) throws Exception {
        return wxPayServiceApi.unifiedWxpay(orderVo);
    }

    @ApiOperation(value = "回调")
    @RequestMapping(value = "/wxPayNotify")
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response)throws Exception{
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

    /*public static boolean checkSign(Map<String, String> data, String key) throws Exception {
        String newSign = generateSignature(data, key);
        String rSign = data.get("sign");
        return rSign.equalsIgnoreCase(newSign);
    }*/

}
