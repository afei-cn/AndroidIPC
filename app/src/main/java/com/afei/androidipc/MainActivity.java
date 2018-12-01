/*
 * Copyright © 2018 by afei. All rights reserved.
 *
 * @file: MainActivity.java
 * @author: afei
 * @data: 2018年11月27日
 */

package com.afei.androidipc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.afei.androidipc.aidl.AIDLActivity;
import com.afei.androidipc.messenger.MessengerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button mAidlBtn;
    private Button mMessengerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mAidlBtn = findViewById(R.id.aidl_btn);
        mAidlBtn.setOnClickListener(this);
        mMessengerBtn = findViewById(R.id.messenger_btn);
        mMessengerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aidl_btn:
                startActivity(new Intent(this, AIDLActivity.class));
                break;
            case R.id.messenger_btn:
                startActivity(new Intent(this, MessengerActivity.class));
                break;
        }
    }
}
