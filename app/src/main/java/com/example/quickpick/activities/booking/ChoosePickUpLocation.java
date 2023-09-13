package com.example.quickpick.activities.booking;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickpick.R;
import com.example.quickpick.databinding.ActivityChooseLocationBinding;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChoosePickUpLocation extends AppCompatActivity {

    private ActivityChooseLocationBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding= ActivityChooseLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle= getIntent().getBundleExtra("location search");
        if (bundle!=null){
            binding.searchView.setQuery(bundle.get("pickup_location").toString(),true);
        }

        if(binding.suggestSearch.getVisibility() == View.VISIBLE)
        {
            binding.suggestSearch.setVisibility(View.GONE);
        }

        binding.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //binding.searchView.setBackgroundColor(Color.TRANSPARENT);
                if(binding.suggestSearch.getVisibility() == View.VISIBLE)
                {
                    binding.suggestSearch.setVisibility(View.GONE);
                }
                return false;
            }
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("pickup_location", binding.searchView.getQuery().toString());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Intent intent;
//                intent= new Intent(ChoosePickUpLocation.this, BookingPage.class);
//                Bundle bundle=new Bundle();
//                bundle.putString("location address",binding.searchView.getQuery().toString());
//
//                intent.putExtra("location search",bundle);
//                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(binding.suggestSearch.getVisibility() != View.VISIBLE)
                {
                    binding.suggestSearch.setVisibility(View.VISIBLE);
                }
                String location = binding.searchView.getQuery().toString();
                Log.e("STRING LOATION",location);
                List<Address> addressesList = null;

                if(!location.equals("")) {
                    Geocoder geocoder = new Geocoder((ChoosePickUpLocation.this));
                    try {
                        addressesList = geocoder.getFromLocationName(location, 10);

                        if (addressesList.size() > 0) {
                            List<String> suggest = addressesList.stream().map((address1) -> address1.getAddressLine(0)).collect(Collectors.toList());
                            String[] strings = suggest.stream().toArray(String[]::new);
                            Arrays.stream(strings).forEach(System.out::println);
                            System.out.println(suggest);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChoosePickUpLocation.this, R.layout.custom_suggest_line,strings);
                            binding.suggestSearch.setAdapter(adapter);
                            binding.suggestSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    binding.searchView.setQuery(strings[position],true);
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
                else{
                    if(binding.suggestSearch.getVisibility() == View.VISIBLE)
                    {
                        binding.suggestSearch.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });
    }

    private void fileList(String newText) {
    }


}
