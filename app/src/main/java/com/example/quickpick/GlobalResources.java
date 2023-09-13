package com.example.quickpick;

import android.app.Application;

import com.example.quickpick.repositories.FirebaseRepository;

import javax.inject.Singleton;

@Singleton
public class GlobalResources extends Application
{
    private FirebaseRepository firebaseRepository;


    @Override
    public void onCreate()
    {
        super.onCreate();
        firebaseRepository = FirebaseRepository.getInstance(getApplicationContext());
    }

    public FirebaseRepository getFirebaseRepository()
    {
        return firebaseRepository;
    }

}
