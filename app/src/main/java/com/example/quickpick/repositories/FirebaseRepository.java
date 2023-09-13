package com.example.quickpick.repositories;


import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quickpick.MainActivity;
import com.example.quickpick.components.Booking;
import com.example.quickpick.components.MyCustomer;
import com.example.quickpick.constants.FirebaseCollection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Singleton;

@Singleton
public class FirebaseRepository {

    private static volatile FirebaseRepository firebaseRepository;
    private static FirebaseAuth firebaseAuth;

    private Context appContext;
    private static Handler handler;
    private static volatile FirebaseFirestore fireStore;

    private static MyCustomer mainUserInfo;
    private static boolean isUserInfoReady = false;

    private static String NGROK_IGNORE_WARNING_KEY="ngrok-skip-browser-warning";
    public static String NGROK_IGNORE_WARNING_VALUE="any value";

    private FirebaseRepository(Context providedContext)
    {
        try
        {
            appContext = providedContext;
            FirebaseApp.initializeApp(appContext);
            Log.e("FirebaseApp: ", FirebaseApp.getInstance().getName());
            firebaseAuth = FirebaseAuth.getInstance();
            fireStore=FirebaseFirestore.getInstance();
            handler = new Handler();
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex.getMessage());
        }

    }

    public static FirebaseRepository getInstance(Context providedContext)
    {
        FirebaseRepository tempAccess = firebaseRepository;
        if(tempAccess != null)
        {
            System.out.println("FirebaseRepository");
            return tempAccess;
        }
        else
        {
            if(firebaseRepository == null)
            {
                firebaseRepository = new FirebaseRepository(providedContext);
            }
            System.out.println("FirebaseRepository");
            return firebaseRepository;
        }
    }

    public static FirebaseUser getCurrentUser()
    {
        return firebaseAuth.getCurrentUser();
    }

    public FirebaseAuth getFirebaseAuth()
    {
        return firebaseAuth;
    }

    public void loginByEmailAndPassword(@NotNull String email, @NotNull String password, Runnable successfulReaction, Runnable failureReaction)
    {
        Log.e("TEST LOGIN","YES");

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, (OnCompleteListener<AuthResult>) new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e("Test", "SignIn failed: " + task);
                        if (task.isSuccessful()) {
                                if(successfulReaction != null)
                                {
                                    handler.post(successfulReaction);
                                }
                            } else {
                                Log.e("FirebaseLogin", "SignIn failed: " + task.getException().getMessage());
                                if(failureReaction != null)
                                {
                                    handler.post(failureReaction);
                                }
                        }
                    }
                });

    }

    public void signupByEmailAndPassword(@NotNull String email, @NotNull String password, String phone_number, String username, Runnable successfulReaction, Runnable failureReaction) {
        if (firebaseAuth.getCurrentUser() != null) {
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            FirebaseMessaging.getInstance().getToken()
                                    .addOnCompleteListener(new OnCompleteListener<String>() {
                                        @Override
                                        public void onComplete(@NonNull Task<String> task) {
                                            if (!task.isSuccessful()) {
                                                Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                                                return;
                                            }

                                            // Get new FCM registration token
                                            String token = task.getResult();
                                            MyCustomer data = new MyCustomer(email, phone_number, username,token);

                                            fireStore.collection(FirebaseCollection.CUSTOMER_COLLECTION)
                                                    .document(user.getUid())
                                                    .set(data)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            // Successfully signed up and data written to Firestore
                                                            if (successfulReaction != null) {
                                                                handler.post(successfulReaction);
                                                            }
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            // Failed to write data to Firestore
                                                            if (failureReaction != null) {
                                                                handler.post(failureReaction);
                                                            }
                                                        }
                                                    });

                                        }
                                    });

                        } else {
                            // Failed to create Firebase Auth user
                            if (failureReaction != null) {
                                handler.post(failureReaction);
                            }
                        }
                    }
                });
    }

    public static void runForegroundTask(@NotNull Runnable task)
    {
        handler.post(task);
    }

    public static boolean load_data()
    {
        Thread backgroundLoadDataThread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                fireStore.collection(FirebaseCollection.CUSTOMER_COLLECTION).document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            mainUserInfo = task.getResult().toObject(MyCustomer.class);
                            handler.post(MainActivity.increaseProgress());
                            isUserInfoReady = true;
                        }
                        else
                        {
                            handler.post(MainActivity.hideProgressBar());
                        }
                    }
                });

                //load plans from database
                while(MainActivity.getCurrentProgressStepOfProgressBar() < 1)
                {
//                    Log.i("Wait", "run: I'm stuck 1");
                }
                if(MainActivity.getCurrentProgressStepOfProgressBar() == MainActivity.getProgressMax())
                {
                    runForegroundTask(MainActivity.hideProgressBar());
                    return;
                }


                List<Booking> setOfBookingId = mainUserInfo.getBookingList();
                if(setOfBookingId.isEmpty())
                {
                    System.out.println("<<Loaddata>>: empty data");
                    return;
                }
                for(int i=0; i< setOfBookingId.size(); i++)
                {
                    System.out.println(i+ ": "+ setOfBookingId.get(i));
                }

                Query getSetBookingListQuery = fireStore.collection(FirebaseCollection.BOOKING_COLLECTION).whereIn("id", setOfBookingId);
                getSetBookingListQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            System.out.println("queryDoc is empty");

                            return;
                        }
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot doc: docs) {
                            Booking convertedDoc = doc.toObject(Booking.class);

                            mainUserInfo.getBookingList().add(convertedDoc);
                        }
                        runForegroundTask(MainActivity.increaseProgress());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Load data: " + e);
                        runForegroundTask(MainActivity.hideProgressBar());
                    }
                });

                while(MainActivity.getCurrentProgressStepOfProgressBar() < 2)
                {
//
                }
            }
        });
        backgroundLoadDataThread.start();
        return true;
    }

    public static List<Booking> getBookingByStatus(@NotNull int inputStatus)
    {
        List<Booking> suitableBooking = new ArrayList<Booking>();
        for(int i=0; i<FirebaseRepository.mainUserInfo.getBookingList().size(); i++)
        {
            Booking booking = FirebaseRepository.mainUserInfo.getBookingList().get(i);
            if(booking.getStatus()==inputStatus)
            {
                suitableBooking.add(booking);
            }
        }
        return suitableBooking;
    }

    public static MyCustomer getMainUserInfo(){
        return mainUserInfo;
    }

    public static FirebaseFirestore getFirestore() {
        return fireStore;
    }

}
