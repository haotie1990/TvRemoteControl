package com.tv.remote.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.tv.remote.R;

/**
 * Created by 凯阳 on 2015/10/9.
 */
public class KeyBoardDialog extends Dialog{

    public KeyBoardDialog(Context context) {
        super(context, R.style.MyDialog);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        getWindow().setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
        setContentView(R.layout.layout_softkeyboard);
    }
}
