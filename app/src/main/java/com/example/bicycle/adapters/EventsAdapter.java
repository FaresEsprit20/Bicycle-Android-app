package com.example.bicycle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.bicycle.models.Event;

import java.util.ArrayList;



public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder>  {

    private final ArrayList<Event> events;
    private Context mContext;
    private EventsAdapter.Callback mCallback;

    public EventsAdapter(Context mContext, ArrayList<Event> events) {
        this.mContext = mContext ;
        this.events = events;
    }

    @NonNull
    @Override
    public EventsAdapter.EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(mContext).inflate(R.layout.events_item, parent, false);
        return new EventsAdapter.EventsViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.EventsViewHolder holder, int position) {
        final Event singleItem = events.get(position);

        holder.BikeName.setText(singleItem.getTitle());
        //holder.BikeImage.setBackgroundResource(singleItem.getImage());
        holder.BikeImage.setBackgroundResource(singleItem.getImage());

        holder.bikeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onItemClicked(singleItem);
            }
        });
        holder.BikeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,singleItem.getAdresse(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {

        public final TextView BikeName;
        public final ImageView BikeImage;
        public final Button bikeView;
        final EventsAdapter mAdapter;

        public EventsViewHolder(@NonNull View itemView, EventsAdapter mAdapter) {
            super(itemView);
            this.BikeName = itemView.findViewById(R.id.bikeName);
            this.BikeImage = itemView.findViewById(R.id.bikeImage);
            this.bikeView = itemView.findViewById(R.id.btn_view);
            this.mAdapter = mAdapter;
        }
    }


    public void setCallback(EventsAdapter.Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onItemClicked(Event event);
    }


}
