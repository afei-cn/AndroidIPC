/*
 * Copyright © 2018 by afei. All rights reserved.
 *
 * @file: MainActivity.java
 * @author: afei
 * @data: 2018年11月27日
 */

package com.afei.androidipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button mAidlBtn;

    private ITestAidl mITestAidl;
    private ServiceConnection mAidlServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: " + name);
            mITestAidl = ITestAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // 绑定服务
        Intent intent = new Intent(MainActivity.this, AIDLService.class);
        bindService(intent, mAidlServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        mAidlBtn = findViewById(R.id.aidl_btn);
        mAidlBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aidl_btn:
                testAidl();
                break;
        }
    }

    private void testAidl() {
        if (mITestAidl == null) {
            Log.e(TAG, "testAidl: service not connect!");
            return;
        }
        try {
            mITestAidl.basicTypes(1, 2, true, 3.0f, 4.0);

            mITestAidl.stringType("I'm MainActivity");

            Bundle bundle = new Bundle();
            bundle.putInt("int", 1);
            mITestAidl.parcelableType(bundle);

            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                list.add(i);
            }
            mITestAidl.listType(list);
            Map<Integer, String> map = new HashMap<>();
            map.put(1, "value1");
            map.put(2, "value2");
            map.put(3, "value3");
            mITestAidl.mapType(map);

            mITestAidl.setTestData(new TestData("I'm MainActivity", 666));
            Log.d(TAG, mITestAidl.getTestData().toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mAidlServiceConnection);
        super.onDestroy();
    }
}
