package com.kgc.consumer.vo;

public class OrderVo {
    private int type;
    private int startPage;
    private int pageSize;
    private String code;
    private String goodName;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }
}
