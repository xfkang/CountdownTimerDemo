package com.itbird.countdown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.itbird.countdown.custom.VerificationCodeButton;

/**
 * 获取验证码button 测试Activity
 * Created by itbird on 2017/3/21
 */

public class VerificationCodeActivity extends AppCompatActivity{

    private VerificationCodeButton mVerificationCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationcode);
        mVerificationCodeButton = (VerificationCodeButton) findViewById(R.id.testbutton);
        mVerificationCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 60s倒计时，1s间隔
                mVerificationCodeButton.start(60000, 1000);
            }
        });

        //外界如果需要更多自定义行为可，以调用暂停、继续、停止
//        mVerificationCodeButton.pause();
//        mVerificationCodeButton.restart();
//        mVerificationCodeButton.stop();
    }
}
