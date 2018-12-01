// MyAidlInterface.aidl
package com.afei.androidipc.aidl;

// 在这里引用非默认的数据类型
import com.afei.androidipc.aidl.TestData;

interface MyAidlInterface {

    // 1. 基本数据类型
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble);

    // 2. String类 + CharSequence接口
    void stringType(String aString);

    // ****** 以下类型参数必须指明方向 (in / out / inout)
    // 3. 序列化的类
    void parcelableType(in Bundle aBundle);

    // 4. List 和 Map, 并且里面的元素也是可序列化的
    void listType(in List list);
    void mapType(in Map map);

    // 5. 自定义的序列化类
    void setTestData(in TestData data);
    TestData getTestData();

}
