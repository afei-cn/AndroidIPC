/*
 * Copyright © 2018 by afei. All rights reserved.
 *
 * @file: TestData.java
 * @author: afei
 * @data: 2018年12月01日
 */

package com.afei.androidipc.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class TestData implements Parcelable {

    private String mString;
    private int mInt;

    public TestData(String string, int anInt) {
        mString = string;
        mInt = anInt;
    }

    public String getString() {
        return mString;
    }

    public void setString(String string) {
        mString = string;
    }

    public int getInt() {
        return mInt;
    }

    public void setInt(int anInt) {
        mInt = anInt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mString);
        dest.writeInt(this.mInt);
    }

    protected TestData(Parcel in) {
        this.mString = in.readString();
        this.mInt = in.readInt();
    }

    public static final Parcelable.Creator<TestData> CREATOR = new Parcelable.Creator<TestData>() {
        @Override
        public TestData createFromParcel(Parcel source) {
            return new TestData(source);
        }

        @Override
        public TestData[] newArray(int size) {
            return new TestData[size];
        }
    };

    @Override
    public String
    toString() {
        return "TestData{" + "mString='" + mString + '\'' + ", mInt=" + mInt + '}';
    }
}
