package com.itexico.a2eapp;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;

import com.itexico.Listeners.InfoListener;
import com.itexico.Utils.VideoSurface;
import com.itexico.dialogs.ProgressDialog;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    // Declare variables
    private ProgressDialog pDialog;
    private VideoSurface videoview;
    private TextView frameRate,frameLost,frameDisplayed;
    private Button showInfo;
    private GridLayout gridLayout;

    // Insert your Video URL
    String VideoURL = "rtsp://192.168.2.238:8554/main";
    DecimalFormat decimalFormat = new DecimalFormat("###,###,##0");
    //String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    //String VideoURL = "https://www.youtube.com/watch?v=dgmhgRD2onM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (!LibsChecker.checkVitamioLibs(this))
            return;*/
        // Get the layout from video_main.xml
        setContentView(R.layout.activity_main);
        // Find your VideoView in your video_main.xml layout
        videoview = (VideoSurface) findViewById(R.id.myVideo);
        // Find the labels for Video Info.
        frameRate = (TextView) findViewById(R.id.frate);
        frameLost = (TextView) findViewById(R.id.fLost);
        frameDisplayed = (TextView) findViewById(R.id.fDisplayed);
        // Find the button for Info
        showInfo = (Button) findViewById(R.id.buttonShow);
        // Find the Layout with Video Info
        gridLayout = (GridLayout) findViewById(R.id.grid);
        // Read URL from intent
        String urlVideo = getIntent().getStringExtra("url");
            VideoURL = urlVideo;
        //}
        // Execute StreamVideo AsyncTask

        // Create a progressbar
        pDialog = new ProgressDialog(MainActivity.this);
        // Set progressbar title
        pDialog.setTittle("Video Streaming");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (pDialog.isCancelled()) {
                    pDialog.setIsCancelled(false);
                    finish();
                }
            }
        });
        // Show progressbar
        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(MainActivity.this);
            mediacontroller.setAnchorView(videoview);
            //Set the path to VideoView controller
            videoview.setVideoPath(VideoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setInfoListener(new InfoListener() {
                @Override
                public void updatingInfo(final long FRate, final long FDisplayed, final long FLost) {
                    frameRate.post(new Runnable() {
                        @Override
                        public void run() {
                            frameRate.setText(decimalFormat.format(FRate));
                        }
                    });
                    frameLost.post(new Runnable() {
                        @Override
                        public void run() {
                            frameLost.setText(decimalFormat.format(FLost));
                        }
                    });
                    frameDisplayed.post(new Runnable() {
                        @Override
                        public void run() {
                            frameDisplayed.setText(decimalFormat.format(FDisplayed));
                        }
                    });
                }
            });

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });
    }

    @Override
    protected void onResume() {
        videoview.setShouldStop(false);
        videoview.resume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        videoview.setShouldStop(true);
        videoview.stopPlayback();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoview.pause();
    }

    @Override
    public void onBackPressed() {
        videoview.setShouldStop(true);
        videoview.stopPlayback();
        super.onBackPressed();
    }

    public void InfoTransitionClick(View view){
        showInfo.setVisibility(showInfo.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        gridLayout.setVisibility(showInfo.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
}
