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
import com.example.bicycle.models.Record;

import java.util.ArrayList;


public class TrackAdapter  extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private final ArrayList<Record> records;
    private Context mContext;
    private TrackAdapter.Callback mCallback;


    public TrackAdapter(Context mContext, ArrayList<Record> records) {
        this.mContext = mContext ;
        this.records = records;
    }


    @NonNull
    @Override
    public TrackAdapter.TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(mContext).inflate(R.layout.records_item, parent, false);
        return new TrackViewHolder(mItemView, this);
    }


    @Override
    public void onBindViewHolder(@NonNull TrackAdapter.TrackViewHolder holder, int position) {
        final Record singleItem = records.get(position);
        holder.RecordDate.setText(String.valueOf(singleItem.getDaterecord()));
        holder.recordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onItemClicked(singleItem);
            }
        });

        holder.recordDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   mCallback.ItemDelete(singleItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder {

        public final TextView RecordDate;
        public final Button recordView;
        public final Button recordDelete;
        final TrackAdapter mAdapter;


        public TrackViewHolder(@NonNull View itemView, TrackAdapter mAdapter) {
            super(itemView);
            this.RecordDate = itemView.findViewById(R.id.recordDate);
            this.recordView = itemView.findViewById(R.id.btn_view);
            this.recordDelete = itemView.findViewById(R.id.deleteRec);
            this.mAdapter = mAdapter;
        }


    }


    public void setCallback(TrackAdapter.Callback callback) {
        mCallback = callback;
    }


    public interface Callback {
        void onItemClicked(Record record);
        void ItemDelete(Record record);
    }



}
