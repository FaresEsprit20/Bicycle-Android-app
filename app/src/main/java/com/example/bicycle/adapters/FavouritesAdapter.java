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
import com.example.miniprojetandroid.database.AppDataBase;
import com.squareup.picasso.Picasso;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.BikesViewHolder> {

    private final ArrayList<Bike> bikes;
    private final Context mContext;
    private Callback mCallback;

    public FavouritesAdapter(Context mContext, ArrayList<Bike> bikes) {
        this.mContext = mContext;
        this.bikes = bikes;
    }

    @NonNull
    @Override
    public BikesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(mContext).inflate(R.layout.favourites_item, parent, false);
        return new BikesViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BikesViewHolder holder, int position) {
        final Bike singleItem = bikes.get(position);

        holder.BikeName.setText(singleItem.getModel());

        // Updated Picasso implementation
        Picasso.get()
                .load("http://10.0.2.2:3000/" + singleItem.getImage())
                .resize(40, 40)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image) // Add default placeholder
                .error(R.drawable.error_image) // Add error placeholder
                .into(holder.BikeImage);

        holder.bikeDelete.setOnClickListener(v -> {
            AppDataBase.getAppDatabase(mContext).bikeDao().delete(singleItem);
            bikes.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, bikes.size());
            Toast.makeText(mContext, "Favourite deleted successfully!", Toast.LENGTH_SHORT).show();
        });

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
        public final Button bikeView, bikeDelete;
        final FavouritesAdapter mAdapter;

        public BikesViewHolder(@NonNull View itemView, FavouritesAdapter mAdapter) {
            super(itemView);
            this.BikeName = itemView.findViewById(R.id.bikeName);
            this.BikeImage = itemView.findViewById(R.id.bikeImage);
            this.bikeView = itemView.findViewById(R.id.btn_view);
            this.bikeDelete = itemView.findViewById(R.id.deleteFav);
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