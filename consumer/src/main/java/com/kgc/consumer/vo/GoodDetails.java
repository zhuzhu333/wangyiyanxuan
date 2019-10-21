package com.kgc.consumer.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/21 19:59
 * @since
 */
public class GoodDetails implements Serializable {
    private List goodNames;
    private String defaultName;
    private int GoodNumber;

    public List getGoodNames() {
        return goodNames;
    }

    public void setGoodNames(List goodNames) {
        this.goodNames = goodNames;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public int getGoodNumber() {
        return GoodNumber;
    }

    public void setGoodNumber(int goodNumber) {
        GoodNumber = goodNumber;
    }
}
