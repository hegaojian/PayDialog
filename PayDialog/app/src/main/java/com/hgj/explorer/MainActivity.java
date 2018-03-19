package com.hgj.explorer;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hgj.paydialog.view.PayDialog;

/**
 * ==========================================
 * 作   者：何高建
 * 日   期：2018/3/19
 * 描   述：
 * ===========================================
 */

public class MainActivity extends AppCompatActivity implements PayDialog.PayInterface{
    PayDialog payDialog;
    boolean status = false;//替代方案，这里没有做网络请求，所以定义一个boolean字段来判断正确还是失败
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_payerror).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = false;
                payDialog = new PayDialog(MainActivity.this,"支付金额：80元",MainActivity.this);
                payDialog.show();
            }
        });
        findViewById(R.id.main_paysucc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = true;
                payDialog = new PayDialog(MainActivity.this,"支付金额：80元",MainActivity.this);
                //设置progress的颜色
                payDialog.setColorId(ContextCompat.getColor(MainActivity.this,R.color.black));
                payDialog.show();
            }
        });

    }

    @Override
    public void Payfinish(String password) {
        //这里是当用户输入密码完成时 得到输入密码的回调方法
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //这里是做请求校验的，暂时用停留2秒做假请求
                if(status){
                    //请求成功
                    payDialog.setSucc();
                }else{
                    //请求失败 传入失败理由
                    payDialog.setError("支付密码不正确");
                }

            }
        },2000);
    }

    @Override
    public void onSucc() {
        //回调 成功，关闭dialog 做自己的操作
        payDialog.cancel();
    }

    @Override
    public void onForget() {
        //当progress显示时，说明在请求网络，这时点击忘记密码不作处理
        if(payDialog.payPassView.progress.getVisibility()!=View.VISIBLE){
            Toast.makeText(MainActivity.this,"去找回密码",Toast.LENGTH_SHORT).show();

        }
    }
}
