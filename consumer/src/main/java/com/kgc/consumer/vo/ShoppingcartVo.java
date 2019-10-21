package com.kgc.consumer.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created By: YunCeng
 * Created on: 2019/10/17:19:59
 */
public class ShoppingcartVo {
    private Long id;
    @ApiModelProperty(value = "图片",required = true,example = "1")
    private String goodImage;
    @ApiModelProperty(value = "商品名字",required = true,example = "1")
    private String goodName;
    @ApiModelProperty(value = "商品描述",required = true,example = "1")
    private String goodContent;
    @ApiModelProperty(value = "商品价格",required = true,example = "1")
    private Double goodPrice;
    @ApiModelProperty(value = "购买数量",required = true,example = "1")
    private Integer goodAmount;
    @ApiModelProperty(value = "总价",required = true,example = "1")
    private Double totalPrice;

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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
