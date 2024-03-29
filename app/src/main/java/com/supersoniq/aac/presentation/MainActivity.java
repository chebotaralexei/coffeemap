package com.supersoniq.aac.presentation;

import android.os.Bundle;

import com.supersoniq.aac.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new CoffeeShopsFragment(), null)
                .commit();
    }
}
