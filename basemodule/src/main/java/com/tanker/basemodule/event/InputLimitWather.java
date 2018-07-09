package com.tanker.basemodule.event;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.tanker.basemodule.constants.RegexConst;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputLimitWather implements TextWatcher {
    /**
     * et
     */
    private EditText et = null;
    /**
     * 筛选条件
     */
    private String regex;




    /**
     * 构造方法
     *
     * @param et
     */
    public InputLimitWather(EditText et) {
        this.et = et;
        this.regex = RegexConst.CHINESE_ONLY_REGEX;
    }

    /**
     * 构造方法
     *
     * @param et    et
     * @param regex 筛选条件
     */
    public InputLimitWather(EditText et, String regex) {
        this.et = et;
        this.regex = regex;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String str = editable.toString();
        String inputStr = clearLimitStr(regex, str);
        et.removeTextChangedListener(this);
        // et.setText方法可能会引起键盘变化,所以用editable.replace来显示内容
        editable.replace(0, editable.length(), inputStr.trim());
        et.addTextChangedListener(this);

    }

    /**
     * 清除不符合条件的内容
     *
     * @param regex
     * @return
     */
    private String clearLimitStr(String regex, String str) {

        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(str);
        if (!m.matches()){
            return "";
        }
        return str;
    }
}
