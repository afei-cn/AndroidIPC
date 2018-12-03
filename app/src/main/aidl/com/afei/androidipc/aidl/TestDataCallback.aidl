// TestDataCallback.aidl
package com.afei.androidipc.aidl;

import com.afei.androidipc.aidl.TestData;

interface TestDataCallback {
    void onCallback(in TestData data);
}