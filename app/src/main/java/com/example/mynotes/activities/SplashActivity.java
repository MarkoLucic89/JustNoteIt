package com.example.mynotes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mynotes.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        getSupportActionBar().hide();
        goToMainActivity();
    }

    private void goToMainActivity() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, NoteListActivity.class));
            finish();
        }, 200);
    }
}