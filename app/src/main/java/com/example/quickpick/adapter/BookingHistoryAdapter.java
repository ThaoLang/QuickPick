package com.example.quickpick.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickpick.MainActivity;
import com.example.quickpick.R;
import com.example.quickpick.components.Booking;
import com.example.quickpick.main_fragments.activity.BookingDetail;
import com.example.quickpick.main_fragments.activity.Booking_view_holder;
import com.example.quickpick.my_intefaces.ItemClickListener;
import com.example.quickpick.repositories.FirebaseRepository;

import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<Booking_view_holder> {
   private Context _context;
    private List<Booking> _data = null;


    public BookingHistoryAdapter(Context context, List<Booking> data)
    {
        _context = context;
        if(_data != null)
        {
            _data.clear();
        }
        _data = data;
    }


    @NonNull
    @Override
    public Booking_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(_context);
        View CustomRowView = inflater.inflate(R.layout.custom_booking_item, parent, false);
        Booking_view_holder viewHolder = new Booking_view_holder(CustomRowView, _context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Booking_view_holder holder, int position) {
        Booking booking = _data.get(position);

        holder.getTitleText().setText(booking.getType_transport() +"Booking");
        holder.getPriceText().setText(booking.getPrice());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Log.i("Trip_incoming", "Item is clicked");
                Intent intent = new Intent(_context, BookingDetail.class);
                intent.putExtra(" booking id", _data.get(position).getId());
                FirebaseRepository.runForegroundTask(((MainActivity)_context).startSpecificActivity(intent));
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return _data.size();
    }

}
