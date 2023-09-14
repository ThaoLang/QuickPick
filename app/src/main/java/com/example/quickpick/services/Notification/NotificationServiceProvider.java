package com.example.quickpick.services.Notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.quickpick.GlobalResources;
import com.example.quickpick.api_structure.GetCostNotification;
import com.example.quickpick.api_structure.GetCostResponse;
import com.example.quickpick.api_structure.GetDirectionNotification;
import com.example.quickpick.api_structure.UpdateFCMTokenRequest;
import com.example.quickpick.constants.NotificationBroadcastFilters;
import com.example.quickpick.constants.NotificationTypes;
import com.example.quickpick.my_intefaces.GetRunnable_StringParam_Function;
import com.example.quickpick.my_intefaces.NotificationConverterStrategy;
import com.example.quickpick.repositories.APIRequestToServer;
import com.example.quickpick.services.Notification.constant.RemoteMessageAttributes;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class NotificationServiceProvider
{
    private Context serviceContext;
    private Context appContext;

    private HashMap<String, GetRunnable_StringParam_Function> serviceProvider;
    private ThreadPoolExecutor executor;

    public NotificationServiceProvider(Context serviceContext)
    {
        serviceProvider = new HashMap<String, GetRunnable_StringParam_Function>();
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.serviceContext = serviceContext;
        this.appContext = serviceContext.getApplicationContext();
        init();
    }

    public NotificationServiceProvider(Context serviceContext, Context appContext)
    {
        serviceProvider = new HashMap<String, GetRunnable_StringParam_Function>();
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.serviceContext = serviceContext;
        this.appContext = appContext;
        init();
    }

    public void handleRemoteMessage(RemoteMessage message)
    {
        if(message.getData() == null)
        {
            return;
        }
        Map<String, String> property = message.getData();
        String notificationType = property.get(RemoteMessageAttributes.NOTIFICATION_TYPE);
        String payload = property.get(RemoteMessageAttributes.PAYLOAD);
        GetRunnable_StringParam_Function task = serviceProvider.get(notificationType);
        try
        {
            executor.submit(task.getRunnable(payload));
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        System.out.println("after submit task");
    }

    private void init()
    {
        serviceProvider.put(NotificationTypes.GET_COST_NOTIFICATION_REQUEST, handleGetCostResponse);

    }

    public void sendUpdateNotificationToken(String fcmToken, Context appContext)
    {
        if(fcmToken == null)
        {
            return;
        }
        if(fcmToken.isEmpty())
        {
            return;
        }

        Runnable task = getUpdateNotificationTask(fcmToken, appContext);
        try
        {
            executor.submit(task);
        }
        catch (Exception ex)
        {
            Log.e("sendUpdateNotification", ex.getMessage());
        }
    }

    private Runnable getUpdateNotificationTask(String fcmToken, Context context)
    {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("inside background task");
                GlobalResources resources = (GlobalResources) context;
                System.out.println("after get resource");

                String userId = resources.getFirebaseRepository().getCurrentUser().getUid();
                System.out.println("userId: " + userId);
                APIRequestToServer repository = resources.getApiRequestTOServer();
                System.out.println("get Repository");

               UpdateFCMTokenRequest request = new UpdateFCMTokenRequest();
                request.setUserId(userId);
                request.setFcm_token(fcmToken);
                request.setRole("driver");
                request.setIdToken("testing");
                request.setSystemKey("LTTLHKNNMQLHP");
                repository.postUpdateFCMToken(request);
            }
        };
        return task;
    }


