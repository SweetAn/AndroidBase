package com.androidbase.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Article implements Serializable {



//    "id": 3,
//    "title": "河南新沂市社保处四项措施加强服务标准化建设 ",
//    "img_url": "",
//    "url": "http://www.chinajob.gov.cn/SocialSecurity/content/2015-05/08/content_1064891.htm",
//    "add_dt": "2015-05-07T14:09:30+08:00"
    //date=2015-05-12T14:25:56.3006486+08:00

    private int id;
    private String title;
    private String context;
    private String desc;

    @JSONField(name = "img_url")
    private String imageUrl;

    private String url;

    @JSONField(name = "add_dt")
    private Date addDt;

    @JSONField(name = "tag_list")
    List<Tag> tags;

    private int imageWidth;
    private int imageHeight;
    private String shareLink;

    @JSONField(name = "is_video")
    private boolean hasVideo;

    @JSONField(name = "is_essence")
    private boolean isNice;


    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getAddDt() {
        return addDt;
    }

    public void setAddDt(Date addDt) {
        this.addDt = addDt;
    }

    public boolean isNice() {
        return isNice;
    }

    public void setNice(boolean isNice) {
        this.isNice = isNice;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

}
