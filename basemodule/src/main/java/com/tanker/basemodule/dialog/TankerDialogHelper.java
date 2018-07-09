package com.tanker.basemodule.dialog;

import android.app.Activity;
import android.os.Bundle;


/**
 * Created by Ronny on 2017/12/16.
 */

public class TankerDialogHelper {

    public static TankerDialog showOBDialog(Activity context) {
        TankerDialog obDialog = new TankerDialog(context);
        obDialog.show();
        return obDialog;
    }

    public static TankerDialog showOBDialog(Activity context, Bundle bundle) {
        return showOBDialog(context, false, false, bundle);
    }

    public static TankerDialog showOBDialog(Activity context, boolean cancelable,
                                            boolean cancelOnTouchOutside, Bundle bundle) {
        return showOBDialog(context, cancelable, cancelOnTouchOutside, bundle, null);
    }

    public static TankerDialog showOBDialog(Activity context, boolean cancelable,
                                            boolean cancelOnTouchOutside, Bundle bundle, TankerDialog.OptionListener optionListener) {
        TankerDialog
                obDialog = new TankerDialog(context, cancelable, cancelOnTouchOutside, bundle);
        obDialog.setOptionListener(optionListener);
        obDialog.show();
        return obDialog;
    }
}
