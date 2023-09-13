package com.example.quickpick.activities.booking;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickpick.R;
import com.example.quickpick.databinding.ActivityBookingBinding;
import com.google.android.datatransport.runtime.Destination;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class BookingPage extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private int locationFocusIndex = -1;
    private List<Destination> listOfDestinations;
    private ActivityBookingBinding binding;
    private int defaultMarkerSize;
    private Marker selectedMarker = null;
    private LatLngBounds.Builder builder;
    private String startAddress;
    private String endAddress;
    private int REQUEST_CODE_PICKUP = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding= ActivityBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Bundle bundle= getIntent().getBundleExtra("search price");
        if (bundle!=null){
            startAddress=bundle.get("pickup address").toString();
            endAddress=bundle.get("destination address").toString();
        }
        Log.e("Start point",startAddress);
        Log.e("End point",endAddress);

        MapView mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng position = marker.getPosition();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

        if (startAddress.equals(marker.getTitle())) {
            Intent intent = new Intent(BookingPage.this, ChoosePickUpLocation.class);
            intent.putExtra("pickup_location", startAddress);
            choosePickupLocationLauncher.launch(intent);
        }

        if (startAddress.equals(marker.getTitle())) {
            Intent intent = new Intent(BookingPage.this, ChoosePickUpLocation.class);
            intent.putExtra("pickup_location", startAddress);
            intent.putExtra("destination_location", endAddress);
            startActivity(intent);
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MapView mapView = (MapView) findViewById(R.id.map_view);
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MapView mapView = (MapView) findViewById(R.id.map_view);
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MapView mapView = (MapView) findViewById(R.id.map_view);
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        MapView mapView = (MapView) findViewById(R.id.map_view);
        mapView.onLowMemory();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        builder = new LatLngBounds.Builder();
        displayOnMap(startAddress,0,true);
        displayOnMap(endAddress,1,false);

        LatLngBounds bounds = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

        mMap.setOnMarkerClickListener(this);

        if (locationFocusIndex != -1) {
            onMarkerClick(selectedMarker);
        }

        //displayPath(startAddress,endAddress);
    }

    private ActivityResultLauncher<Intent> choosePickupLocationLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        startAddress = data.getStringExtra("pickup_location");
                    }
                }
            }
    );

    private BitmapDescriptor getCustomMarkerIcon(int index, boolean isStartLocation, double scale) {
        // Create different icons for each destination and distinguish start and end locations
        int markerResource;
        if (isStartLocation) {
            // Use a different icon for the starting location
            markerResource = R.drawable.ic_start_point;
        } else {
            // Use a different icon for the ending location
            markerResource = R.drawable.ic_end_point;
        }

        // Load the original marker icon
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), markerResource);

        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromBitmap(originalBitmap);


        return descriptor;
    }


    private void displayOnMap(String address, int index, boolean isStartLocation) {
        double latitude = 0;
        double longitude = 0;
        Geocoder geocoder = new Geocoder(getApplicationContext());

        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address addressResult = addresses.get(0);
                latitude = addressResult.getLatitude();
                longitude = addressResult.getLongitude();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LatLng latLng = new LatLng(latitude, longitude);
        builder.include(latLng);

        // Determine whether this marker represents the starting location
        boolean isThisStartLocation = index == locationFocusIndex; // Modify this condition accordingly

        // Get the custom marker icon
        BitmapDescriptor customIcon = getCustomMarkerIcon(index, isStartLocation,0.5);

        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(address)
                .snippet(String.valueOf(index + 1))
                .icon(customIcon));

        if (isThisStartLocation) {
            selectedMarker = marker;
        }
    }

//    private  void displayPath(String start_point, String end_point){
//
//
//        LatLng start_latLng = getCoordinates(start_point);
//        LatLng end_latLng = getCoordinates(end_point);
//
//        List <LatLng> routeCoordinates=new ArrayList<>();
//        routeCoordinates.add(start_latLng);
//        routeCoordinates.add(end_latLng);
//        Log.e("ROUTES",routeCoordinates.toString());
//        PolylineOptions polylineOptions = new PolylineOptions()
//                .addAll(routeCoordinates)
//                .color(Color.BLUE)
//                .width(5f);
//        mMap.addPolyline(polylineOptions);
//
//        //getCoordinatesWithLocation(start_latLng,end_latLng);
//    }

//    private void displayPath(String startAddress, String endAddress) {
//        // Use Geocoder to get LatLng for start and end addresses
//        LatLng startLatLng = getCoordinates(startAddress);
//        LatLng endLatLng = getCoordinates(endAddress);
//
//        // Create a Directions API request
//        GeoApiContext context = new GeoApiContext.Builder()
//                .apiKey("YOUR_API_KEY")
//                .build();
//
//        DirectionsApiRequest request = DirectionsApi.newRequest(context)
//                .origin(new com.google.maps.model.LatLng(startLatLng.latitude, startLatLng.longitude))
//                .destination(new com.google.maps.model.LatLng(endLatLng.latitude, endLatLng.longitude))
//                .mode(TravelMode.DRIVING); // You can change the travel mode as needed
//
//        try {
//            DirectionsResult result = request.await();
//
//            // Extract route coordinates from the response
//            List<com.google.maps.model.LatLng> path = result.routes[0].overviewPolyline.decodePath();
//
//            List<LatLng> routeCoordinates = new ArrayList<>();
//            for (com.google.maps.model.LatLng latLng : path) {
//                routeCoordinates.add(new LatLng(latLng.lat, latLng.lng));
//            }
//
//            // Create and add the polyline to the map
//            PolylineOptions polylineOptions = new PolylineOptions()
//                    .addAll(routeCoordinates)
//                    .color(Color.BLUE)
//                    .width(5f);
//
//            mMap.addPolyline(polylineOptions);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private LatLng getCoordinates(String address_name){
        double latitude = 0;
        double longitude = 0;
        Geocoder geocoder = new Geocoder(getApplicationContext());

        try {
            List<Address> addresses = geocoder.getFromLocationName(address_name, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address addressResult = addresses.get(0);
                latitude = addressResult.getLatitude();
                longitude = addressResult.getLongitude();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new LatLng(latitude, longitude);
    }

}
