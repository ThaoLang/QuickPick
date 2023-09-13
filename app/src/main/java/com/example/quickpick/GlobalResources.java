package com.example.quickpick;

import android.app.Application;

import com.example.quickpick.repositories.FirebaseRepository;

import javax.inject.Singleton;

@Singleton
public class GlobalResources extends Application
{
    private FirebaseRepository firebaseRepository;
    private APIRequestTOServer apiRequestTOServer;

    @Override
    public void onCreate()
    {
        super.onCreate();
        firebaseRepository = FirebaseRepository.getInstance(getApplicationContext());
        apiRequestTOServer = new APIRequestTOServer();
    }

    public FirebaseRepository getFirebaseRepository()
    {
        return firebaseRepository;
    }

    public APIRequestTOServer getApiRequestTOServer()
    {
        return apiRequestTOServer;
    }

}
