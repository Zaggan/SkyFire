package com.itexico.Utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

/**
 * Created by DarkGeat on 1/14/2016.
 */
public class VideoSurface extends VideoView {

    private long lastTime = 0;
    private long framesDisplayed = 0;
    private int framesCount = 0, framesPerSecond = 0, frameRate = 0;
    private boolean firstTime = true, shouldStop = false;
    private static final String TAG = "VideoSurface";

    public VideoSurface(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public VideoSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public VideoSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VideoSurface(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);
    }

    public static volatile boolean flag = true;
    private Thread calculation;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*if( calculation == null ){
            calculation = new Thread(new Runnable() {
                @Override
                public void run() {
                    while ( flag ) {*/
                        long currentTime = System.currentTimeMillis();
                        if (firstTime) {
                            lastTime = currentTime;
                            firstTime = false;
                        }
                        framesCount++;
                        if (currentTime - lastTime > 1000) {
                            firstTime = true;
                            frameRate = framesCount / 60;
                            framesDisplayed += frameRate;
                            framesCount = 0;
                            Log.w(TAG, "Frames Rate = " + frameRate);
                            Log.w(TAG, "Frames Displayed = " + framesDisplayed);
                        }
                    //}
               // }
            //});
            //calculation.start();
        //}
    }

    public int getFramesPerSecond(){
        return framesPerSecond;
    }

    public void setShouldStop(boolean value){
        shouldStop = value;
    }
}
