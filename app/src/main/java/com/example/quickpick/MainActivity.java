//package com.example.quickpick;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.core.widget.ContentLoadingProgressBar;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.example.quickpick.activities.access_gps.ManualAccessLocation;
//import com.example.quickpick.activities.login.Login;
//import com.example.quickpick.main_fragments.account.Account;
//import com.example.quickpick.main_fragments.home.Home;
//import com.example.quickpick.main_fragments.activity.ActivityTab;
//import com.example.quickpick.main_fragments.notification.NotificationPage;
//import com.example.quickpick.repositories.FirebaseRepository;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.navigation.NavigationBarView;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//
//public class MainActivity extends FragmentActivity {
//
//    private FragmentTransaction fragmentTransaction;
//    private BottomNavigationView bottomNavigationView;
//    private Fragment currentScreen;
//
//    private int CURRENT_SELECTED_ID = 0;
//    private static ContentLoadingProgressBar progressBar;
//    private static int progressStep;
//
//    private static int screenType = 0;
//
//    private static boolean isRunning;
//    public static final int HOME_PAGE_ID = 0;
//    public static final int ACTIVITY_PAGE_ID = 1;
//    public static final int NOTIFICATION_PAGE_ID = 2;
//    public static final int ACCOUNT_PAGE_ID = 3;
//
//    private static final String HOME = "HOME";
//    private static final String ACTIVITY = "ACTIVITY";
//    private static final String NOTIFICATION = "NOTIFICATION";
//    private static final String ACCOUNT = "ACCOUNT";
//
//    private static final int PERMISSION_REQUEST_CODE = 100;
//    private static final int LOCATION_SETTINGS_REQUEST_CODE = 101;
//
//    private static String current_location = null;
//    private static boolean isLocationEnabled;
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        //checkLocationStatus();
//        Log.e("MAIN ACTIVITY","MESSAGE 1");
//        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom_nav);
//        //progressBar = (ContentLoadingProgressBar) findViewById(R.id.loading_progressbar);
//        isRunning = true;
//
//        currentScreen = Home.newInstance(HOME);
//        fragmentTransaction.replace(R.id.main_where_place_fragment, currentScreen);
//        fragmentTransaction.commit();
//
//        Log.e("MAIN ACTIVITY","MESSAGE 2");
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
////                System.out.println("Item clicked: " + item.getItemId());
//                int idItemSelected = item.getItemId();
//                Log.e("NAVIGATION", String.valueOf(idItemSelected));
//                switchScreenBySelectMenuItem(idItemSelected);
//                //return true if we want to the item be selected (be colored). Else, return false
//                return true;
//            }
//        });
//
//        Log.e("MAIN ACTIVITY","MESSAGE 4");
//        FirebaseRepository firebaseRepository = ((GlobalResources) getApplication()).getFirebaseRepository();
//        if (firebaseRepository.getCurrentUser() == null) {
//            FirebaseRepository.runForegroundTask(setLoadingProgressBarVisible(4, 0, 1));
//            FirebaseRepository.load_data();
//            if (screenType == 0) {
//                currentScreen = Home.newInstance(HOME);
//            }
//            Log.e("ERROR ACTIVITY",currentScreen.toString());
//
//        }
//        else{
//
//            fragmentTransaction.replace(R.id.main_where_place_fragment, currentScreen);
//            fragmentTransaction.commit();
//        }
//
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseRepository firebaseRepository = ((GlobalResources) getApplication()).getFirebaseRepository();
//        if (firebaseRepository.getCurrentUser() == null) {
//            Intent startLoginIntent = new Intent(MainActivity.this, Login.class);
//            startActivity(startLoginIntent);
//            finish();
//        }
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (!isRunning || FirebaseRepository.getCurrentUser() == null) {
//            fragmentTransaction.detach(currentScreen);
////            if (isLocationEnabled) {
////                // Lưu địa chỉ vào biến hoặc cơ sở dữ liệu
////                saveCurrentLocation(current_location);
////            }
//        }
//    }
//
//    private void switchScreenBySelectMenuItem(int idItemSelected) {
//        if (idItemSelected == CURRENT_SELECTED_ID) {
//            return;
//        }
//        if (idItemSelected == R.id.main_bottom_nav_home) {
//            screenType = HOME_PAGE_ID;
//            System.out.println("HOMEPAGE");
//        } else if (idItemSelected == R.id.main_bottom_nav_activity) {
//            screenType = ACTIVITY_PAGE_ID;
//            System.out.println("ACTIVITY");
//        } else if (idItemSelected == R.id.main_bottom_nav_notification) {
//            screenType = NOTIFICATION_PAGE_ID;
//            System.out.println("NOTIFICATION");
//        } else if (idItemSelected == R.id.main_bottom_nav_account) {
//            screenType = ACCOUNT_PAGE_ID;
//            System.out.println("MANAGE ACCOUNT");
//        } else {
//            System.out.println("NOTHING BE CHOSEN");
//        }
//        CURRENT_SELECTED_ID = idItemSelected;
//        switchScreenByScreenType(screenType);
//    }
//
//    public void switchScreenByScreenType(int inputScreenType) {
//        switch (inputScreenType) {
//            case HOME_PAGE_ID: {
//                currentScreen = Home.newInstance(HOME);
//                break;
//            }
//            case ACTIVITY_PAGE_ID: {
//                currentScreen = ActivityTab.newInstance(ACTIVITY);
//                break;
//            }
//            case NOTIFICATION_PAGE_ID: {
//                currentScreen = NotificationPage.newInstance(NOTIFICATION);
//                break;
//            }
//            case ACCOUNT_PAGE_ID: {
//                currentScreen = Account.newInstance(ACCOUNT);
//                break;
//            }
//        }
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.main_where_place_fragment, currentScreen);
//        transaction.commit();
//    }
//
//    public static Runnable setLoadingProgressBarVisible(@NotNull int Max, @NotNull int StartWith, @NotNull int ProgressStep) {
//        progressBar.setMax(100);
//        progressBar.setMin(0);
//        progressStep = 1;
//        Runnable loadingProgress = new Runnable() {
//            @Override
//            public void run() {
//                progressBar.setMax(Max);
//                progressBar.setMin(StartWith);
//                progressStep = ProgressStep;
//                progressBar.show();
//            }
//        };
//        return loadingProgress;
//    }
//
//    public static Runnable increaseProgress() {
//        Runnable increase = new Runnable() {
//            @Override
//            public void run() {
//                progressBar.incrementProgressBy(progressStep);
//                if (progressBar.getProgress() >= progressBar.getMax()) {
//                    progressBar.hide();
//                }
//            }
//        };
//        return increase;
//    }
//
//    public static Runnable hideProgressBar() {
//        Runnable hide = new Runnable() {
//            @Override
//            public void run() {
//                progressBar.hide();
//            }
//        };
//        return hide;
//    }
//
//    public static int getImplementedProgressStep() {
//        return progressStep;
//    }
//
//    public static int getCurrentProgressStepOfProgressBar() {
//        return progressBar.getProgress();
//    }
//
//
//    public Runnable toast(@NotNull String message) {
//        Runnable toastTask = new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//            }
//        };
//        return toastTask;
//    }
//
//    public static int getProgressMax() {
//        return progressBar.getMax();
//    }
//
//    public Runnable startSpecificActivity(Intent intent) {
//        Runnable foregroundTask = new Runnable() {
//            @Override
//            public void run() {
//                startActivity(intent);
//            }
//        };
//        return foregroundTask;
//    }
//
//    public void setIsRunning(@NotNull boolean status) {
//        isRunning = status;
//    }
//
//
//    private void checkLocationStatus() {
//        Toast.makeText(getApplicationContext(), "Helllo", Toast.LENGTH_SHORT).show();
//
//        if (current_location != null) {
//            isLocationEnabled = isLocationEnabled();
//            if (isLocationEnabled) {
//                current_location = getCurrentLocation();
//            }
//        } else {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // Nếu chưa có quyền, yêu cầu người dùng cấp quyền
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
//            }
//        }
//    }
//
//    private boolean isLocationEnabled() {
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (locationManager != null) {
//            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        }
//        return false;
//    }
//
//    private String getCurrentLocation() {
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (locationManager != null) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            }
//            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (location != null) {
//                double latitude = location.getLatitude();
//                double longitude = location.getLongitude();
//
//                return getAddressFromCoordinates(latitude,longitude);
//            }
//        }
//
//        return null;
//    }
//
//    // Xử lý kết quả yêu cầu quyền định vị
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                current_location=getCurrentLocation();
//            } else {
//                Intent intent=new Intent(getApplicationContext(), ManualAccessLocation.class);
//                startActivity(intent);
//            }
//        }
//    }
//
//    private String getAddressFromCoordinates(double latitude, double longitude) {
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        try {
//            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//
//            if (addresses != null && !addresses.isEmpty()) {
//                Address address = addresses.get(0);
//                String fullAddress = address.getAddressLine(0); // Địa chỉ đầy đủ
//                String city = address.getLocality(); // Thành phố
//                String state = address.getAdminArea(); // Tỉnh/Thành phố
//                String country = address.getCountryName(); // Quốc gia
//                String postalCode = address.getPostalCode(); // Mã bưu chính
//
//                String TAG="LOCATION ADDRESS";
//                Log.d(TAG, "Full Address: " + fullAddress);
//                Log.d(TAG, "City: " + city);
//                Log.d(TAG, "State: " + state);
//                Log.d(TAG, "Country: " + country);
//                Log.d(TAG, "Postal Code: " + postalCode);
//
//                return fullAddress;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//}
//
//
//
//
//
//
//
//
//
//
package com.example.quickpick;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.quickpick.activities.access_gps.ManualAccessLocation;
import com.example.quickpick.activities.login.Login;
import com.example.quickpick.main_fragments.account.Account;
import com.example.quickpick.main_fragments.home.Home;
import com.example.quickpick.main_fragments.activity.ActivityTab;
import com.example.quickpick.main_fragments.notification.NotificationPage;
import com.example.quickpick.repositories.FirebaseRepository;
import com.example.quickpick.services.Notification.NotificationService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends FragmentActivity {

    private FragmentTransaction fragmentTransaction;
    private BottomNavigationView bottomNavigationView;
    private Fragment currentScreen;

    private int CURRENT_SELECTED_ID = 0;
    private static ContentLoadingProgressBar progressBar;
    private static int progressStep;

    private static int screenType = 0;

    private static boolean isRunning;
    public static final int HOME_PAGE_ID = 0;
    public static final int ACTIVITY_PAGE_ID = 1;
    public static final int NOTIFICATION_PAGE_ID = 2;
    public static final int ACCOUNT_PAGE_ID = 3;

    private static final String HOME = "HOME";
    private static final String ACTIVITY = "ACTIVITY";
    private static final String NOTIFICATION = "NOTIFICATION";
    private static final String ACCOUNT = "ACCOUNT";

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int LOCATION_SETTINGS_REQUEST_CODE = 101;

    private static String current_location = null;
    private static boolean isLocationEnabled;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //checkLocationStatus();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom_nav);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.loading_progressbar);
        isRunning = true;


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                System.out.println("Item clicked: " + item.getItemId());
                int idItemSelected = item.getItemId();
                Log.e("NAVIGATION", String.valueOf(idItemSelected));
                switchScreenBySelectMenuItem(idItemSelected);
                //return true if we want to the item be selected (be colored). Else, return false
                return true;
            }
        });

        FirebaseRepository firebaseRepository = ((GlobalResources) getApplication()).getFirebaseRepository();
        if (firebaseRepository.getCurrentUser() != null) {
            FirebaseRepository.runForegroundTask(setLoadingProgressBarVisible(4, 0, 1));
            //FirebaseRepository.load_data();
            currentScreen = Home.newInstance(HOME);
            fragmentTransaction.replace(R.id.main_where_place_fragment, currentScreen);
            fragmentTransaction.commit();


            Intent intent= new Intent(MainActivity.this, NotificationService.class);
            startService(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRepository firebaseRepository = ((GlobalResources) getApplication()).getFirebaseRepository();
        if (firebaseRepository.getCurrentUser() == null) {
            Intent startLoginIntent = new Intent(MainActivity.this, Login.class);
            startActivity(startLoginIntent);
            finish();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isRunning || FirebaseRepository.getCurrentUser() == null) {
            fragmentTransaction.detach(currentScreen);
//            if (isLocationEnabled) {
//                // Lưu địa chỉ vào biến hoặc cơ sở dữ liệu
//                saveCurrentLocation(current_location);
//            }
        }
    }

    private void switchScreenBySelectMenuItem(int idItemSelected) {
        if (idItemSelected == CURRENT_SELECTED_ID) {
            return;
        }
        if (idItemSelected == R.id.main_bottom_nav_home) {
            screenType = HOME_PAGE_ID;
            System.out.println("HOMEPAGE");
        } else if (idItemSelected == R.id.main_bottom_nav_activity) {
            screenType = ACTIVITY_PAGE_ID;
            System.out.println("ACTIVITY");
        } else if (idItemSelected == R.id.main_bottom_nav_notification) {
            screenType = NOTIFICATION_PAGE_ID;
            System.out.println("NOTIFICATION");
        } else if (idItemSelected == R.id.main_bottom_nav_account) {
            screenType = ACCOUNT_PAGE_ID;
            System.out.println("MANAGE ACCOUNT");
        } else {
            System.out.println("NOTHING BE CHOSEN");
        }
        CURRENT_SELECTED_ID = idItemSelected;
        switchScreenByScreenType(screenType);
    }

    public void switchScreenByScreenType(int inputScreenType) {
        switch (inputScreenType) {
            case HOME_PAGE_ID: {
                currentScreen = Home.newInstance(HOME);
                break;
            }
            case ACTIVITY_PAGE_ID: {
                currentScreen = ActivityTab.newInstance(ACTIVITY);
                break;
            }
            case NOTIFICATION_PAGE_ID: {
                currentScreen = NotificationPage.newInstance(NOTIFICATION);
                break;
            }
            case ACCOUNT_PAGE_ID: {
                currentScreen = Account.newInstance(ACCOUNT);
                break;
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_where_place_fragment, currentScreen);
        transaction.commit();
    }

    public static Runnable setLoadingProgressBarVisible(@NotNull int Max, @NotNull int StartWith, @NotNull int ProgressStep) {
        progressBar.setMax(100);
        progressBar.setMin(0);
        progressStep = 1;
        Runnable loadingProgress = new Runnable() {
            @Override
            public void run() {
                progressBar.setMax(Max);
                progressBar.setMin(StartWith);
                progressStep = ProgressStep;
                progressBar.show();
            }
        };
        return loadingProgress;
    }

    public static Runnable increaseProgress() {
        Runnable increase = new Runnable() {
            @Override
            public void run() {
                progressBar.incrementProgressBy(progressStep);
                if (progressBar.getProgress() >= progressBar.getMax()) {
                    progressBar.hide();
                }
            }
        };
        return increase;
    }

    public static Runnable hideProgressBar() {
        Runnable hide = new Runnable() {
            @Override
            public void run() {
                progressBar.hide();
            }
        };
        return hide;
    }

    public static int getImplementedProgressStep() {
        return progressStep;
    }

    public static int getCurrentProgressStepOfProgressBar() {
        return progressBar.getProgress();
    }


    public Runnable toast(@NotNull String message) {
        Runnable toastTask = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        };
        return toastTask;
    }

    public static int getProgressMax() {
        return progressBar.getMax();
    }

    public Runnable startSpecificActivity(Intent intent) {
        Runnable foregroundTask = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };
        return foregroundTask;
    }

    public void setIsRunning(@NotNull boolean status) {
        isRunning = status;
    }


    private void checkLocationStatus() {
        Toast.makeText(getApplicationContext(), "Helllo", Toast.LENGTH_SHORT).show();

        if (current_location != null) {
            isLocationEnabled = isLocationEnabled();
            if (isLocationEnabled) {
                current_location = getCurrentLocation();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Nếu chưa có quyền, yêu cầu người dùng cấp quyền
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return false;
    }

    private String getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                return getAddressFromCoordinates(latitude,longitude);
            }
        }

        return null;
    }

    // Xử lý kết quả yêu cầu quyền định vị
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                current_location=getCurrentLocation();
            } else {
                Intent intent=new Intent(getApplicationContext(), ManualAccessLocation.class);
                startActivity(intent);
            }
        }
    }

    private String getAddressFromCoordinates(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String fullAddress = address.getAddressLine(0); // Địa chỉ đầy đủ
                String city = address.getLocality(); // Thành phố
                String state = address.getAdminArea(); // Tỉnh/Thành phố
                String country = address.getCountryName(); // Quốc gia
                String postalCode = address.getPostalCode(); // Mã bưu chính

                String TAG="LOCATION ADDRESS";
                Log.d(TAG, "Full Address: " + fullAddress);
                Log.d(TAG, "City: " + city);
                Log.d(TAG, "State: " + state);
                Log.d(TAG, "Country: " + country);
                Log.d(TAG, "Postal Code: " + postalCode);

                return fullAddress;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}










