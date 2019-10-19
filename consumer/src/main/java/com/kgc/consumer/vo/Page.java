package com.kgc.consumer.vo;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

    private int pagesize;

    private int currentPage;// 当前页

    private long totalCount;// 总条数

    private List<T> currList;

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getCurrList() {
        return currList;
    }

    public void setCurrList(List<T> currList) {
        this.currList = currList;
    }
}
