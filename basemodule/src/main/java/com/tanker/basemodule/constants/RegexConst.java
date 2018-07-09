package com.tanker.basemodule.constants;

public class RegexConst {
    /**
     * 用户名的匹配规则
     */
    public static String USER_NAME_REGEX = "[A-Za-z\\-\u4e00-\u9fa5|.|,]+";

    /**
     * 默认的筛选条件(正则:只能输入中文)
     */
    public static String CHINESE_ONLY_REGEX = "[^\u4E00-\u9FA5]";

}
