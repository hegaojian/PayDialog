package com.hgj.paydialog.view;

////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                        //
////////////////////////////////////////////////////////////////////

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hgj.paydialog.R;

/**
 * ==========================================
 * 作   者：何高建
 * 日   期：2018/3/19
 * 描   述：
 * ===========================================
 */

public class PayDialog extends Dialog implements OnPasswordInputFinish{
    public PayPassView payPassView;
    PayInterface payInterface;
    boolean isclear = false;
    String content;
    private int colorId;
    public PayDialog(@NonNull Context context, String content, PayInterface payInterface) {
        // 在构造方法里, 传入主题
        super(context, R.style.BottomDialogStyle);
        this.content = content;
        this.payInterface = payInterface;
        // 拿到Dialog的Window, 修改Window的属性
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化
        payPassView = new PayPassView(getContext());
        //设置内容 如支付金额：80元
        payPassView.content.setText(content);
        if(colorId!=0){
            payPassView.progress.setArcColor(colorId);
        }
        setContentView(payPassView);
        payPassView.setOnFinishInput(this);
        payPassView.paypass_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////当progress显示时，说明在请求网络，这时点击关闭不作处理
                if(payPassView.progress.getVisibility()!=View.VISIBLE) {
                    cancel();
                }
            }
        });
        payPassView.progress.setOnPasswordCorrectlyListener(new MDProgressBar.OnPasswordCorrectlyListener() {
            @Override
            public void onPasswordCorrectly() {
                //progress动画完成后 回调
                if(payInterface!=null){
                    payInterface.onSucc();
                }
            }
        });
        payPassView.forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(payInterface!=null){
                    payInterface.onForget();
                }
            }
        });
    }

    @Override
    public void inputFinish() {
        payPassView.progress.setVisibility(View.VISIBLE);
        payPassView.keyboard.setVisibility(View.INVISIBLE);
        if(payInterface!=null){
            payInterface.Payfinish(payPassView.getStrPassword());
        }
    }

    @Override
    public void inputFirst() {
        payPassView.tips.setVisibility(View.INVISIBLE);
    }
    public interface PayInterface{
        public void Payfinish(String password);
        public void onSucc();
        public void onForget();
    }
    public void setError(String errorText){
        payPassView.tips.setVisibility(View.VISIBLE);
        payPassView.tips.setText(errorText);
        check();
    }

    public void setSucc(){
        payPassView.progress.setSuccessfullyStatus();
    }

    public void check(){
        isclear = false;
        TextView[] textViews =  payPassView.tvList;
        isclear = false;
        for (TextView textview:textViews) {
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .repeat(0)
                    .onEnd(new YoYo.AnimatorCallback() {
                        @Override
                        public void call(Animator animator) {
                            if(!isclear){
                                payPassView.clearText();
                                payPassView.progress.setVisibility(View.GONE);
                                payPassView.keyboard.setVisibility(View.VISIBLE);
                                isclear = true;
                            }
                        }
                    })
                    .playOn(textview);
        }
        YoYo.with(Techniques.Shake)
                .duration(500)
                .repeat(0)
                .playOn(payPassView.tips);
    }

    /**
     * 设置progress的颜色
     */
    public void setColorId(int colorId) {
        this.colorId = colorId;
    }
}
