package com.kgc.consumer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.consumer.model.WXPayModel;
import com.kgc.consumer.utils.UrlUtils;
import com.kgc.consumer.utils.wxPayUtils.CommonUtil;
import com.kgc.consumer.utils.wxPayUtils.WxPayUtils;
import com.kgc.consumer.vo.SuperManVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/20 17:01
 * @since
 */
@Component
public class SuperManPayService {
    @Autowired
    private WXPayModel wxPayModel;

    public String wxPay(SuperManVo superManVo,String phone) throws Exception {

        SortedMap<String, String> param = new TreeMap<String, String>();
        param.put("appid", wxPayModel.getAppid());
        param.put("mch_id", wxPayModel.getMchid());
        param.put("nonce_str", CommonUtil.createUUID(32));
        param.put("body", superManVo.getName());
        param.put("out_trade_no", CommonUtil.createUUID(16));
        param.put("total_fee", String.valueOf(superManVo.getPrice()/10));
        param.put("spbill_create_ip", "192.168.1.187");
        param.put("notify_url", wxPayModel.getNotifyurl());
        param.put("trade_type", wxPayModel.getType());
        param.put("detail",phone);
        String sign = WxPayUtils.generateSignature(param, wxPayModel.getKey());
        param.put("sign", sign);
        //将map转成xml
        String WxXmlstr = WxPayUtils.mapToXml(param);
        String WxreturnXml = UrlUtils.doPost(wxPayModel.getUnified(), WxXmlstr, 5000);
        Map<String, String> WxReturnMap = WxPayUtils.xmlToMap(WxreturnXml);
        return WxReturnMap.get("code_url");
    }
}