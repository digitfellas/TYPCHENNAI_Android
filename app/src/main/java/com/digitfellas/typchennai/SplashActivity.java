package com.digitfellas.typchennai;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.digitfellas.typchennai.dashboard.DashboardActivity;
import com.digitfellas.typchennai.fcm.MyFirebaseInstanceIDService;
import com.digitfellas.typchennai.login.LoginActivity;
import com.digitfellas.typchennai.preference.Preferences;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;


public class SplashActivity extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_splash);
        mToolbar.setVisibility(View.GONE);

       /*
        if (Preferences.INSTANCE.getFCMToken() == null) {
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        }*/


       startService(new Intent(this, MyFirebaseInstanceIDService.class));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(Preferences.INSTANCE.isUserLoggedIn()){

                    Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 2000);
    }
}
