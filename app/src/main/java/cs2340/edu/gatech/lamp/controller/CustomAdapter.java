package cs2340.edu.gatech.lamp.controller;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import java.util.ArrayList;
import java.util.List;

import cs2340.edu.gatech.lamp.R;
import cs2340.edu.gatech.lamp.model.Shelter;

/**
 * Created by Potato on 3/7/2018.
 */

class CustomAdapter extends ArrayAdapter<Shelter> {

    private final Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView location;
        TextView space;
        ImageView call;
    }

    /**
     * Creates a CustomAdapter
     * @param data Shelter to add to the adapter
     * @param context the current context
     */
    public CustomAdapter(List<Shelter> data, Context context) {
        super(context, R.layout.listelement, data);
        //List<Shelter> shelters = data;
        this.mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        Shelter shelter = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listelement, parent, false);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.space = convertView.findViewById(R.id.hasSpace);
            viewHolder.call = convertView.findViewById(R.id.call_butt);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //hopefully this doesn't break anything
        //int lastPosition = position;

        viewHolder.name.setTextColor(Color.WHITE);
        viewHolder.location.setTextColor(Color.WHITE);
        viewHolder.space.setTextColor(Color.WHITE);
        assert shelter != null;
        viewHolder.name.setText(shelter.getName());
//        String address = shelter.getLocation().getStreet() + ", " +
//                shelter.getLocation().getCity() + ", " + shelter.getLocation().getState()
//                + " " + shelter.getLocation().getZip();
        viewHolder.location.setText(shelter.getLocation().getAddress());
        viewHolder.space.setText(!shelter.isFull() ? "Available" : "Full");
        viewHolder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call
            }
        });

        return convertView;
    }
}
