package com.example.festpal.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.festpal.R;
import com.example.festpal.model.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FestivalListAdapter extends RecyclerView.Adapter<FestivalListAdapter.FestivalListViewHolder> {
    private static String TAG = FestivalListAdapter.class.getSimpleName();

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    private List<Event> events;
    private Context context;

    public FestivalListAdapter(List<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @NonNull
    @Override
    public FestivalListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rvitem_festival_list, viewGroup, false);
        return new FestivalListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FestivalListViewHolder festivalListViewHolder, int i) {
        festivalListViewHolder.setDate(events.get(i).getDate());
        festivalListViewHolder.setFestivalName(events.get(i).getName());
        festivalListViewHolder.setPictureFestival(events.get(i).getImage());
        Log.d(TAG, "onBindViewHolder: events semua\n" + events);
        Log.d(TAG, "onBindViewHolder: events " + events.get(i).getPricePerStands());
        festivalListViewHolder.setPrice("Rp " + events.get(i).getPricePerStands() + "/stand");
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class FestivalListViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFestival;
        TextView tvFestivalName;
        TextView tvDate;
        TextView tvPrice;

        public void setPictureFestival(String url) {
            Glide.with(context).load(url).into(ivFestival);
        }

        public void setFestivalName(String festivalName) {
            tvFestivalName.setText(festivalName);
        }

        public void setDate(Date date) {
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
            tvDate.setText(df.format(date));
        }

        public void setPrice(String price) {
            tvPrice.setText(price);
        }

        public FestivalListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFestival = itemView.findViewById(R.id.iv_festival);
            tvFestivalName = itemView.findViewById(R.id.tv_festival_name);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
