package com.tanker.basemodule.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tanker.basemodule.R;
import com.tanker.basemodule.utils.EmptyUtils;


/**
 * @author lwj
 * @ClassName: CommonDialog
 * @Description:
 * @date 2018/5/28 15:41
 */
public class CommonDialog implements View.OnClickListener {
    //标题title
    private TextView tv_title;
    //取消X按钮
    private ImageButton ib_cancle;
    //公司名称布局包裹
    private LinearLayout ll_company_name;
    //公司名称输入框
    private EditText et_company_name;
    //内容提示布局包裹
    private LinearLayout ll_tips;
    //内容提示
    private TextView tv_tips;
    //确认按钮
    private Button bt_confirm;
    //取消按钮
    private Button bt_cancel;


    private Context mContext;
    private Dialog dialog;
    private String title = "";
    private String content = "";
    private Boolean isOneCon = true;//true为只有一个确定按钮false为有确认和取消按钮
    private Boolean isCancelable = true;//点击窗口外和按返回键是否可以关闭窗口
    private Double mHeight = 0.0;
    private Double mWidth = 0.85;
    private Display display;

    public CommonDialog(Context mContext, String content) {
        this.mContext = mContext;
        this.content = content;
        init(mContext);
    }

    public CommonDialog(Boolean isCancelable, Context mContext, String content) {
        this.isCancelable = isCancelable;
        this.mContext = mContext;
        this.content = content;
        init(mContext);
    }
    public CommonDialog(Context mContext, String content, Boolean isOneCon) {
        this.mContext = mContext;
        this.content = content;
        this.isOneCon = isOneCon;
        init(mContext);
    }
    public CommonDialog(Context mContext, String title, String content) {
        this.mContext = mContext;
        this.title = title;
        this.content = content;
        init(mContext);
    }
    public CommonDialog(Context mContext, String title, String content, Boolean isOneCon) {
        this.mContext = mContext;
        this.title = title;
        this.content = content;
        this.isOneCon = isOneCon;
        init(mContext);
    }
    public CommonDialog(Context mContext, String title, String content, Boolean isOneCon, Double mWidth) {
        this.mContext = mContext;
        this.title = title;
        this.content = content;
        this.isOneCon = isOneCon;
        this.mWidth = mWidth;
        init(mContext);
    }

    private void init(Context mContext) {
        WindowManager windowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        StartDialog();
    }

    public void StartDialog() {
        dialog = new Dialog(mContext, R.style.AlertDialogStyle);// 弹出框基本样式不带标题的弹出框
        //自定义发XML
        dialog.setContentView(R.layout.dialog_common);
        //捕获主页键和拨号键
        dialog.getWindow().setFlags(0x80000000, 0x80000000);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_HOME || keyCode == event.KEYCODE_CALL ) {
                    return true;
                }
                return false;
            }
        });

        LinearLayout dialog_view = dialog.findViewById(R.id.dialog_view);
        dialog_view.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * mWidth), LinearLayout.LayoutParams.WRAP_CONTENT));

        //标题title
        tv_title=dialog.findViewById(R.id.tv_title);
        //取消X按钮
        ib_cancle=dialog.findViewById(R.id.ib_cancle);
        ib_cancle.setOnClickListener(this);
        //公司名称布局包裹
        ll_company_name=dialog.findViewById(R.id.ll_company_name);
        //公司名称输入框
        et_company_name=dialog.findViewById(R.id.et_company_name);
        //内容提示布局包裹
        ll_tips=dialog.findViewById(R.id.ll_tips);
        //添加车主成功提示
        tv_tips=dialog.findViewById(R.id.tv_tips);
        //确认按钮
        bt_confirm=dialog.findViewById(R.id.bt_confirm);
        //取消按钮
        bt_cancel=dialog.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(this);

        if(isOneCon){
            dialog.setCancelable(false);//点击窗口外和按返回键是否可以关闭窗口
            bt_confirm.setVisibility(View.VISIBLE);
        }else {
            dialog.setCancelable(false);//点击窗口外和按返回键是否可以关闭窗口
            bt_cancel.setVisibility(View.VISIBLE);
        }

        if(!EmptyUtils.isEmpty(title)){
            tv_title.setText(title);
        }

        if(!EmptyUtils.isEmpty(content)){
            tv_tips.setText(content);
        }
        dialog.show();

    }

    public Dialog getDialog() {
        return dialog;
    }
    public void getDismiss() {
        if (dialog!=null){
            dialog.dismiss();
        }
    }

    public TextView getTv_title() {
        return tv_title;
    }

    public void setTv_title(TextView tv_title) {
        this.tv_title = tv_title;
    }

    public ImageButton getIb_cancle() {
        return ib_cancle;
    }

    public void setIb_cancle(ImageButton ib_cancle) {
        this.ib_cancle = ib_cancle;
    }

    public LinearLayout getLl_company_name() {
        return ll_company_name;
    }

    public void setLl_company_name(LinearLayout ll_company_name) {
        this.ll_company_name = ll_company_name;
    }

    public EditText getEt_company_name() {
        return et_company_name;
    }

    public void setEt_company_name(EditText et_company_name) {
        this.et_company_name = et_company_name;
    }

    public TextView getTv_tips() {
        return tv_tips;
    }

    public void setTv_tips(TextView tv_tips) {
        this.tv_tips = tv_tips;
    }

    public Button getBt_confirm() {
        return bt_confirm;
    }

    public void setBt_confirm(Button bt_confirm) {
        this.bt_confirm = bt_confirm;
    }

    public Button getBt_cancel() {
        return bt_cancel;
    }

    public void setBt_cancel(Button bt_cancel) {
        this.bt_cancel = bt_cancel;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public LinearLayout getLl_tips() {
        return ll_tips;
    }

    public void setLl_tips(LinearLayout ll_tips) {
        this.ll_tips = ll_tips;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ib_cancle){
            getDismiss();
        }else if(v.getId() == R.id.bt_cancel){
            getDismiss();
        }
    }
}
