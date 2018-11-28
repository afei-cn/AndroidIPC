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

    private MyAidlInterface mAidlInterface;
    private ServiceConnection mAidlServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: " + name);
            mAidlInterface = MyAidlInterface.Stub.asInterface(service);
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
        if (mAidlInterface == null) {
            Log.e(TAG, "testAidl: service not connect!");
            return;
        }
        try {
            // 1. 基本数据类型
            mAidlInterface.basicTypes(1, 2, true, 3.0f, 4.0);
            // 2. String类 + CharSequence接口
            mAidlInterface.stringType("test string");
            // 3. 序列化的类
            Bundle bundle = new Bundle();
            bundle.putInt("int", 1);
            mAidlInterface.parcelableType(bundle);
            // 4. List 和 Map, 并且里面的元素也是可序列化的
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                list.add(i);
            }
            mAidlInterface.listType(list);
            Map<Integer, String> map = new HashMap<>();
            map.put(1, "value1");
            map.put(2, "value2");
            map.put(3, "value3");
            mAidlInterface.mapType(map);
            // 5. 自定义的序列化类
            mAidlInterface.setTestData(new TestData("I'm from MainActivity", 666));
            Log.d(TAG, mAidlInterface.getTestData().toString());
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
