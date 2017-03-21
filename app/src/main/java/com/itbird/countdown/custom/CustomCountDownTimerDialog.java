package com.itbird.countdown.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.itbird.countdown.R;
import com.itbird.countdown.util.PhoneInfoUtils;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 自定义倒计时关闭 dialog
 * Created by itbird on 2016/6/18
 */
public class CustomCountDownTimerDialog extends Dialog {

    public CustomCountDownTimerDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomCountDownTimerDialog(Context context) {
        super(context);
    }

    public static class Builder {

        private Context context;
        private String title;
        private int patientnumber;
        private String message;
        private int messageColor;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;

        private OnClickListener
                positiveButtonClickListener,
                negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setPatientnumber(int patientnumber) {
            this.patientnumber = patientnumber;
            return this;
        }

        /**
         * Set the Dialog message from String
         *
         * @param message
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessageColor(int color) {
            this.messageColor = color;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * @param v
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button resource and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button text and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(OnClickListener listener) {
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Create the custom dialog
         */
        public CustomCountDownTimerDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomCountDownTimerDialog dialog = new CustomCountDownTimerDialog(context, R.style.dialog);
            dialog.setCanceledOnTouchOutside(false);
            View layout = inflater.inflate(R.layout.countdown_timerl_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
            p.width = (int) (PhoneInfoUtils.getScreenWidth(context) * 0.8); // 宽度设置为屏幕的0.8
            dialog.getWindow().setAttributes(p);

            if (!TextUtils.isEmpty(title)) {
                ((TextView) layout.findViewById(R.id.title)).setText(title);
            }

            ((TextView) layout.findViewById(R.id.patientnumber)).setText(patientnumber + "");

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                }
            };
            timer.schedule(task, 1000 * 3); //3秒后

            layout.findViewById(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                }
            });
            new CustomCountDownTimer(4 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {//倒计时设置
                    ((TextView) dialog.findViewById(R.id.tv_count_down)).setText((int) (millisUntilFinished / 1000) + "");
                }

                @Override
                public void onFinish() {
                }
            }.start();
            dialog.setContentView(layout);
            return dialog;
        }

    }
}

