package com.example.iitbhustudentsapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.iitbhustudentsapp.R;
import com.example.iitbhustudentsapp.utils.Constants;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkRegistrationAndProceed();
    }

    /**
     * Checks if user already registered and proceeds to suitable activity.
     */
    private void checkRegistrationAndProceed() {
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(Constants.SPLASH_SCREEN_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
//                    boolean isRegistered = PreferenceUtils.getBooleanPreference(
//                            SplashScreen.this, PreferenceUtils.PREF_USER_LOGGED_IN);
                    boolean isRegistered = true;
                    Intent intent;
                    if (isRegistered)
                        intent = new Intent(SplashScreen.this, MainActivity.class);
                    else
                        intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
