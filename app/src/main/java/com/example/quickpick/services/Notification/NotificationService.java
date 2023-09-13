

public class NotificationService extends FirebaseMessagingService
{
    private Context context;
    private NotificationServiceProvider serviceProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        serviceProvider = new NotificationServiceProvider(NotificationService.this, context);

        NotificationConverterStrategyProvider converterStrategyProvider = new NotificationConverterStrategyProvider();

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

    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(@NonNull String token) {
        //send to server
        System.out.println("FCM token: " + token);
        serviceProvider.sendUpdateNotificationToken(token, context);
        //TODO:send to server
    }
}