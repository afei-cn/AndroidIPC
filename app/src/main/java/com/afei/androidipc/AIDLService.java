/*
 * Copyright © 2018 by afei. All rights reserved.
 *
 * @file: AIDLService.java
 * @author: afei
 * @data: 2018年11月28日
 */

package com.afei.androidipc;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.Map;

public class AIDLService extends Service {

    private static final String TAG = "AIDLService";

    private final MyAidlInterface.Stub mStub = new MyAidlInterface.Stub() {
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
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }
}
