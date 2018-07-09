package com.tanker.basemodule.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tanker.basemodule.R;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.constants.DialogConst;
import com.tanker.basemodule.utils.DensityUtils;
import com.tanker.basemodule.utils.StringUtils;

import java.lang.ref.WeakReference;


/**
 * Created by Ronny on 2017/12/15.
 */

public class TankerTwoButtonDialog extends Dialog {

    public final static int IMAGE_DIALOG = 0;
    public final static int IMAGE_DIALOG_WITHOUT_IMG = 1;
    public final static int IMAGE_DIALOG_WITH_TWO_CONTENT = 2;
    public final static int PHONE_DIALOG = 3;
    public final static int EDIT_DIALOG = 4;

    private final static String TAG = "TankerDialog";
    protected WeakReference<Activity> context;
    protected boolean cancelable = false;
    protected boolean cancelOnTouchOutside = false;
    protected Bundle data = null;
    protected RelativeLayout llParent;
    protected Button btnConfirm;
    protected Button btnClose;
    protected ImageButton btnCancel;//关闭、确认、取消的按钮
    protected int messageType;//消息的type
    protected boolean notCancelDialog;
    protected OptionListener optionListener;
    private LinearLayout ll_image_dialog;
    private RelativeLayout rl_phone_dialog;
    private TextView id_tv_second_content;
    private TextView id_tv_content;
    private ImageView id_iv_image;
    private TextView pd_tv_title;
    private TextView pd_tv_title1;
    private TextView pd_tv_content;
    private LinearLayout ll_edit_dialog;
    private TextView ed_tv_title;
    private EditText ed_ed_content;

    public TankerTwoButtonDialog(@NonNull Activity context) {
        super(context);

        this.context = new WeakReference<>(context);
    }

    public TankerTwoButtonDialog(@NonNull Activity context, int theme) {
        super(context, theme);
        this.context = new WeakReference<>(context);
    }

    protected TankerTwoButtonDialog(@NonNull Activity context, boolean cancelable,
                                    @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = new WeakReference<>(context);
    }

    public TankerTwoButtonDialog(@NonNull Activity context, boolean cancelable, boolean cancelOnTouchOutside) {
        super(context);
        this.context = new WeakReference<>(context);
        this.cancelable = cancelable;
        this.cancelOnTouchOutside = cancelOnTouchOutside;
    }

    public TankerTwoButtonDialog(@NonNull Activity context, boolean cancelable, boolean cancelOnTouchOutside,
                                 Bundle data) {
        super(context);
        this.context = new WeakReference<>(context);
        this.cancelable = cancelable;
        this.cancelOnTouchOutside = cancelOnTouchOutside;
        this.data = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConfig();
        initContentView();
        initView();
        initDialog();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initDialog();
        initListener();

    }

    protected void initDialog() {
        initData();
    }

    protected void initConfig() {
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.setCancelable(cancelable);
        this.setCanceledOnTouchOutside(cancelOnTouchOutside);
    }

    public String getEditTextString() {
        return ed_ed_content.getText().toString();
    }

    protected void initContentView() {
        setContentView(R.layout.dialog_tanker);
    }

    protected void initView() {
        llParent = findViewById(R.id.ll_parent);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnCancel = findViewById(R.id.btn_cancle);
        btnClose = findViewById(R.id.btn_close);

        //带有提示图的Dialog
        ll_image_dialog = findViewById(R.id.ll_image_dialog);
        id_iv_image = findViewById(R.id.id_iv_image);
        id_tv_content = findViewById(R.id.id_tv_content);
        id_tv_second_content = findViewById(R.id.id_tv_second_content);

        //电话Dialog
        rl_phone_dialog = findViewById(R.id.rl_phone_dialog);
        pd_tv_title = findViewById(R.id.pd_tv_title);
        pd_tv_content = findViewById(R.id.pd_tv_content);

        //可输入弹窗
        ll_edit_dialog = findViewById(R.id.ll_edit_dialog);
        ed_tv_title = findViewById(R.id.ed_tv_title);
        ed_ed_content = findViewById(R.id.ed_ed_content);

    }

    protected void initData() {
        if (data != null) {
            messageType = data.getInt(DialogConst.DIALOG_MESSAGE_TYPE, -1);
            initDialogSize();
            initVisibility();
        }
    }

    protected void initDialogSize() {
        int height = data.getInt(DialogConst.DIALOG_HEIGHT, DialogConst.DIALOG_DEFAULT_HEIGHT);
        int width = data.getInt(DialogConst.DIALOG_WIDTH, DialogConst.DIALOG_DEFAULT_WIDTH);
        ViewGroup.LayoutParams params = llParent.getLayoutParams();
        if (height > 0) {
            params.height = DensityUtils.dip2px(height);
        }
        if (width > 0) {
            params.width = DensityUtils.dip2px(width);
        }
        llParent.setLayoutParams(params);
    }


