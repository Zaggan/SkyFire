package com.itexico.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.itexico.a2eapp.R;

/**
 * Created by DarkGeat on 1/13/2016.
 */
public class ProgressDialog extends Dialog {

    private TextView tittle, message;
    private boolean isCancelled = false;

    public ProgressDialog(Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(false);
        setContentView(R.layout.progress_dialog);

        tittle = (TextView)findViewById(R.id.tittle);
        message = (TextView)findViewById(R.id.message);
    }

    public void setTittle(String tittleText){
        tittle.setText(tittleText);
    }

    public void setMessage(String messageText){
        message.setText(messageText);
    }

    @Override
    public void onBackPressed() {
        message.setText("Cancelling Buffering...");
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1500);
                    setIsCancelled(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    dismiss();
                }
            }
        };
        thread.start();
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
