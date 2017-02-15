package com.elbejaj.sharetm;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

/**
 * Created by Bejaj on 20/11/2016.
 */

public class Splash extends Activity {
    MediaPlayer splashSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        splashSound = MediaPlayer.create(getApplicationContext(), R.raw.splash_sound);
        //splashSound.start();

        Thread chrono = new Thread(){
            public void run(){
                try{
                    sleep(3);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(".MAINACTIVITY");
                    startActivity(i);
                }
            }
        };

        chrono.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        splashSound.release();
        finish();
    }
}
