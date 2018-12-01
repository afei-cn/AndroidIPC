/*
 * Copyright © 2018 by afei. All rights reserved.
 *
 * @file: MessengerService.java
 * @author: afei
 * @data: 2018年12月01日
 */

package com.afei.androidipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {

    public static final int MSG_WHAT_1 = 0x001;
    public static final String BUNDLE_KEY_1 = "str";
    private static final String TAG = "MessengerService";

    private Handler mHandler;
    private Messenger mMessenger;

    @Override
    public IBinder onBind(Intent intent) {
        mHandler = new ServiceHandler();
        mMessenger = new Messenger(mHandler);
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        return super.onUnbind(intent);
    }

    public static class ServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHAT_1:
                    // get msg from client
                    Log.d(TAG, ((Bundle) msg.obj).getString("str"));

                    // reply client
                    Message replyMsg = Message.obtain();
                    replyMsg.what = MSG_WHAT_1;
                    Bundle bundle = new Bundle();
                    bundle.putString(BUNDLE_KEY_1, "I'm from service!");
                    replyMsg.obj = bundle;
                    Messenger messenger = msg.replyTo;
                    try {
                        messenger.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
