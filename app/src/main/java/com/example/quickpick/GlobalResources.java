package com.example.quickpick;

import android.app.Application;

import com.example.quickpick.repositories.APIRequestToServer;
import com.example.quickpick.repositories.FirebaseRepository;

import javax.inject.Singleton;

@Singleton
public class GlobalResources extends Application
{
    private FirebaseRepository firebaseRepository;
    private APIRequestToServer apiRequestTOServer;

    @Override
    public void onCreate()
    {
        super.onCreate();
        firebaseRepository = FirebaseRepository.getInstance(getApplicationContext());
        apiRequestTOServer = new APIRequestToServer();
    }

    public FirebaseRepository getFirebaseRepository()
    {
        return firebaseRepository;
    }

    public APIRequestToServer getApiRequestTOServer()
    {
        return apiRequestTOServer;
    }

}
