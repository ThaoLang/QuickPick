//package com.example.quickpick.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class SuggestSearchAdapter extends ArrayAdapter<String> {
//    private final Context context;
//    private final String[] values;
//
//    public CustomArrayAdapter(Context context, String[] values) {
//        super(context, R.layout.custom_suggest_line, values);
//        this.context = context;
//        this.values = values;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(R.layout.custom_suggest_line, parent, false);
//
//        TextView textView = rowView.findViewById(R.id.text1); // Use the ID defined in your custom layout
//        ImageView imageView = rowView.findViewById(R.id.edit_button); // Optional, if you need it
//
//        textView.setText(values[position]);
//        // Set other data to imageView if needed
//
//        return rowView;
//    }
//}
//
