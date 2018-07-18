package com.tanker.loginmodule.inputFilter;

import android.text.InputFilter;
import android.text.Spanned;

import com.tanker.resmodule.constants.RegexConstants;

import java.util.regex.Pattern;

public class ChineseInputFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        boolean isChinese = Pattern.compile(RegexConstants.REGEX_CHINESE_ONLY)
                .matcher(source.toString()).find();
        if (isChinese) {
            return "";
        }
        return null;
    }
}
