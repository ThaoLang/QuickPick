package com.example.quickpick.main_fragments.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.quickpick.MainActivity;
import com.example.quickpick.activities.SearchLocation;
import com.example.quickpick.activities.saved_locations.SavedLocation;
import com.example.quickpick.constants.GlobalImageSlider;
import com.example.quickpick.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class Home extends Fragment {

    //ensure thread-safe Singleton with lazy load
    private static volatile Home instance;
    private static String INIT_NAME = "HOME";

    private static String name;

    private MainActivity mainActivity;

    private Context context;

    private ArrayList<SlideModel> slideModels=new ArrayList<>();





    private FragmentHomeBinding binding;


    Home()
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

    public static Home newInstance(String fragmentName)
    {
        Home tempInstance = instance;
        if(tempInstance != null)
        {
            return tempInstance;
        }
        //else
        synchronized (Home.class)
        {
            if(instance == null)
            {
                instance = new Home();
                Bundle initBundle = new Bundle();
                initBundle.putString(INIT_NAME, fragmentName);
                instance.setArguments(initBundle);
            }
            return instance;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            Bundle initBundle = getArguments();
            name = initBundle.getString(INIT_NAME);
        }
        try
        {
            context = getActivity();
            mainActivity = (MainActivity) getActivity();
        }
        catch (IllegalStateException ex)
        {
            throw new IllegalStateException("Home:onCreate: IllegalStateException");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        //View homeView = inflater.inflate(R.layout.fragment_home, null);


        //search btn
        binding.searchView.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SearchLocation.class);
            //intent.putExtra(Search.SEARCH_MODE, Search.SEARCH_LOCATION_INFO);
            startActivity(intent);
        });

        binding.motorbikeImage.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SearchLocation.class);
            //intent.putExtra(Search.SEARCH_MODE, Search.SEARCH_LOCATION_INFO);
            startActivity(intent);
        });

        binding.car4seatImage.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SearchLocation.class);
            //intent.putExtra(Search.SEARCH_MODE, Search.SEARCH_LOCATION_INFO);
            startActivity(intent);
        });

        binding.car7seatImage.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SearchLocation.class);
            //intent.putExtra(Search.SEARCH_MODE, Search.SEARCH_LOCATION_INFO);
            startActivity(intent);
        });

        //saved location btn
        binding.savedLocationBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SavedLocation.class);
            startActivity(intent);
        });


        binding.imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        for (int i = 0; i< GlobalImageSlider.default_image_url.length; i++){
            slideModels.add(new SlideModel(GlobalImageSlider.default_image_url[i], ScaleTypes.FIT));

        }
    }

}