//    private GetRunnable_StringParam_Function HandleBroadcastPickupRequestNotification = (payload) ->
//    {
//        Runnable task = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("inside background task");
//                try
//                {
//                    String notificationType = NotificationTypes.BROADCAST_PICKUP_REQUEST;
//                    JSONObject object = new JSONObject(payload);
//
//                    System.out.println("converted object: " + object);
//
//                    BroadcastPickupRequestNotification notification = new BroadcastPickupRequestNotification();
//
//                    String userId = String.valueOf(object.get(BroadcastPickupRequestNotificationAttributes.USER_ID));
//                    String parseNotificationType = String.valueOf(object.get(BroadcastPickupRequestNotificationAttributes.NOTIFICATION_TYPES));
//                    String startLongitude = String.valueOf(object.get(BroadcastPickupRequestNotificationAttributes.START_LONGITUDE));
//                    String startLatitude = String.valueOf(object.get(BroadcastPickupRequestNotificationAttributes.START_LATITUDE));
//                    String endLongitude = object.getString(BroadcastPickupRequestNotificationAttributes.END_LONGITUDE);
//                    String endLatitude = object.getString(BroadcastPickupRequestNotificationAttributes.END_LATITUDE);
//                    String startAddress = object.getString(BroadcastPickupRequestNotificationAttributes.START_ADDRESS);
//                    String endAddress = object.getString(BroadcastPickupRequestNotificationAttributes.END_ADDRESS);
//                    String customerName = object.getString(BroadcastPickupRequestNotificationAttributes.CUSTOMER_NAME);
//                    String phone = object.getString(BroadcastPickupRequestNotificationAttributes.PHONE);
//                    String vehicle = object.getString(BroadcastPickupRequestNotificationAttributes.VEHICLE);
//                    Double duration = object.getDouble(BroadcastPickupRequestNotificationAttributes.DURATION);
//                    Double distance = object.getDouble(BroadcastPickupRequestNotificationAttributes.DISTANCE);
//                    Double cost = object.getDouble(BroadcastPickupRequestNotificationAttributes.COST);
//
//                    notification.setUserId(userId);
//                    notification.setNotificationType(parseNotificationType);
//                    notification.setStartLongitude(startLongitude);
//                    notification.setStartLatitude(startLatitude);
//                    notification.setEndLongitude(endLongitude);
//                    notification.setEndLatitude(endLatitude);
//                    notification.setStartAddress(startAddress);
//                    notification.setEndAddress(endAddress);
//                    notification.setCustomerName(customerName);
//                    notification.setPhone(phone);
//                    notification.setVehicle(vehicle);
//                    notification.setDuration(duration);
//                    notification.setDistance(distance);
//                    notification.setCost(cost);
//
//                    //update resource
//                    GlobalResources.getIncomingPickupRequests().add(0, notification);
//
//
//                    System.out.println(GlobalResources.getIncomingPickupRequests().size());
//
//                    byte[] payload = converterStrategy.fromObjectToBytes(notification);
//                    System.out.println("after convert to bytes");
//
//                    Intent intent = new Intent(NotificationBroadcastFilters.INCOMING_PICKUP_NOTIFICATION);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(NotiDataAttributes.NOTIFICATION_TYPE, notificationType);
//                    bundle.putByteArray(NotiDataAttributes.PAYLOAD, payload);
//                    intent.putExtra(NotiDataAttributes.INTENT_BUNDLE, bundle);
//
//                    appContext.sendBroadcast(intent);
//                    System.out.println("after broadcast");
//                }
//                catch (Exception ex)
//                {
//                    System.out.println(ex);
//                }
//            }
//        };
//
//        return task;
//    };

    private GetRunnable_StringParam_Function handleGetCostResponse = new GetRunnable_StringParam_Function() {
        @Override
        public Runnable getRunnable(String payload) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    System.out.println("inside background task");
                    try
                    {
                        String notificationType = NotificationTypes.GET_COST_NOTIFICATION_REQUEST;
                        JSONObject object = new JSONObject(payload);
                        NotificationConverterStrategy converterStrategy = new GetCostNotificationConverterStrategy();

                        System.out.println("converted object: " + object);

                        GetCostNotification notification = new GetCostNotification();

                        String notiType = String.valueOf(object.get("notificationType"));
                        Object listOfCostResponseObject= object.get("listOfCostResponse");
                        List<GetCostResponse> listOfCostResponse = new ArrayList<>();
                        List<Object> list= (List<Object>) listOfCostResponseObject;


                        for (int i=0;i<list.size();i++) {
                            GetCostResponse item=(GetCostResponse) list.get(i);
                            listOfCostResponse.add(item);
                        }

                        String idUser = String.valueOf(object.get("idUser"));

                        notification.setListOfCostResponse(listOfCostResponse);
                        notification.setNotificationType(notiType);
                        notification.setIdUser(idUser);


                        byte[] payload = converterStrategy.fromObjectToBytes(notification);
                        System.out.println("after convert to bytes");

                        Intent intent = new Intent(NotificationBroadcastFilters.NOTIFICATION_GET_COST);
                        Bundle bundle = new Bundle();
                        bundle.putString(NotiDataAttributes.NOTIFICATION_TYPE, notificationType);
                        bundle.putByteArray(NotiDataAttributes.PAYLOAD, payload);
                        intent.putExtra(NotiDataAttributes.INTENT_BUNDLE, bundle);

                        appContext.sendBroadcast(intent);
                        System.out.println("after broadcast");
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex);
                    }
                }
            };

            return task;

        }
    };


//    private GetRunnable_StringParam_Function handleGetDirectionResponse = new GetRunnable_StringParam_Function() {
//        @Override
//        public Runnable getRunnable(String payload) {
//            Runnable task = new Runnable() {
//                @Override
//                public void run() {
//                    try
//                    {
//                        String notificationType = NotificationTypes.GET_DIRECTION_NOTIFICATION;
//                        JSONObject object = new JSONObject(payload);
//                        NotificationConverterStrategy converterStrategy = new GetDirectionNotificationConverterStrategy();
//
//                        System.out.println("converted object: " + object);
//
//                        GetDirectionNotification notification = new GetDirectionNotification();
//
//                        String notiType = String.valueOf(object.get("notificationType"));
//                        Object listOfCostResponseObject= object.get("listOfCostResponse");
//                        List<GetCostResponse> listOfCostResponse = new ArrayList<>();
//                        List<Object> list= (List<Object>) listOfCostResponseObject;
//
//
//                        for (int i=0;i<list.size();i++) {
//                            GetCostResponse item=(GetCostResponse) list.get(i);
//                            listOfCostResponse.add(item);
//                        }
//
//                        String idUser = String.valueOf(object.get("idUser"));
//
//                        notification.setListOfCostResponse(listOfCostResponse);
//                        notification.setNotificationType(notiType);
//                        notification.setIdUser(idUser);
//
//
//                        byte[] payload = converterStrategy.fromObjectToBytes(notification);
//                        System.out.println("after convert to bytes");
//
//                        Intent intent = new Intent(NotificationBroadcastFilters.NOTIFICATION_GET_COST);
//                        Bundle bundle = new Bundle();
//                        bundle.putString(NotiDataAttributes.NOTIFICATION_TYPE, notificationType);
//                        bundle.putByteArray(NotiDataAttributes.PAYLOAD, payload);
//                        intent.putExtra(NotiDataAttributes.INTENT_BUNDLE, bundle);
//
//                        appContext.sendBroadcast(intent);
//                        System.out.println("after broadcast");
//                    }
//                    catch (Exception ex)
//                    {
//                        System.out.println(ex);
//                    }
//                }
//            };
//
//            return task;
//
//        }
//    };



}