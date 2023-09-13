package com.example.quickpick.main_fragments.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickpick.MainActivity;
import com.example.quickpick.R;
import com.example.quickpick.adapter.BookingHistoryAdapter;
import com.example.quickpick.adapter.BookingOngoingAdapter;
import com.example.quickpick.components.Booking;
import com.example.quickpick.constants.BookingStatus;
import com.example.quickpick.repositories.FirebaseRepository;
import com.google.android.material.button.MaterialButton;

import java.util.List;


public class ActivityTab extends Fragment {

    private List<Booking> demoData;
    private MainActivity main;
    private Context context;
    private String InitParam;


    private MaterialButton HistoryButton, OngoingButton, ScheduledButton;


    private RecyclerView recyclerViewPosition;

    private int currentMode;

    public static String CREATE_PLAN_MODE = "TRIPS_CREATE_PLAN_MODE";

    private static final String INIT_PARAM = "INIT_PARAM";

    public static final String UPCOMING = "Upcoming";
    public static final String ONGOING = "Ongoing";
    public static final String HISTORY = "Finished";



    public static ActivityTab newInstance(String initParam)
    {
        ActivityTab fragment = new ActivityTab();
        Bundle args = new Bundle();
        args.putString(INIT_PARAM, initParam);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            InitParam = getArguments().getString(INIT_PARAM);
        }

        try
        {
            main = (MainActivity) getActivity();
            context = getContext();
        }
        catch(IllegalStateException e)
        {
            throw new IllegalStateException("MainActivity must implements callbacks");
        }
    }


    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_activity, null);


        ScheduledButton = (MaterialButton) view.findViewById(R.id.trips_history_button);
        OngoingButton = (MaterialButton) view.findViewById(R.id.trips_ongoing_button);
        HistoryButton = (MaterialButton) view.findViewById(R.id.trips_scheduled_button);
         recyclerViewPosition = (RecyclerView) view.findViewById(R.id.trips_recyclerview_holder);


        currentMode = 0;
        HistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Incoming trips");
                if(currentMode == 0)
                {
                    return;
                }
                currentMode= 0;

                BookingHistoryAdapter adapter = new BookingHistoryAdapter(context, FirebaseRepository.getBookingByStatus(BookingStatus.COMPLETED));
                recyclerViewPosition.removeAllViews();
                recyclerViewPosition.setAdapter(adapter);
                HistoryButton.setBackgroundColor(getResources().getColor(R.color.CustomColor1, Resources.getSystem().newTheme()));
                OngoingButton.setBackgroundColor(getResources().getColor(R.color.md_theme_light_onPrimary, Resources.getSystem().newTheme()));
                ScheduledButton.setBackgroundColor(getResources().getColor(R.color.md_theme_light_onPrimary, Resources.getSystem().newTheme()));
            }
        });

        OngoingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Ongoing trips");
                if(currentMode == 1)
                {
                    return;
                }
                currentMode = 1;


                BookingOngoingAdapter adapter = new BookingOngoingAdapter(getContext(), FirebaseRepository.getBookingByStatus(BookingStatus.ONGOING));
                recyclerViewPosition.removeAllViews();
                recyclerViewPosition.setAdapter(adapter);
                OngoingButton.setBackgroundColor(getResources().getColor(R.color.CustomColor1, Resources.getSystem().newTheme()));
                ScheduledButton.setBackgroundColor(getResources().getColor(R.color.md_theme_light_onPrimary, Resources.getSystem().newTheme()));
                HistoryButton.setBackgroundColor(getResources().getColor(R.color.md_theme_light_onPrimary, Resources.getSystem().newTheme()));
            }
        });

        ScheduledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("History");
                if(currentMode == 2)
                {
                    return;
                }
                currentMode = 2;

                BookingHistoryAdapter adapter = new BookingHistoryAdapter(getContext(), FirebaseRepository.getBookingByStatus(BookingStatus.SCHEDULED));
                recyclerViewPosition.removeAllViews();
                recyclerViewPosition.setAdapter(adapter);
                ScheduledButton.setBackgroundColor(getResources().getColor(R.color.CustomColor1, Resources.getSystem().newTheme()));
                OngoingButton.setBackgroundColor(getResources().getColor(R.color.md_theme_light_onPrimary, Resources.getSystem().newTheme()));
                HistoryButton.setBackgroundColor(getResources().getColor(R.color.md_theme_light_onPrimary, Resources.getSystem().newTheme()));
            }
        });

        HistoryButton.setBackgroundColor(getResources().getColor(R.color.CustomColor10, Resources.getSystem().newTheme()));

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        recyclerViewPosition.removeAllViews();
        if(currentMode == BookingStatus.COMPLETED)
        {
            BookingHistoryAdapter adapter = new BookingHistoryAdapter(getContext(), FirebaseRepository.getBookingByStatus(BookingStatus.COMPLETED));
            recyclerViewPosition.setAdapter(adapter);
        }
        else if(currentMode == BookingStatus.ONGOING)
        {
            BookingOngoingAdapter adapter = new BookingOngoingAdapter(getContext(), FirebaseRepository.getBookingByStatus(BookingStatus.ONGOING));
            recyclerViewPosition.setAdapter(adapter);
        }
        else if(currentMode == BookingStatus.SCHEDULED)
        {
            BookingHistoryAdapter adapter = new BookingHistoryAdapter(getContext(), FirebaseRepository.getBookingByStatus(BookingStatus.SCHEDULED));
            recyclerViewPosition.setAdapter(adapter);
        }
    }
}
