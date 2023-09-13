package com.example.quickpick.main_fragments.activity;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickpick.R;
import com.example.quickpick.my_intefaces.ItemClickListener;

public class Booking_view_holder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private Context _context;

    private ItemClickListener itemClickListener;
    private Button bookAgain;
    private TextView priceText, titleText, dateText;

    public Booking_view_holder(@NonNull View itemView, Context context)
    {
        super(itemView);
        bookAgain = (Button) itemView.findViewById(R.id.bookAgainBtn);
        priceText = (TextView) itemView.findViewById(R.id.priceText);
        titleText = (TextView) itemView.findViewById(R.id.titleText);
        dateText = (TextView) itemView.findViewById(R.id.dateText);
        _context = context;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public Button getBookAgain()
    {
        return bookAgain;
    }

    public TextView getPriceText()
    {
        return priceText;
    }

    public TextView getTitleText()
    {
        return titleText;
    }

    public TextView getDateText()
    {
        return dateText;
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v)
    {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), true);
        return true;
    }
}