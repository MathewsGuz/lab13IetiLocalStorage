package com.eci.cosw.taskplanner.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eci.cosw.taskplanner.R;
import com.eci.cosw.taskplanner.Util.SharedPreference;

public class LaunchActivity extends AppCompatActivity {

    private SharedPreference sharedPreference;
    private String TOKEN_KEY;
    private String file;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        file = getString(R.string.preference_file_key);
        TOKEN_KEY = getString(R.string.token_key);

        sharedPreference = new SharedPreference(LaunchActivity.this, file);

        if (sharedPreference.contains(TOKEN_KEY)) {
            Intent mainIntent = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(mainIntent);
        } else {
            Intent loginIntent = new Intent(LaunchActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }
}
