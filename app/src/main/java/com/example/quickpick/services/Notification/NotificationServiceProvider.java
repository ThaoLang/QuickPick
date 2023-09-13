import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class NotificationServiceProvider
{
    private Context serviceContext;
    private Context appContext;
    private NotificationConverterStrategyProvider converterProvider;

    private HashMap<String, GetRunnable_StringParam_Function> serviceProvider;
    private ThreadPoolExecutor executor;

    public NotificationServiceProvider(Context serviceContext)
    {
        serviceProvider = new HashMap<String, GetRunnable_StringParam_Function>();
        converterProvider = new NotificationConverterStrategyProvider();
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.serviceContext = serviceContext;
        this.appContext = serviceContext.getApplicationContext();
        init();
    }

    public NotificationServiceProvider(Context serviceContext, Context appContext)
    {
        serviceProvider = new HashMap<String, GetRunnable_StringParam_Function>();
        converterProvider = new NotificationConverterStrategyProvider();
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.serviceContext = serviceContext;
        this.appContext = appContext;
        init();
    }

    private void init()
    {
        serviceProvider.put(NotificationTypes., );

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
                APIRequestTOServer repository = resources.getAPIRequestTOServer();
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

    GetRunnable_StringParam_Function  = (payload) ->
    {

    }

}