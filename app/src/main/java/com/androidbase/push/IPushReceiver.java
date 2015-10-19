package com.androidbase.push;

/**
 * Created by User on 2015/10/19.
 */
public interface IPushReceiver {

    /**
     * 接收透传消息，该消息不需要再通知栏显示
     */
    void onReceivePassThroughMessage(String message);

    /**
     * 接收通知消息，一般用不到这个，会直接展示在通知栏，自定义通知样式就重写该方法
     */
    void onNotificationMessageArrived(String message);

    /**
     * 通知栏的消息被点击后的处理，自定义通知样式该方法无效
     */
    void onNotificationMessageClicked(String message);

}
