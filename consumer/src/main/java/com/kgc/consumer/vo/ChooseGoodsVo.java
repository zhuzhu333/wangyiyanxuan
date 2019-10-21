package com.kgc.consumer.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created BY: WYM
 * Created on: 2019/10/18 14:35
 */
public class ChooseGoodsVo implements Serializable {
    private String goodName;
    private String goodContent;
    private String goodImage;
    @ApiModelProperty(value = "购买数量", required = true)
    private int amount;
    @ApiModelProperty(value = "积分可减金额", required = true)
    private int subPrice;
    @ApiModelProperty(value = "运费", required = true)
    private int freight;
    @ApiModelProperty(value = "总金额", required = true)
    private double totalPrice;

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

    public int getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(int subPrice) {
        this.subPrice = subPrice;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
