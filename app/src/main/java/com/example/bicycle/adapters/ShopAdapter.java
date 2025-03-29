package com.example.bicycle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.bicycle.models.Shop;

import java.util.ArrayList;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder>{

    private final ArrayList<Shop> shops;
    private Context mContext;
    private ShopAdapter.Callback mCallback;


    public ShopAdapter(Context mContext, ArrayList<Shop> shops) {
        this.mContext = mContext ;
        this.shops = shops;
    }

    @NonNull
    @Override
    public ShopAdapter.ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(mContext).inflate(R.layout.shop_list_item, parent, false);
        return new ShopAdapter.ShopViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ShopViewHolder holder, int position) {
        final Shop singleItem = shops.get(position);
        holder.ShopName.setText(singleItem.getTitle());
        holder.shopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onItemClicked(singleItem);
            }
        });
    }


    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {

        public final TextView ShopName;

        public final Button shopView;
        final ShopAdapter mAdapter;

        public ShopViewHolder(@NonNull View itemView, ShopAdapter mAdapter) {
            super(itemView);
            this.ShopName = itemView.findViewById(R.id.shopName);
            this.shopView = itemView.findViewById(R.id.btn_view);
            this.mAdapter = mAdapter;
        }
    }


    public void setCallback(ShopAdapter.Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onItemClicked(Shop bike);
    }



}

