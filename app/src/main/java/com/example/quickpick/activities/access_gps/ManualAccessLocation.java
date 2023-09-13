package com.example.quickpick.activities.access_gps;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickpick.databinding.ActivityManualAccessLocationBinding;

public class ManualAccessLocation extends AppCompatActivity {
    private ActivityManualAccessLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityManualAccessLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }



}

