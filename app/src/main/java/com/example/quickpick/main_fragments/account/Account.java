package com.example.quickpick.main_fragments.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quickpick.MainActivity;
import com.example.quickpick.R;
import com.example.quickpick.activities.login.Login;
import com.example.quickpick.activities.saved_locations.SavedLocation;
import com.example.quickpick.components.AccountType;
import com.example.quickpick.components.MyCustomer;
import com.example.quickpick.repositories.FirebaseRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Account extends Fragment {

    //ensure thread-safe Singleton with lazy load
    private static volatile Account instance;
    private static String INIT_NAME = "HOME";

    private static String name;

    private MainActivity mainActivity;

    private Context context;

    private TextView username,phone_number,type_customer;
    private Button upgradeAccount, logout, profile_info,favorite_location;



    Account()
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

    public static Account newInstance(String fragmentName)
    {
        Account tempInstance = instance;
        if(tempInstance != null)
        {
            return tempInstance;
        }
        //else
        synchronized (Account.class)
        {
            if(instance == null)
            {
                instance = new Account();
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
        View accountView = inflater.inflate(R.layout.fragment_account, null);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();

                startActivity(new Intent(getContext(), Login.class));
                getParentFragment().onDetach();
            }
        });

        favorite_location.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SavedLocation.class);
            startActivity(intent);
        });

        upgradeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upgradeVIPAccount();
            }
        });

        return accountView;
    }

    @Override
    public void onStart() {
        super.onStart();
        MyCustomer mainUserInfo=FirebaseRepository.getMainUserInfo();

        if (mainUserInfo!=null){
            username.setText(mainUserInfo.getName());
            phone_number.setText(mainUserInfo.getPhoneNumber());

            if (mainUserInfo.getType_customer()== AccountType.Standard){
                type_customer.setText("Regular Account");
            }else{
                type_customer.setText("VIP account");
                upgradeAccount.setEnabled(false);
            }
        }
    }

    public void upgradeVIPAccount()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MyCustomer mainUserInfo=FirebaseRepository.getMainUserInfo();
        Map<String, Object> updates = new HashMap<>();
        updates.put("type_customer", "new_type_value");

        db.collection("Customer")
                .whereEqualTo("email", mainUserInfo)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Lấy ID của tài liệu tìm thấy
                            String documentId = documentSnapshot.getId();

                            // Dữ liệu bạn muốn cập nhật
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("type_customer", AccountType.VIP);

                            // Thực hiện cập nhật cho tài liệu tìm thấy
                            db.collection("Customer")
                                    .document(documentId)
                                    .update(updates)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Account.instance.getContext(), "Upgrade to VIP account successfully",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Xử lý khi có lỗi xảy ra
                                            Toast.makeText(Account.instance.getContext(), "Upgrade to VIP account successfully",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi có lỗi xảy ra trong quá trình truy vấn
                        Toast.makeText(Account.instance.getContext(), "Upgrade to VIP account successfully",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
