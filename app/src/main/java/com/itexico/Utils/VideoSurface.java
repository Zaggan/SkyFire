package com.itexico.Utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

import com.itexico.Listeners.InfoListener;

/**
 * Created by DarkGeat on 1/14/2016.
 */
public class VideoSurface extends VideoView {

    private long lastTime = 0;
    private long framesDisplayed = 0, maxCycles = 5000;
    private long framesCount = 0, framesPerSecond = 30, frameRate = 0, framesLosed = 0, frameLast = 0;
    public static volatile boolean shouldStop = false;
    private boolean firstTime = true;
    private InfoListener listener;
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

    private Thread calculation;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if( calculation == null ){
            calculation = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!shouldStop) {
                        long currentTime = System.currentTimeMillis();
                        if (firstTime) {
                            lastTime = currentTime;
                            firstTime = false;
                        }
                        framesCount++;
                        if (currentTime - lastTime > 1000) {
                            firstTime = true;
                            if (framesCount > maxCycles){
                                maxCycles = framesCount;
                            }
                            frameRate = framesCount * framesPerSecond / maxCycles;
                            framesDisplayed += frameRate;
                            if (frameRate < framesPerSecond && frameRate < frameLast){
                                framesLosed += (framesPerSecond - frameRate);
                            }
                            framesCount = 0;
                            frameLast = frameRate;
                            listener.updatingInfo(frameRate,framesDisplayed,framesLosed);
                        }
                    }
                }
            });
            calculation.start();
        }
    }

    public void setShouldStop(boolean value){
        shouldStop = value;
    }

    public void setInfoListener(InfoListener infoListener){
        listener = infoListener;
    }
}
