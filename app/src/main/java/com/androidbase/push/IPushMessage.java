package com.androidbase.push;

import com.androidbase.entity.Message;

/**
 * Created by User on 2015/10/19.
 */
public interface IPushMessage {

    void message200();

    void message201(Message getMsg);

    void message202(int getRef, Message getMsg);

}
