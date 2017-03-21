package com.itbird.countdown.custom;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * VerificationCodeButton
 * Created by itbird on 2017/3/21
 */

public class VerificationCodeButton extends Button {
    private CustomCountDownTimer mCustomCountDownTimer;

    public VerificationCodeButton(Context context) {
        super(context);
    }

    public VerificationCodeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerificationCodeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * start countdown
     * @param millisecond 总倒计时时间
     * @param countDownInterval 倒计时间隔时间
     */
    public void start(long millisecond, long countDownInterval) {
        if (millisecond <= 0) {
            return;
        }

        if (null != mCustomCountDownTimer) {
            mCustomCountDownTimer.stop();
            mCustomCountDownTimer = null;
        }

        mCustomCountDownTimer = new CustomCountDownTimer(millisecond, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                setEnabled(false);
                setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                setText(millisUntilFinished / 1000 + "秒后重新获取");
            }

            @Override
            public void onFinish() {
                setEnabled(true);
                setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                setText("获取验证码");
            }
        };
        mCustomCountDownTimer.start();
    }

    /**
     * stop countdown
     */
    public void stop() {
        if (null != mCustomCountDownTimer) {
            mCustomCountDownTimer.stop();
        }
    }

    /**
     * pause countdown
     */
    public void pause() {
        if (null != mCustomCountDownTimer) {
            mCustomCountDownTimer.pause();
        }
    }

    /**
     * pause countdown
     */
    public void restart() {
        if (null != mCustomCountDownTimer) {
            mCustomCountDownTimer.restart();
        }
    }

    private class TimerCount extends CountDownTimer {
        public TimerCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            setClickable(true);
            setText("获取验证码");
        }

        @Override
        public void onTick(long arg0) {
            setClickable(false);
            setText(arg0 / 1000 + "秒后重新获取");
        }
    }
}
