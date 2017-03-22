package com.itbird.countdown;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 计时器演示Activity
 * Created by itbird on 2017/3/21
 */
public class CountDownTimerActivity extends AppCompatActivity {
    /**
     * hour:min:sec layout
     */
    private LinearLayout mRealTimeLayout;
    private TextView mHourTextView, mMinTextView, mSecTextView;

    /**
     * mStartButton, mResumeButton, mPauseButton, mResetButton
     */
    private Button mStartButton, mResumeButton, mPauseButton, mResetButton;
    /**
     * Time Select Widget:TimePicker
     */
    private TimePicker mTimePicker;
    /**
     * total time
     */
    private int mTotalTime = 0;
    private Timer mTimer = new Timer();
    private TimerTask mTimerTask;

    /**
     * handler msg type : start and stop
     */
    private static final int MSG_WHAT_TIME_STOP = 1;
    private static final int MSG_WHAT_TIME_TICK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_timerview_layout);
        initView();
        initAction();
    }

    /**
     * init Action
     */
    private void initAction() {
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });
        mResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    /**
     * start and resume operation
     * mTimerTask == null ? start : resume
     */
    private void startTimer() {
        if (mTimerTask == null) {
            mTotalTime = mTimePicker.getCurrentHour() * 60 * 60
                    + mTimePicker.getCurrentMinute() * 60;
        }
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mTotalTime--;
                mHandler.sendEmptyMessage(MSG_WHAT_TIME_TICK);
                if (mTotalTime <= 0) {
                    resetTimer();
                }
            }
        };
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    /**
     * pause operation
     */
    private void pauseTimer() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
        mPauseButton.setVisibility(View.GONE);
        mResumeButton.setVisibility(View.VISIBLE);
    }

    /**
     * reset operation
     */
    private void resetTimer() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        mHandler.sendEmptyMessage(MSG_WHAT_TIME_STOP);
    }
    /**
     * Init View
     */
    private void initView() {
        mRealTimeLayout = (LinearLayout) findViewById(R.id.realtimelayout);
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);
        // 设置TimePicker为24小时制
        mTimePicker.setIs24HourView(true);
        // 初始化TimePicker时间为00:01
        mTimePicker.setCurrentHour(0);
        mTimePicker.setCurrentMinute(1);
        mStartButton = (Button) findViewById(R.id.btnStart);
        mResumeButton = (Button) findViewById(R.id.btnResume);
        mResetButton = (Button) findViewById(R.id.btnReset);
        mPauseButton = (Button) findViewById(R.id.btnPause);
        mStartButton.setVisibility(VISIBLE);
        mResumeButton.setVisibility(GONE);
        mResetButton.setVisibility(GONE);
        mPauseButton.setVisibility(GONE);
        mRealTimeLayout.setVisibility(GONE);

        mHourTextView = (TextView) findViewById(R.id.etHour);
        mMinTextView = (TextView) findViewById(R.id.etMinute);
        mSecTextView = (TextView) findViewById(R.id.etSecond);
        mHourTextView.setText("00");
        mMinTextView.setText("00");
        mSecTextView.setText("00");
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_WHAT_TIME_STOP:
                    if (mTotalTime <= 0) {
                        // 对话框不能在子线程中显示，所以要在Handler中执行
                        new AlertDialog.Builder(CountDownTimerActivity.this).setTitle("Time is up!")
                                .setMessage("Time is up!")
                                .setNegativeButton("Cancel", null).show();
                    }
                    mPauseButton.setVisibility(GONE);
                    mResetButton.setVisibility(GONE);
                    mResumeButton.setVisibility(GONE);
                    mStartButton.setVisibility(VISIBLE);
                    mRealTimeLayout.setVisibility(GONE);
                    mTimePicker.setVisibility(VISIBLE);
                    mHourTextView.setText("00");
                    mMinTextView.setText("00");
                    mSecTextView.setText("00");
                    break;
                case MSG_WHAT_TIME_TICK:
                    int hour = mTotalTime / 60 / 60;
                    int min = (mTotalTime / 60) % 60;
                    int sec = mTotalTime % 60;
                    mHourTextView.setText(String.format("%02d", hour));
                    mMinTextView.setText(String.format("%02d", min));
                    mSecTextView.setText(String.format("%02d", sec));

                    mPauseButton.setVisibility(VISIBLE);
                    mResetButton.setVisibility(VISIBLE);
                    mResumeButton.setVisibility(GONE);
                    mStartButton.setVisibility(GONE);
                    mRealTimeLayout.setVisibility(VISIBLE);
                    mTimePicker.setVisibility(GONE);
                    break;
                default:
                    break;
            }
        }
    };
}
