package com.kgc.provider.dto;

import java.io.Serializable;

public class Shoppingcart implements Serializable {
    private Long id;

    private String goodImage;

    private String goodName;

    private String goodContent;

    private Double goodPrice;

    private Integer goodAmount;

    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodImage() {
        return goodImage;
    }

    public void setGoodImage(String goodImage) {
        this.goodImage = goodImage;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodContent() {
        return goodContent;
    }

    public void setGoodContent(String goodContent) {
        this.goodContent = goodContent;
    }

    public Double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(Double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public Integer getGoodAmount() {
        return goodAmount;
    }

    public void setGoodAmount(Integer goodAmount) {
        this.goodAmount = goodAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}