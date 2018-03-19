package com.hgj.paydialog.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hgj.paydialog.R;

/**
 * ==========================================
 * 作   者：何高建
 * 日   期：2018/3/19
 * 描   述：
 * ===========================================
 */

public class PayPassView extends LinearLayout implements OnClickListener {

    public TextView[] tvList;
    private TextView[] tv;
    private ImageView iv_del;
    public ImageView paypass_close;
    public TextView title;
    public TextView content;
    public TextView tips;
    public LinearLayout keyboard;
    public MDProgressBar progress;
    public TextView forget;
    public LinearLayout paydialog_linear;
    private View view;
    private String strPassword;     //输入的密码
    private int currentIndex = -1;    //用于记录当前输入密码格位置
    public  OnPasswordInputFinish passwordInputFinish ;
    public PayPassView(Context context) {
        this(context, null);
    }

    public PayPassView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PayPassView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        view = View.inflate(context, R.layout.pay_pass, null);
        tvList = new TextView[6];
        tips = (TextView)view.findViewById(R.id.paypass_tips);
        forget = (TextView)view.findViewById(R.id.paypass_forget);
        title = (TextView)view.findViewById(R.id.paypass_title);
        content = (TextView)view.findViewById(R.id.paypass_content);
        keyboard = (LinearLayout) view.findViewById(R.id.keyboard);
        paydialog_linear = (LinearLayout) view.findViewById(R.id.paydialog_linear);
        progress = (MDProgressBar) view.findViewById(R.id.paypass_progress);
        tvList[0] = (TextView)view.findViewById(R.id.pay_box1);
        tvList[1] = (TextView)view. findViewById(R.id.pay_box2);
        tvList[2] = (TextView) view.findViewById(R.id.pay_box3);
        tvList[3] = (TextView) view.findViewById(R.id.pay_box4);
        tvList[4] = (TextView) view.findViewById(R.id.pay_box5);
        tvList[5] = (TextView) view.findViewById(R.id.pay_box6);
        tv = new TextView[10];
        tv[0]= (TextView)view.findViewById(R.id.pay_keyboard_zero);
        tv[1] = (TextView)view.findViewById(R.id.pay_keyboard_one);
        tv[2]= (TextView)view.findViewById(R.id.pay_keyboard_two);
        tv[3] = (TextView)view.findViewById(R.id.pay_keyboard_three);
        tv[4] = (TextView)view.findViewById(R.id.pay_keyboard_four);
        tv[5]= (TextView)view.findViewById(R.id.pay_keyboard_five);
        tv[6] = (TextView)view.findViewById(R.id.pay_keyboard_sex);
        tv[7] = (TextView)view.findViewById(R.id.pay_keyboard_seven);
        tv[8]= (TextView)view.findViewById(R.id.pay_keyboard_eight);
        tv[9] = (TextView)view.findViewById(R.id.pay_keyboard_nine);
        iv_del= (ImageView)view.findViewById(R.id.pay_keyboard_del);
        paypass_close= (ImageView)view.findViewById(R.id.paypass_close);
        for(int i=0;i<10;i++){
            tv[i].setOnClickListener(this);
        }
        iv_del.setOnClickListener(this);
        addView(view);
    }

    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput(final OnPasswordInputFinish pass) {
        this.passwordInputFinish = pass;
        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    strPassword = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
                    for (int i = 0; i < 6; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    passwordInputFinish.inputFinish();    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.pay_keyboard_one) {
            getPass("1");

        } else if (i == R.id.pay_keyboard_two) {
            getPass("2");

        } else if (i == R.id.pay_keyboard_three) {
            getPass("3");

        } else if (i == R.id.pay_keyboard_four) {
            getPass("4");

        } else if (i == R.id.pay_keyboard_five) {
            getPass("5");

        } else if (i == R.id.pay_keyboard_sex) {
            getPass("6");

        } else if (i == R.id.pay_keyboard_seven) {
            getPass("7");

        } else if (i == R.id.pay_keyboard_eight) {
            getPass("8");

        } else if (i == R.id.pay_keyboard_nine) {
            getPass("9");

        } else if (i == R.id.pay_keyboard_zero) {
            getPass("0");

        } else if (i == R.id.pay_keyboard_del) {
            if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界
                tvList[currentIndex--].setText("");
            }

        }
    }
    public void getPass(String str){
        if (currentIndex >= -1 && currentIndex < 5) {
            tvList[++currentIndex].setText(str);
        }
        if(currentIndex==0){
            passwordInputFinish.inputFirst();
        }
    }

    public String getStrPassword() {
        return strPassword;
    }
    public void clearText() {
        for (TextView textview:tvList) {
            textview.setText("");
        }
        currentIndex=-1;
    }

}
