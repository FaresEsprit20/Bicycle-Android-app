package com.example.bicycle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.bicycle.models.Bike;
import com.squareup.picasso.Picasso;  // Make sure this import exists

public class BikesAdapter extends RecyclerView.Adapter<BikesAdapter.BikesViewHolder> {

    private final ArrayList<Bike> bikes;
    private Context mContext;
    private Callback mCallback;

    public BikesAdapter(Context mContext, ArrayList<Bike> bikes) {
        this.mContext = mContext;
        this.bikes = bikes;
    }

    @NonNull
    @Override
    public BikesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(mContext).inflate(R.layout.bike_list_item, parent, false);
        return new BikesViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BikesViewHolder holder, int position) {
        final Bike singleItem = bikes.get(position);
        holder.BikeName.setText(singleItem.getModel());

        // Modern Picasso implementation
        Picasso.get()
                .load("http://10.0.2.2:3000/" + singleItem.getImage())
                .resize(50, 50)
                .centerCrop()  // Recommended to maintain aspect ratio
                .into(holder.BikeImage);

        holder.bikeView.setOnClickListener(v -> mCallback.onItemClicked(singleItem));

        holder.BikeImage.setOnClickListener(v ->
                Toast.makeText(mContext, singleItem.getModel(), Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return bikes.size();
    }

    public static class BikesViewHolder extends RecyclerView.ViewHolder {
        public final TextView BikeName;
        public final ImageView BikeImage;
        public final Button bikeView;
        final BikesAdapter mAdapter;

        public BikesViewHolder(@NonNull View itemView, BikesAdapter mAdapter) {
            super(itemView);
            this.BikeName = itemView.findViewById(R.id.bikeName);
            this.BikeImage = itemView.findViewById(R.id.bikeImage);
            this.bikeView = itemView.findViewById(R.id.btn_view);
            this.mAdapter = mAdapter;
        }
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onItemClicked(Bike bike);
    }
}