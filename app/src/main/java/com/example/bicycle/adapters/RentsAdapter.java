package com.example.bicycle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.bicycle.models.Location;
import com.squareup.picasso.Picasso;

public class RentsAdapter extends RecyclerView.Adapter<RentsAdapter.RentsViewHolder> {

    private final ArrayList<Location> rents;
    private final Context mContext;
    private Callback mCallback;

    public RentsAdapter(Context mContext, ArrayList<Location> rents) {
        this.mContext = mContext;
        this.rents = rents;
    }

    @NonNull
    @Override
    public RentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(mContext).inflate(R.layout.rent_list_item, parent, false);
        return new RentsViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RentsViewHolder holder, int position) {
        final Location singleItem = rents.get(position);

        holder.RentDate.setText(String.valueOf(singleItem.getDateLocation()));

        // Modern Picasso implementation
        Picasso.get()
                .load("http://10.0.2.2:3000/" + singleItem.getBike().getImage())
                .resize(80, 80)
                .centerCrop()
//                .placeholder(R.drawable.ic_bike_placeholder) // Add placeholder
//                .error(R.drawable.ic_broken_image) // Add error image
                .into(holder.RentImage);

        holder.rentView.setOnClickListener(v -> mCallback.onItemClicked(singleItem));
    }

    @Override
    public int getItemCount() {
        return rents.size();
    }

    public static class RentsViewHolder extends RecyclerView.ViewHolder {
        public final TextView RentDate;
        public final ImageView RentImage;
        public final Button rentView;
        final RentsAdapter mAdapter;

        public RentsViewHolder(@NonNull View itemView, RentsAdapter mAdapter) {
            super(itemView);
            this.RentDate = itemView.findViewById(R.id.rentDate);
            this.RentImage = itemView.findViewById(R.id.rentImage);
            this.rentView = itemView.findViewById(R.id.btn_view);
            this.mAdapter = mAdapter;
        }
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onItemClicked(Location rent);
    }
}