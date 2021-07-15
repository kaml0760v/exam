package com.example.mybusinessbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.mybusinessbook.HomeScreen;
import com.example.mybusinessbook.MainActivity;
import com.example.mybusinessbook.R;
import com.example.mybusinessbook.helper.SessionManager;

public class splash extends AppCompatActivity {
    ImageView img;
    Animation animation;
    SessionManager session;
    MediaPlayer music;

    @Override
    protected void onPause() {
        super.onPause();
        music.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getSupportActionBar().hide();
        session = new SessionManager(this);

        if(session.isLoggedIn()){
           startActivity(new Intent(this, HomeScreen.class));
           finish();
        }else{
            Thread timer = new Thread(){
                @Override
                public void run() {
                    try {

                        img = findViewById(R.id.SplashScreenImage);
                        //Loading the aninmation
                        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_anim);
                        //Set animation to image
                        img.startAnimation(animation);
                        //cretaing a MusicPlayer object
                        music=MediaPlayer.create(getApplicationContext(),R.raw.ring);
                        //started music
                        music.start();
                        //thread will run for 5 seconds
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally{
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                   // startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            };
            timer.start();
            //new Handler().postDelayed(() -> startActivity(new Intent(getApplicationContext(), MainActivity.class)),3000);
        }


    }
}