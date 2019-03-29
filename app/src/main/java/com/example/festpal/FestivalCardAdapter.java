package com.example.festpal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.festpal.activity.DetailsFestivalTennant;
import com.example.festpal.activity.DetailsFestivalTourist;
import com.example.festpal.model.Event;
import com.example.festpal.model.User;
import com.example.festpal.utils.UtilsManager;
import com.google.gson.Gson;

import java.util.List;

public class FestivalCardAdapter extends RecyclerView.Adapter<FestivalCardAdapter.FestCardViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private List<Event> events;
    private User user;
    public FestivalCardAdapter(Context context, List<Event> events){
        this.context = context;
        this.events = events;
        mInflater =LayoutInflater.from(context);
        user = UtilsManager.getUser(context);

    }
    @NonNull
    @Override
    public FestCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FestCardViewHolder(mInflater.inflate(R.layout.rvitem_festival_terdekat,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FestCardViewHolder festCardViewHolder, final int i) {
        festCardViewHolder.mFestName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event = new Gson().toJson(events.get(i));
                Intent intent;
                if (user.getUMKM()) {
                    intent = new Intent(context, DetailsFestivalTennant.class);
                }
                else {
                    intent = new Intent(context, DetailsFestivalTourist.class);
                }
                intent.putExtra("EVENT", event);
                context.startActivity(intent);
            }
        });
        festCardViewHolder.mFestName.setText(events.get(i).getName());
        Glide.with(context).load(events.get(i).getImage()).into(festCardViewHolder.mFestPhoto);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class FestCardViewHolder extends RecyclerView.ViewHolder{
        public ImageView mFestPhoto;
        public TextView mFestName;
        public FestCardViewHolder(@NonNull View itemView) {
            super(itemView);
            mFestName = (TextView) itemView.findViewById(R.id.festival_terdekat_name);
            mFestPhoto = (ImageView) itemView.findViewById(R.id.festival_terdekat_photo);
        }
    }
}
