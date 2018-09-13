package com.codeframetech.jobchaiyoo;

/**
 * Created by Digition on 12/24/2017.
 */


import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.facebook.*;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class SplashActivity extends AppCompatActivity {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }


    /**
     * Called when the activity is first created.
     */
    AccessTokenTracker  accessTokenTracker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };
        updateWithToken(AccessToken.getCurrentAccessToken());


        setContentView(R.layout.activity_splash);
        CircularProgressBar circularProgressBar = findViewById(R.id.circle);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.progressBarColor));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.progress_background));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBarWidth));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.backgroundProgressBarWidth));
        int animationDuration = 2000; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(100, animationDuration);
        // Default duration = 1500ms


    }

    private void updateWithToken(AccessToken currentAccessToken) {

        if (currentAccessToken != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, TeachingJob.class);
                    startActivity(i);
                    finish();
                }
            }, 2000);
        } else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this,FacebookActivity.class);
                    startActivity(i);

                    finish();
                }
            }, 2000);
        }
    }
}