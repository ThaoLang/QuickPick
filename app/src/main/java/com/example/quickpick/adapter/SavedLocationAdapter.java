package com.example.quickpick.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quickpick.R;
import com.example.quickpick.activities.saved_locations.SavedLocation;
import com.example.quickpick.components.SavedPlace;
import com.example.quickpick.my_intefaces.ItemClickListener;
import com.example.quickpick.repositories.FirebaseRepository;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SavedLocationAdapter extends RecyclerView.Adapter<SavedLocationAdapter.ViewHolder>{

    private ArrayList<SavedPlace> savedLocationArrayList;
    Context context;

    private SavedLocationAdapter.Callbacks listener;
    public void setListener(SavedLocationAdapter.Callbacks listener) {
        this.listener = listener;
    }

    public interface Callbacks {
        void swapToPost(SavedLocation plan);
    }

    public SavedLocationAdapter(Context _context,ArrayList<SavedPlace> _savedLocationArrayList) {
        this.context = _context;
        this.savedLocationArrayList= new ArrayList<SavedPlace>(_savedLocationArrayList);

        Log.e("HOW MANY POST",String.valueOf(this.savedLocationArrayList.size()));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private  final TextView name;
        private final TextView location_address;

        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.nick_name);
            location_address = view.findViewById(R.id.location_address);

            view.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

    }


    @Override
    public SavedLocationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_saved_location_item, viewGroup, false);

        return new SavedLocationAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(SavedLocationAdapter.ViewHolder viewHolder, final int position) {

        //get account
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Customer")
                .whereEqualTo("email", FirebaseRepository.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                viewHolder.name.setText(String.valueOf(document.get("name")));
                                viewHolder.location_address.setText(String.valueOf(document.get("location_address")));
                            }
                        }
                    }
                });

        viewHolder.setItemClickListener((view, position1, isLongClick) -> {

            //can swap to another activity using this method
            //listener.swapToPost(savedLocationArrayList.get(position1));
        });
    }

    @Override
    public int getItemCount() {
        return savedLocationArrayList.size();
    }
}
