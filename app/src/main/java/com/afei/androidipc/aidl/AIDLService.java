/*
 * Copyright © 2018 by afei. All rights reserved.
 *
 * @file: AIDLService.java
 * @author: afei
 * @data: 2018年12月01日
 */

package com.afei.androidipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.Map;

public class AIDLService extends Service {

    private static final String TAG = "AIDLService";
    private static final String PERMISSION = "com.afei.androidipc.permission.ACCESS_SERVICE";
    private RemoteCallbackList<TestDataCallback> mListenerList = new RemoteCallbackList<>();

    private final Binder mBinder = new MyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double
                aDouble) throws RemoteException {
            Log.d(TAG, "basicTypes: anInt: " + anInt);
            Log.d(TAG, "basicTypes: aLong: " + aLong);
            Log.d(TAG, "basicTypes: aBoolean: " + aBoolean);
            Log.d(TAG, "basicTypes: aFloat: " + aFloat);
            Log.d(TAG, "basicTypes: aDouble: " + aDouble);
        }

        @Override
        public void stringType(String aString) throws RemoteException {
            Log.d(TAG, "stringType: " + aString);
        }

        @Override
        public void parcelableType(Bundle aBundle) throws RemoteException {
            int anInt = aBundle.getInt("int");
            Log.d(TAG, "parcelableType: get int: " + anInt);
        }

        @Override
        public void listType(List list) throws RemoteException {
            Log.d(TAG, "listType: list size: " + list.size());
            Log.d(TAG, "listType: " + list.toString());
        }

        @Override
        public void mapType(Map map) throws RemoteException {
            Log.d(TAG, "mapType: map size: " + map.size());
            Log.d(TAG, "mapType: " + map.toString());
        }

        @Override
        public void setTestData(TestData data) throws RemoteException {
            Log.d(TAG, "setTestData: " + data.toString());
        }

        @Override
        public TestData getTestData() throws RemoteException {
            return new TestData("I'm from AIDLService", 666);
        }

        @Override
        public void registerListener(TestDataCallback listener) throws RemoteException {
            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(TestDataCallback listener) throws RemoteException {
            mListenerList.unregister(listener);
        }

        @Override
        public void testCallback() throws RemoteException {
            new ServiceWorker().start(); // 启动一个线程
        }

        // 权限认证
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            // 方式1：验证客户端是否申请拥有 "com.afei.androidipc.permission.ACCESS_SERVICE" 权限
            int check = checkCallingOrSelfPermission(PERMISSION);
            if (check == PackageManager.PERMISSION_DENIED) {
                Log.e(TAG, "onTransact: permission denied: " + PERMISSION);
                return false;
            }
            // 方式2：验证包名
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            // 判断包名是否符合要求
            if (packageName == null || !packageName.startsWith("com.afei")) {
                Log.e(TAG, "onTransact: packageName error: " + packageName);
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder; // 在这里返回 mBinder 对象实现绑定
    }

    private class ServiceWorker extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(3000); // 模拟耗时任务
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int n = mListenerList.beginBroadcast(); // beginBroadcast和finishBroadcast必须配对使用
            for (int i = 0; i < n; i++) {
                TestDataCallback listener = mListenerList.getBroadcastItem(i);
                if (listener != null) {
                    try {
                        listener.onCallback(new TestData("I'm callback test data", 200));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            mListenerList.finishBroadcast(); // finishBroadcast和beginBroadcast必须配对使用
        }
    }

}
