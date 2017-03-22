package com.itbird.countdown;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.itbird.countdown.custom.CustomCountDownTimerDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.VerificationCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, VerificationCodeActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.CountDownTimerDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountDownTimerDialog(MainActivity.this, 7);
            }
        });
        findViewById(R.id.CountDownTimerView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CountDownTimerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showCountDownTimerDialog(Context context, int patientnumber) {
        CustomCountDownTimerDialog.Builder customBuilder = new CustomCountDownTimerDialog.Builder(context);
        customBuilder.setTitle("发送成功")
                .setPatientnumber(patientnumber)
                .setNegativeButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null) {
                            dialog.dismiss();
                            // TODO: 2017/3/21  after close, do something
                        }
                    }
                });
        Dialog dialog = customBuilder.create();
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }
}
