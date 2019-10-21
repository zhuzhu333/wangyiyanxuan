package com.kgc.consumer.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created BY: WYM
 * Created on: 2019/10/17 20:15
 */
public class GoodsVo implements Serializable {

    private String goodName;
    private String goodContent;
    private String goodImage;
    private Double goodPrice;
    private int rate;
    @ApiModelProperty(value = "好评率颜色", required = true)
    private String color;
    @ApiModelProperty(value = "积分可减金额", required = true)
    private double subPrice;
    @ApiModelProperty(value = "购买后回馈积分", required = true)
    private int reIntegral;
    @ApiModelProperty(value = "当前库存", required = true)
    private int currentStock;
    @ApiModelProperty(value = "库存提示", required = true)
    private String stockMsg;


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

    public String getGoodImage() {
        return goodImage;
    }

    public void setGoodImage(String goodImage) {
        this.goodImage = goodImage;
    }

    public Double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(Double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public double getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(double subPrice) {
        this.subPrice = subPrice;
    }

    public int getReIntegral() {
        return reIntegral;
    }

    public void setReIntegral(int reIntegral) {
        this.reIntegral = reIntegral;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }
    public String getStockMsg() {
        return stockMsg;
    }

    public void setStockMsg(String stockMsg) {
        this.stockMsg = stockMsg;
    }
}
