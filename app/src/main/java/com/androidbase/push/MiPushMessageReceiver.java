package com.androidbase.push;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

/**
 * 这些方法运行在非UI线程中
 * Created by User on 2015/9/11.
 */
public class MiPushMessageReceiver extends PushMessageReceiver {
    String TAG = "MiPushMessageReceiver";

    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mAccount;
    private String mStartTime;
    private String mEndTime;

    public static void initMiPush(Context context, String topic, String token){ // 配置小米推送，要根据登录状态设置这些
        MiPushClient.setAlias(context, topic, null); // 私有的topic，唯一
        MiPushClient.setUserAccount(context, "account-test", null);
        MiPushClient.subscribe(context, token, null); // topic-组
        MiPushClient.setAcceptTime(context, 7, 0, 23, 0, null);
    }

    /**
     * 用来接收服务器向客户端发送的透传消息
     * @param context
     * @param miPushMessage
     */
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        Log.v(TAG, "onReceivePassThroughMessage is called. " + miPushMessage.toString());
        Log.v(TAG, "onReceivePassThroughMessage getContent. " + miPushMessage.getContent());

        PushReceiver.getInstance().onReceivePassThroughMessage(miPushMessage.getContent());

        if(!TextUtils.isEmpty(miPushMessage.getTopic())) {
            mTopic=miPushMessage.getTopic();
        } else if(!TextUtils.isEmpty(miPushMessage.getAlias())) {
            mAlias=miPushMessage.getAlias();
        }

    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        Log.v(TAG, "onNotificationMessageClicked is called. " + miPushMessage.toString());

        PushReceiver.getInstance().onNotificationMessageClicked(miPushMessage.getContent());

        if(!TextUtils.isEmpty(miPushMessage.getTopic())) {
            mTopic=miPushMessage.getTopic();
        } else if(!TextUtils.isEmpty(miPushMessage.getAlias())) {
            mAlias=miPushMessage.getAlias();
        }
    }

    /**
     * 接收服务器向客户端发送的通知消息，这个回调方法是在通知消息到达客户端时触发。
     * 另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数
     * 小米测试机无法收到
     * @param context
     * @param miPushMessage
     */
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        Log.v(TAG, "onNotificationMessageArrived is called. " + miPushMessage.toString());

        PushReceiver.getInstance().onNotificationMessageArrived(miPushMessage.getContent());

        if(!TextUtils.isEmpty(miPushMessage.getTopic())) {
            mTopic=miPushMessage.getTopic();
        } else if(!TextUtils.isEmpty(miPushMessage.getAlias())) {
            mAlias=miPushMessage.getAlias();
        }
    }

    /**
     * 当客户端向服务器发送注册push、设置alias、取消注册alias、订阅topic、取消订阅topic等等命令后，从服务器返回结果。
     * @param context
     * @param miPushCommandMessage
     */
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        Log.v(TAG, "onCommandResult is called. " + miPushCommandMessage.toString());
        String command = miPushCommandMessage.getCommand();
        List<String> arguments = miPushCommandMessage.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);

        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                Log.i(TAG, "onCommandResult COMMAND_REGISTER.success.mRegId="+mRegId);
            } else {
                Log.i(TAG, "onCommandResult COMMAND_REGISTER.fail.mRegId="+mRegId);
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
                Log.i(TAG, "onCommandResult COMMAND_SET_ALIAS.success.mAlias="+mAlias);
            } else {
                Log.i(TAG, "onCommandResult COMMAND_SET_ALIAS.fail.getReason="+miPushCommandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
                Log.i(TAG, "onCommandResult COMMAND_UNSET_ALIAS.success.mAlias="+mAlias);
            } else {
                Log.i(TAG, "onCommandResult COMMAND_UNSET_ALIAS.fail.getReason="+miPushCommandMessage.getReason());
            }
        }  else if (MiPushClient.COMMAND_SET_ACCOUNT.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mAccount = cmdArg1;
                Log.i(TAG, "onCommandResult COMMAND_SET_ACCOUNT.success.mAccount="+mAccount);
            } else {
                Log.i(TAG, "onCommandResult COMMAND_SET_ACCOUNT.fail.getReason="+miPushCommandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSET_ACCOUNT.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mAccount = cmdArg1;
                Log.i(TAG, "onCommandResult COMMAND_UNSET_ACCOUNT.success.mAccount="+mAccount);
            } else {
                Log.i(TAG, "onCommandResult COMMAND_UNSET_ACCOUNT.fail.getReason="+miPushCommandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
                Log.i(TAG, "onCommandResult COMMAND_SUBSCRIBE_TOPIC.success.mTopic="+mTopic);
            } else {
                Log.i(TAG, "onCommandResult COMMAND_SUBSCRIBE_TOPIC.fail.getReason="+miPushCommandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                Log.i(TAG, "onCommandResult COMMAND_UNSUBSCRIBE_TOPIC.success.mTopic="+mTopic);
            } else {
                Log.i(TAG, "onCommandResult COMMAND_UNSUBSCRIBE_TOPIC.fail.getReason="+miPushCommandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
                Log.i(TAG, "onCommandResult COMMAND_SET_ACCEPT_TIME.success.mStartTime="+mStartTime+", mEndTime="+mEndTime);
            } else {
                Log.i(TAG, "onCommandResult COMMAND_SET_ACCEPT_TIME.fail.getReason="+miPushCommandMessage.getReason());
            }
        } else {
            Log.i(TAG, "onCommandResult getReason="+miPushCommandMessage.getReason());
        }
    }

    /**
     * 接收客户端向服务器发送注册命令后的响应结果
     * @param context
     * @param miPushCommandMessage
     */
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage){
        Log.i(TAG, "onReceiveRegisterResult is called. " + miPushCommandMessage.toString());
        String command = miPushCommandMessage.getCommand();
        List<String> arguments = miPushCommandMessage.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                Log.i(TAG, "onReceiveRegisterResult COMMAND_REGISTER.success.mRegId="+mRegId);
            } else {
                Log.i(TAG, "onReceiveRegisterResult COMMAND_REGISTER.fail");
            }
        } else {
            Log.i(TAG, "onReceiveRegisterResult getReason="+miPushCommandMessage.getReason());
        }

    }

}
