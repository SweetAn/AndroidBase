package com.androidbase.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.androidbase.commons.Constants;
import com.loopj.android.http.RequestParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<E> implements Serializable {

    private static final long serialVersionUID = -3111477306352964437L;

    @JSONField(name = "p")
    private int currentPage;

    private int nextPage;

    @JSONField(name = "totalpage")
    private int totalPage;

    @JSONField(name = "searchid")
    private int sid;

    private String resultCode;

    @JSONField(name = "sincetime")
    private String sinceTime;

    private List<E> dataList = new ArrayList();

    @JSONField(name = "list")
    private String listStr;

    private int totalSize;

    public void initPage() {
        this.currentPage = 0;
        this.nextPage = 1;
        this.totalPage = 1;
        this.sinceTime = "";
        this.sid = 0;
        dataList.clear();
    }

    public void initPage(Page<E> page) {
        this.dataList.clear();
        this.currentPage = page.getCurrentPage();
        this.nextPage = page.getCurrentPage() + 1;
        this.totalPage = page.getTotalPage();
        this.sinceTime = page.getSinceTime();
        this.totalSize = page.getTotalSize();
        this.sid = page.getSid();
        this.dataList.addAll(page.getDataList());
        this.resultCode = page.getResultCode();
    }

    public boolean isRefresh() {
        if (currentPage == 1 || currentPage == 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isNeedCache() {
        if (currentPage == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasMore() {
        if (nextPage <= totalPage && totalPage != 1) {
            return true;
        } else {
            return false;
        }
    }

    public Page() {
        super();
        initPage();
    }

    public Page(int page, int totalPage, int sid, String sinceTime, List<E> list) {
        super();
        this.currentPage = page;
        this.nextPage = page + 1;
        this.totalPage = totalPage;
        this.sinceTime = sinceTime;
        this.sid = sid;
        this.dataList.addAll(list);
    }


    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getSinceTime() {
        return sinceTime;
    }

    public void setSinceTime(String sinceTime) {
        this.sinceTime = sinceTime;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public String getListStr() {
        return listStr;
    }

    public void setListStr(String listStr) {
        this.listStr = listStr;
    }

    public List<E> getDataList() {
        return dataList;
    }

    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
    }

    public RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.put(Constants.PARAM_PAGE, nextPage + "");
        //params.put(Constants.PARAM_SID, sid + "");
        params.put(Constants.PARAM_SINCE_TIME, sinceTime);
        return params;
    }


}
