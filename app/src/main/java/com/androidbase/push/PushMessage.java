package com.androidbase.push;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 推送消息
 */
public class PushMessage implements Serializable {

    private static final long serialVersionUID = 8546031634165530454L;

    private String message;
    private int uxid = -1;
    private int ref;
    private String url;
    private boolean checkApp;

    private String data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUxid() {
        return uxid;
    }

    public void setUxid(int uxid) {
        this.uxid = uxid;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isCheckApp() {
        return checkApp;
    }

    public void setCheckApp(boolean checkApp) {
        this.checkApp = checkApp;
    }

    @Override
    public String toString() {
        return "UXID:" + getUxid() + " MSG:" + getMessage() + " URL:" + getUrl() + " REF:" + getRef()
                + " CHECKAPP:" + isCheckApp();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public static PushMessage parser(String payload) {
        PushMessage message = new PushMessage();
        String msg = null;
        try {
            JSONObject obj = new JSONObject(payload);
            if (!obj.isNull("message")) {
                msg = obj.getString("message");
                message.setMessage(msg);
            }
            if (!obj.isNull("uxid")) {
                message.setUxid(obj.getInt("uxid"));
            }
            if (!obj.isNull("ref")) {
                message.setRef(obj.getInt("ref"));
            }
            if (!obj.isNull("url")) {
                message.setUrl(obj.getString("url"));
            }
            if (!obj.isNull("appupdate")) {
                message.setCheckApp(obj.getBoolean("appupdate"));
            }
            if (!obj.isNull("data")) {
                message.setData(obj.getString("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return message;
    }
}
