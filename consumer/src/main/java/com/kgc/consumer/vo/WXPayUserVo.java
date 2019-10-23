package com.kgc.consumer.vo;

import java.io.Serializable;

/**
 * Created By: YunCeng
 * Created on: 2019/10/22:18:29
 */
public class WXPayUserVo implements Serializable {
    private long id;
    private Double goodPrice;
    private String userName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(Double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
