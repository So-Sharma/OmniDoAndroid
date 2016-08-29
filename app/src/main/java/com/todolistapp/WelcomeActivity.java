package com.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText("Welcome to OmniDo. Sign-up using your phone no and whatever!");

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);

        digitsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        digitsButton.setText("Get started!");

        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {

                // TODO: associate the session userID with your user model
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();

                startActivity(new Intent(WelcomeActivity.this, TodoMasterListActivity.class));
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });
    }
}
