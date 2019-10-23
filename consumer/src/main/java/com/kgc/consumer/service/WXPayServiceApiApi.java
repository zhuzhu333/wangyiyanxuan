package com.kgc.consumer.service;

import com.kgc.consumer.model.WXPayModel;
import com.kgc.consumer.utils.UrlUtils;
import com.kgc.consumer.utils.wxPayUtils.CommonUtil;
import com.kgc.consumer.utils.wxPayUtils.WxPayUtils;
import com.kgc.consumer.vo.OrderVo;
import com.kgc.consumer.vo.WXPayUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created By: YunCeng
 * Created on: 2019/10/18:12:08
 */
@Component
public class WXPayServiceApiApi {

    @Autowired
    private WXPayModel wxPayModel;



    //传给我订单，状态为未支付，我从订单里面取我需要的good_id和Price


    public String unifiedWxpay(WXPayUserVo wxPayUserVo) throws Exception{
        int price= (new Double(wxPayUserVo.getGoodPrice())).intValue();
        SortedMap<String, String> param = new TreeMap<String, String>();
        param.put("appid",wxPayModel.getAppid());
        param.put("mch_id",wxPayModel.getMchid());
        param.put("nonce_str", CommonUtil.createUUID(32));
        param.put("body",wxPayUserVo.getUserName());
        param.put("out_trade_no",CommonUtil.createUUID(32));
        param.put("total_fee",String.valueOf(price));
        param.put("spbill_create_ip","192.168.1.125");
        param.put("notify_url",wxPayModel.getNotifyurl());
        param.put("trade_type",wxPayModel.getType());
        String sign = WxPayUtils.generateSignature(param,wxPayModel.getKey());//这里可能会出现异常 所以要抛出去
        param.put("sign",sign);

        String payXml = WxPayUtils.mapToXml(param);//将map转换成xml

        //请求微信统一下单接口（post请求）
        String resultData = UrlUtils.doPost(wxPayModel.getUnified(), payXml, 30000);//设置过期时间为10秒

        Map<String, String> resultMap = WxPayUtils.xmlToMap(resultData);
        return resultMap.get("code_url");//获取code_url

    }

}
