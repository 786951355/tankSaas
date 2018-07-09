package com.tanker.basemodule.constants;

/**
 * Created by Ronny on 2017/12/19.
 */

public class DialogConst {

    /**
     * 数量类为int bundle.putInt(key, int)
     * 文字类为String bundle.putString(key, String)
     * 显示类为boolean bundle,putBoolean(key, boolean( Key包含HIDE的为默认显示的View,包含SHOW的字段为默认隐藏的VIEW
     * <p>
     * 功能类无标注的默认putString(key, String)
     */

    //#----------------------------------------Dialog默认常量----------------------------------------#

    //公告弹窗默认margin dp
    public final static int DIALOG_DEFAULT_MARGIN = 20;

    //公告弹窗默认按钮高度 dp
    public final static int DIALOG_DEFAULT_BUTTON_HEIGHT = 50;

    //公告弹窗默认高度 dp
    public final static int DIALOG_DEFAULT_HEIGHT = 190;

    //公告弹窗默认宽度 dp
    public final static int DIALOG_DEFAULT_WIDTH = 282;

    public final static int DIALOG_SECOND_HEIGHT = 296;

    //强制公告倒计时默认时间 s
    public final static int DIALOG_PROCLAMATION_CLOSE_TIME = 5;

    //公告弹窗标题默认高度 dp
    public final static int DIALOG_PROCLAMATION_TITLE_HEIGHT = 48;

    //公告弹窗关闭按钮默认高度 dp
    public final static int DIALOG_PROCLAMATION_CLOSE_BTN_HEIGHT = 31;

    //公告弹窗关闭按钮默认宽度 dp
    public final static int DIALOG_PROCLAMATION_CLOSE_BTN_WIDTH = 134;

    //公告弹窗关闭按钮默认高度 dp
    public final static int DIALOG_PROCLAMATION_CLOSE_BTN_BOTTOM_MARGIN = 19;

    //#----------------------------------------数量类----------------------------------------#

    //dialog高度 单位dp int 默认wrap_content
    public final static String DIALOG_HEIGHT = "dialog_height";

    //dialog宽度 单位dp int 默认wrap_content
    public final static String DIALOG_WIDTH = "dialog_width";

    //#----------------------------------------文字类----------------------------------------#

    //公告标题文字 默认为空
    public final static String DIALOG_TITLE_TEXT = "dialog_title";

    //公告内容文字 默认为空
    public final static String DIALOG_CONTENT_TEXT = "dialog_content";

    public static final String DIALOG_SECOND_CONTENT_TEXT = "dialog_second_content";


    //公告确认按钮文字 默认确定
    public final static String DIALOG_CONFIRM_TEXT = "dialog_confirm";

    //公告取消按钮文字 默认取消
    public final static String DIALOG_CANCEL_TEXT = "dialog_cancel";

    //#----------------------------------------显示类----------------------------------------#

    //是否隐藏title boolean true隐藏 false显示 默认显示
    public final static String DIALOG_HIDE_TITLE = "dialog_hide_title";

    //是否隐藏content boolean true隐藏 false显示 默认显示
    public final static String DIALOG_HIDE_CONTENT = "dialog_hide_content";

    //是否隐藏底部按钮组 boolean true隐藏 false显示 默认显示
    public final static String DIALOG_HIDE_BTN_GROUP = "dialog_hide_btn_group";

    //是否隐藏确认按钮 boolean true隐藏 false显示 默认显示
    public final static String DIALOG_HIDE_CONFIRM = "dialog_hide_confirm";

    //是否显示取消按钮 boolean true显示 false隐藏 默认隐藏
    public final static String DIALOG_SHOW_CANCEL = "dialog_show_cancel";

    //底部取消按钮 TODO 和关闭按钮合并
    public final static String DIALOG_SHOW_CLOSE = "dialog_show_close";

    //是否显示"下次不再显示"选项 boolean 默认隐藏
    public final static String DIALOG_SHOW_NEVER_SHOW_AGAIN = "dialog_show_never_show_again";

    //#----------------------------------------功能类----------------------------------------#

    //公告ListView的数据源 StringArrayList bundle.putStringArrayList(DIALOG_LIST_DATA, list<String>)
    public final static String DIALOG_LIST_DATA = "dialog_list_data";

    //配置dialog取消按钮颜色 string 十六进制RGB颜色代码 例：#FAFAFA
    public final static String DIALOG_CANCEL_TEXT_COLOR = "dialog_cancel_text_color";

    //是否加粗标题字体 boolean true加粗 false隐藏 默认不加粗
    public final static String DIALOG_SET_TITLE_BOLD_FONT = "dialog_set_title_bold_font";

    //点击按钮后是否不关闭dialog boolean true不关闭 false关闭 默认关闭 bundle.putBoolean(DIALOG_NOT_CANCEL_DIALOG_AFTER_CLICK, int)
    public final static String DIALOG_NOT_CANCEL_DIALOG_AFTER_CLICK = "dialog_not_cancel_after_click";

    //消息类型-用于公告活动等
    public final static String DIALOG_MESSAGE_TYPE = "messageType";

    //通过SharedPreference存储messageId的key-用于公告活动等
    public final static String SP_MESSAGE = "saved_dialog_message";

    public final static String DIALOG_CLOSE_ACTIVITY = "dialog_close_activity";
    public static final java.lang.String DIALOG_IMAGE_RES_ID = "image_res_id";
    public static final java.lang.String DIALOG_CONTENT_HINT = "content_hint";
}
