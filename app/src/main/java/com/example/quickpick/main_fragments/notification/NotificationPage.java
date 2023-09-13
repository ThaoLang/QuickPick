package com.example.quickpick.main_fragments.notification;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickpick.MainActivity;
import com.example.quickpick.adapter.NotificationAdapter;
import com.example.quickpick.databinding.FragmentNotificationBinding;

public class NotificationPage extends Fragment implements NotificationAdapter.Callbacks{
    private static volatile NotificationPage instance;
    private static String INIT_NAME = "NOTIFICATION";
    private static String name;

    private MainActivity mainActivity;

    private Context context;

    LinearLayoutManager notificationManager;

    FragmentNotificationBinding binding;
    private NotificationAdapter notificationAdapter;

    NotificationPage()
    {
        //require an empty constructor
    }

    public void setContext(Context providedContext)
    {
        context = providedContext;
    }

    public void setName(String providedName)
    {
        name = providedName;
    }

    public static NotificationPage newInstance(String fragmentName) {
        NotificationPage tempInstance = instance;
        if(tempInstance != null)
        {
            return tempInstance;
        }
        //else
        synchronized (NotificationPage.class)
        {
            if(instance == null)
            {
                instance = new NotificationPage();
                Bundle initBundle = new Bundle();
                initBundle.putString(INIT_NAME, fragmentName);
                instance.setArguments(initBundle);
            }
            return instance;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle initBundle = getArguments();
            name = initBundle.getString(INIT_NAME);
        }

        try {
            context = getActivity();
            mainActivity = (MainActivity) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Notification:onCreate: IllegalStateException");
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);

        notificationManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        notificationManager.setStackFromEnd(true);
        binding.listNotification.setLayoutManager(notificationManager);

        return binding.getRoot();
    }

    public void swapToDetailNotification(com.example.quickpick.components.Notification notifications){

    }

}
