package com.example.quickpick.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quickpick.R;
import com.example.quickpick.components.Notification;
import com.example.quickpick.my_intefaces.ItemClickListener;

import java.util.ArrayList;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private ArrayList<Notification> notifications;


    Context context;

    private NotificationAdapter.Callbacks listener;
    public void setListener(NotificationAdapter.Callbacks listener) {
        this.listener = listener;
    }

    public interface Callbacks {
        void swapToDetailNotification(Notification notifications);
    }

    public NotificationAdapter(Context _context,ArrayList<Notification> _notifications) {
        this.context = _context;
        this.notifications= new ArrayList<Notification>(_notifications);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView status;

        private  final TextView title;
        private final TextView content;
        private final TextView date;


        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.titleTextView);
            content = view.findViewById(R.id.contentTextView);
            date = view.findViewById(R.id.dateTextView);
            status = view.findViewById(R.id.dotImageView);

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
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_notification_item, viewGroup, false);

        return new NotificationAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder viewHolder, final int position) {

        notifications.add(new Notification("New Message", "You have a new message.", "1",false,"22 Aug"));
        notifications.add(new Notification("Reminder", "Don't forget the meeting tomorrow.", "2",true,"22 Aug"));
        notifications.add(new Notification("Event Invitation", "You're invited to our event!", "3",false,"22 Aug"));
        viewHolder.title.setText(notifications.get(position).getTitle());
        viewHolder.content.setText(notifications.get(position).getContent());
        viewHolder.date.setText(notifications.get(position).getDate());
        if (!notifications.get(position).isReaded()){
            viewHolder.status.setVisibility(View.VISIBLE);
            viewHolder.title.setTypeface(null, Typeface.BOLD);
        }


        viewHolder.setItemClickListener((view, position1, isLongClick) -> {

            //can swap to another activity using this method
            listener.swapToDetailNotification(notifications.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}

