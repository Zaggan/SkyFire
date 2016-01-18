package com.itexico.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.itexico.a2eapp.R;

/**
 * Created by DarkGeat on 1/14/2016.
 */
public class About extends Dialog {
    public About(Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(true);
        setContentView(R.layout.about_dialog);
    }
}
