package com.example.festpal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.awt.font.TextAttribute;

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
        }
    }
}
