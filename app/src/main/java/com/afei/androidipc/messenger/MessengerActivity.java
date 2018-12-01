/*
 * Copyright © 2018 by afei. All rights reserved.
 *
 * @file: MessengerActivity.java
 * @author: afei
 * @data: 2018年12月01日
 */

package com.afei.androidipc.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.afei.androidipc.R;

public class MessengerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MessengerActivity";
    private Button mSendBtn;

    private Handler mHandler; // 消息处理者
    private Messenger mReplyMessenger; // 接收消息的信使
    private Messenger mSendMessenger; // 发送消息的信使

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: " + name);
            mSendMessenger = new Messenger(service);
            mHandler = new ClientHandler();
            mReplyMessenger = new Messenger(mHandler);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        initView();
        // 绑定服务
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    private void initView() {
        mSendBtn = findViewById(R.id.send_btn);
        mSendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn:
                testMessenger();
                break;
        }
    }

    private void testMessenger() {
        if (mSendMessenger == null) {
            Log.e(TAG, "testMessenger: service not connection!");
            return;
        }
        Message msg = Message.obtain();
        msg.what = MessengerService.MSG_WHAT_1;
        Bundle bundle = new Bundle();
        bundle.putString(MessengerService.BUNDLE_KEY_1, "I'm from client!");
        msg.obj = bundle;
        // 在这里声明使用哪个 Messenger 来接收服务端返回的信息
        msg.replyTo = mReplyMessenger;
        try {
            mSendMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static class ClientHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessengerService.MSG_WHAT_1:
                    Log.d(TAG, ((Bundle) msg.obj).getString(MessengerService.BUNDLE_KEY_1));
                    break;
                default:
                    break;
            }
        }
    }
}
