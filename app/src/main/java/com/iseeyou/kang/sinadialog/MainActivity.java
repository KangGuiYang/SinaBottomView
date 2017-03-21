package com.iseeyou.kang.sinadialog;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by kGod on 2017/3/21.
 * Email 18252032703@163.com
 * Thank you for watching my code
 * 仿微博中间按钮菜单弹出效果 <br/> 原理很简单 延迟播放动画
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) this.findViewById(R.id.botton);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.botton:
                // Show Dialog
                CustomDialog mCustomDialog = new CustomDialog(this,R.style.my_dialog_style);
                mCustomDialog.show();
                break;
        }
    }


    class CustomDialog extends Dialog
    {

        private Handler mHandler = new Handler();

        private Context mContext;

        private LinearLayout mLinear1;
        private LinearLayout mLinear2;
        private LinearLayout mLinear3;
        private LinearLayout mLinear4;

        public CustomDialog(@NonNull Context context) {
            this(context,0);
            this.mContext = context;
        }

        public CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
            super(context, themeResId);
            this.mContext = context;
            init();
        }
        protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
            this.mContext = context;
            init();
        }


        private void init() {
            View mView  = LayoutInflater.from(mContext).inflate(R.layout.dialog_layout,null);
            this.setCancelable(true);
            this.setCanceledOnTouchOutside(true);
            this.setContentView(mView, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            mLinear1 = (LinearLayout) mView.findViewById(R.id.ll_1);
            mLinear2 = (LinearLayout) mView.findViewById(R.id.ll_2);
            mLinear3 = (LinearLayout) mView.findViewById(R.id.ll_3);
            mLinear4 = (LinearLayout) mView.findViewById(R.id.ll_4);
            // 这几个show和close的操作千万不要用集合循环去操作，否则在显示dialog时会出现dialog闪一下就消失的情况
            showAnim(mLinear1, 150);
            showAnim(mLinear2, 200);
            showAnim(mLinear3, 250);
            showAnim(mLinear4, 300);
            Button button = (Button) mView.findViewById(R.id.img_close);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeAnim(mLinear4, 100, 380);
                    closeAnim(mLinear3, 150, 430);
                    closeAnim(mLinear2, 200, 480);
                    closeAnim(mLinear1, 250, 530);
                }
            });
        }

        public void showDialog()
        {
            this.show();
        }


        private void showAnim(final LinearLayout i, int d) {
            i.setVisibility(View.INVISIBLE);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    i.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(i, "translationY", 1000, 0);
                    fadeAnim.setDuration(600);
                    KickBackAnimator kickAnimator = new KickBackAnimator();
                    kickAnimator.setDuration(600);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                    fadeAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            i.clearAnimation();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                }
            }, d);
        }


        private void closeAnim(final LinearLayout linearLayout, int i, int j) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(linearLayout, "translationY", 0, 1000);
                    fadeAnim.setDuration(600);
                    KickBackAnimator kickAnimator = new KickBackAnimator();
                    kickAnimator.setDuration(600);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                    fadeAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            linearLayout.setVisibility(View.INVISIBLE);
                            linearLayout.clearAnimation();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }
                    });
                }
            }, i);
            if (linearLayout.getId() == R.id.ll_1) {
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        CustomDialog.this.dismiss();
                    }
                }, j);
            }
        }

    }
}
