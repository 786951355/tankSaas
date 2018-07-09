package com.tanker.basemodule.utils;

import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.widget.EditText;

public class ViewUtils {
    public static void moveToEndSelection(EditText view){
        view.setSelection(view.getText()!=null?view.getText().length():0);
    }

    /**
     * 只能输入英文和数字的键盘
     *
     * @param et
     */
    public static void acceptedChar(EditText et) {
        et.setKeyListener(new DigitsKeyListener() {
            @Override
            public int getInputType() {

                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }

            @Override
            protected char[] getAcceptedChars() {
                String dataID = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM1234567890";
                //char[] data = getStringData(R.string.login_only_can_input).toCharArray();
                char[] data = dataID.toCharArray();
                return data;
            }

        });
    }
}
