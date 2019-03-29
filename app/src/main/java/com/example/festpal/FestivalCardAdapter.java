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

import com.example.festpal.activity.DetailsFestivalTennant;
import com.example.festpal.activity.DetailsFestivalTourist;

public class FestivalCardAdapter extends RecyclerView.Adapter<FestivalCardAdapter.FestCardViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    public FestivalCardAdapter(Context context){
        this.context = context;
        mInflater =LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public FestCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FestCardViewHolder(mInflater.inflate(R.layout.rvitem_festival_terdekat,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FestCardViewHolder festCardViewHolder, int i) {
        festCardViewHolder.mFestName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsFestivalTennant.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class FestCardViewHolder extends RecyclerView.ViewHolder{
        private ImageView mFestPhoto;
        private TextView mFestName;
        public FestCardViewHolder(@NonNull View itemView) {
            super(itemView);
            mFestName = (TextView) itemView.findViewById(R.id.festival_terdekat_name);
            mFestPhoto = (ImageView) itemView.findViewById(R.id.festival_terdekat_photo);
        }
    }
}
