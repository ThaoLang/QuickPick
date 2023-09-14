package com.example.quickpick.services.Notification;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.quickpick.my_intefaces.GetRunnable_StringParam_Function;
import com.example.quickpick.services.Notification.constant.RemoteMessageAttributes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class NotificationService extends FirebaseMessagingService
{
    private Context context;
    private NotificationServiceProvider serviceProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        serviceProvider = new NotificationServiceProvider(NotificationService.this, context);

        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String fcm_token) {
                        System.out.println("FCM token:" + fcm_token);
                        serviceProvider.sendUpdateNotificationToken(fcm_token, context);
                    }
                });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message)
    {
        System.out.println("Receive message " + message.getData());
        serviceProvider.handleRemoteMessage(message);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        //send to server
        System.out.println("FCM token: " + token);
        serviceProvider.sendUpdateNotificationToken(token, context);
        //TODO:send to server
    }




}