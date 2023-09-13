package com.example.quickpick.activities;

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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickpick.R;
import com.example.quickpick.activities.booking.BookingPage;
import com.example.quickpick.activities.booking.ChoosePickUpLocation;
import com.example.quickpick.databinding.ActivitySearchBinding;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchLocation extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private String startAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding= ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent requestIntent = getIntent();

        if(binding.suggestSearch.getVisibility() == View.VISIBLE)
        {
            binding.suggestSearch.setVisibility(View.GONE);
        }

        binding.pickupLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchLocation.this, ChoosePickUpLocation.class);
                Bundle bundle=new Bundle();
                bundle.putString("pickup_location",binding.pickupLocationBtn.getText().toString());

                intent.putExtra("location search",bundle);
                choosePickupLocationLauncher.launch(intent);
            }
        });

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

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent;
                intent= new Intent(SearchLocation.this, BookingPage.class);
                Bundle bundle=new Bundle();
                bundle.putString("pickup address",binding.pickupLocationBtn.getText().toString());
                bundle.putString("destination address",binding.searchView.getQuery().toString());

                intent.putExtra("search price",bundle);
                startActivity(intent);

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
                    Geocoder geocoder = new Geocoder((SearchLocation.this));
                    try {
                        addressesList = geocoder.getFromLocationName(location, 10);

                        if (addressesList.size() > 0) {
                            List<String> suggest = addressesList.stream().map((address1) -> address1.getAddressLine(0)).collect(Collectors.toList());
                            String[] strings = suggest.stream().toArray(String[]::new);
                            Arrays.stream(strings).forEach(System.out::println);
                            System.out.println(suggest);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchLocation.this, R.layout.custom_suggest_line,strings);
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

    private ActivityResultLauncher<Intent> choosePickupLocationLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        startAddress = data.getStringExtra("pickup_location");
                        Log.e("PICKUP",startAddress);
                        binding.pickupLocationBtn.setText(startAddress);
                    }
                }
            }
    );
}