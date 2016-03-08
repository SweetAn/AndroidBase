package com.commons.support.widget.ad;
import com.alibaba.fastjson.annotation.JSONField;

public class Ad {

    @JSONField(name = "canclose")
    private boolean canClose;
    private int id;
    private int type;

    @JSONField(name = "thumb_url")
    private String img;

    private String link;

    @JSONField(name = "refurl")
    private String url;

    private String title;

    private AdBody body;

    public boolean isCanClose() {
        return canClose;
    }

    public void setCanClose(boolean canClose) {
        this.canClose = canClose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AdBody getBody() {
        return body;
    }

    public void setBody(AdBody body) {
        this.body = body;
    }
}