    protected void initVisibility() {
        boolean isShowCancel = data.getBoolean(DialogConst.DIALOG_SHOW_CANCEL, true);
        boolean isShowClose = data.getBoolean(DialogConst.DIALOG_SHOW_CLOSE, true);
        if (isShowCancel) {
            btnCancel.setVisibility(View.VISIBLE);
        } else {
            btnCancel.setVisibility(View.GONE);
        }
        if (isShowClose) {
            btnClose.setVisibility(View.VISIBLE);
        }
        ll_image_dialog.setVisibility(View.GONE);
        ll_edit_dialog.setVisibility(View.GONE);
        rl_phone_dialog.setVisibility(View.GONE);
        String titleText = data.getString(DialogConst.DIALOG_TITLE_TEXT, "");
        int srcImage = data.getInt(DialogConst.DIALOG_IMAGE_RES_ID, -1);
        String contentText = data.getString(DialogConst.DIALOG_CONTENT_TEXT, "");
        String contentHintText = data.getString(DialogConst.DIALOG_CONTENT_HINT, "");
        String contentSecondText = data.getString(DialogConst.DIALOG_SECOND_CONTENT_TEXT, "");
        String confirmText = data.getString(DialogConst.DIALOG_CONFIRM_TEXT, "");
        String cancelText = data.getString(DialogConst.DIALOG_CANCEL_TEXT, "");
        switch (messageType) {
            case IMAGE_DIALOG:
            case IMAGE_DIALOG_WITHOUT_IMG:
            case IMAGE_DIALOG_WITH_TWO_CONTENT:
                ll_image_dialog.setVisibility(View.VISIBLE);
                id_tv_second_content.setVisibility(View.GONE);
                id_iv_image.setVisibility(View.GONE);
                if (messageType == IMAGE_DIALOG) {
                    id_iv_image.setVisibility(View.VISIBLE);
                    id_tv_second_content.setVisibility(View.VISIBLE);
                    btnConfirm.setText("去首页");
                } else if (messageType == IMAGE_DIALOG_WITHOUT_IMG) {
                    id_tv_second_content.setVisibility(View.VISIBLE);
                }

                if (!TextUtils.isEmpty(contentSecondText)) {
                    SpannableString spannableString = StringUtils.showPhoneLink(context.get(), contentSecondText);
                    id_tv_second_content.setText(spannableString);
                    id_tv_second_content.setMovementMethod(LinkMovementMethod.getInstance());
                }
                if (srcImage != -1) {
                    id_iv_image.setImageResource(srcImage);
                }
                id_tv_content.setText(contentText);
                if (!TextUtils.isEmpty(confirmText)){
                    btnConfirm.setText(confirmText);
                }
                if (!TextUtils.isEmpty(cancelText)){
                    btnClose.setText(cancelText);
                }
                break;
            case PHONE_DIALOG:
                rl_phone_dialog.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(contentText)) {
                    SpannableString spannableString = StringUtils.showPhoneLink(context.get(), contentText);
                    pd_tv_content.setText(spannableString);
                    pd_tv_content.setMovementMethod(LinkMovementMethod.getInstance());
                }
                btnConfirm.setText(context.get().getString(R.string.btn_call));
                break;
            case EDIT_DIALOG:
                ll_edit_dialog.setVisibility(View.VISIBLE);
                ed_tv_title.setText(titleText);
                ed_ed_content.setText(contentText);
                ed_ed_content.setSelection(contentText.length());
                ed_ed_content.setHint(contentHintText);
                if (!TextUtils.isEmpty(confirmText)){
                    btnConfirm.setText(confirmText);
                }
                break;
        }
    }


    protected void initListener() {
        initOptionListener();
    }

    protected void initOptionListener() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionListener != null) {
                    try {
                        optionListener.onConfirm(TankerTwoButtonDialog.this);
                    } catch (Exception e) {
                        Logger.e("confirm: " + e);
                    }
                }
                if (!notCancelDialog) {
                    TankerTwoButtonDialog.this.dismiss();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionListener != null) {
                    try {
                        optionListener.onCancel(TankerTwoButtonDialog.this);
                    } catch (Exception e) {
                        Logger.e("onCancel: " + e);
                    }
                }
                if (!notCancelDialog) {
                    TankerTwoButtonDialog.this.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (optionListener != null) {
//                    try {
//                        optionListener.onCancel(TankerDialog.this);
//                    } catch (Exception e) {
//                        Logger.e("cancel: " + e);
//                    }
//                }
//                if (!notCancelDialog) {
                TankerTwoButtonDialog.this.dismiss();
//                }
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void cancel() {
        super.cancel();
    }

    public void switchStyleAndShow(Bundle bundle, OptionListener listener) {
        this.optionListener = listener;
        this.data = bundle;

        this.show();
//        initDialog();
    }

    public void clearEditText() {
        ed_ed_content.setText("");
    }

    public String getPhone() {
        return null;
    }

    public interface OptionListener {
        void onConfirm(TankerTwoButtonDialog obDialog);

        void onCancel(TankerTwoButtonDialog obDialog);
    }

    public void setOptionListener(OptionListener optionListener) {
        this.optionListener = optionListener;
    }

    public OptionListener getOptionListener() {
        return optionListener;
    }
}
