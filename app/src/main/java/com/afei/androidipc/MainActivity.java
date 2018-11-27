/*
 * Copyright © 2018 by afei. All rights reserved.
 *
 * @file: MainActivity.java
 * @author: afei
 * @data: 2018年11月27日
 */

package com.afei.androidipc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mAidlBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mAidlBtn = findViewById(R.id.aidl_btn);
        mAidlBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aidl_btn:
                break;
        }
    }
}
