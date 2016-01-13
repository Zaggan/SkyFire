package com.itexico.a2eapp;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;

    // Insert your Video URL
    String VideoURL = "rtsp://192.168.2.238:8554/main";
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
        videoview = (VideoView) findViewById(R.id.myVideo);
        // Read URL from intent
        String urlVideo = getIntent().getStringExtra("url");
        //if (urlVideo.endsWith("mp4") || urlVideo.endsWith("3gp") || urlVideo.endsWith("webm")){
            VideoURL = urlVideo;
        //}
        // Execute StreamVideo AsyncTask

        // Create a progressbar
        pDialog = new ProgressDialog(MainActivity.this);
        // Set progressbar title
        pDialog.setTitle("Video Streaming");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    MainActivity.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            videoview.setVideoPath(VideoURL);
            videoview.setMediaController(mediacontroller);

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
}
