package com.tanker.basemodule.event;

import android.text.method.ReplacementTransformationMethod;

/**
 * author zhanglei
 * date 2018/6/29
 * description 小写字符转化成大写 EditText.setTransformationMethod(new CapTransformationMethod());
**/
public class CapTransformationMethod extends ReplacementTransformationMethod {
    @Override
    protected char[] getOriginal() {
        return new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    }

    @Override
    protected char[] getReplacement() {
        return new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    }
}
