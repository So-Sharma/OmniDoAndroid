package com.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "FvROkGwOQyGH0A4A55FDqgoqS";
    private static final String TWITTER_SECRET = "3Av8ytzUU0H55b0uVDmZoiBRpCxp6kIAHxuckLrKaNl7Kye5TB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Splash screen doesn't require setting up view
        //setContentView(R.layout.activity_main);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());

        DigitsSession digitsSession = Digits.getActiveSession();

        if (digitsSession == null) {
            startActivity(new Intent(this, WelcomeActivity.class));
        } else {
            startActivity(new Intent(this, TodoMasterListActivity.class));
        }

        finish();
    }
}
