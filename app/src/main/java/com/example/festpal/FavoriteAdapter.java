package com.example.festpal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.festpal.model.Event;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<Event> events;
    private Context context;

    public FavoriteAdapter(List<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rvitem_festival_favorite_list,viewGroup,false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder favoriteViewHolder, int i) {
        Glide.with(context).load(events.get(i).getImage()).into(favoriteViewHolder.mPhotoFestival);
        favoriteViewHolder.mFestivalTitle.setText(events.get(i).getName());
        favoriteViewHolder.mFestivalPlace.setText(events.get(i).getVenue());
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");

        favoriteViewHolder.mFestivalDate.setText(df.format(events.get(i).getDate()));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
    public class FavoriteViewHolder extends RecyclerView.ViewHolder{
        public ImageView mPhotoFestival;
        public TextView mFestivalTitle;
        public TextView mFestivalDate;
        public TextView mFestivalPlace;
        public Button mRemoveFavorites;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            mPhotoFestival = itemView.findViewById(R.id.iv_festival);
            mFestivalTitle = itemView.findViewById(R.id.tv_festival_name);
            mFestivalDate = itemView.findViewById(R.id.tv_date);
            mFestivalPlace = itemView.findViewById(R.id.tv_place);
            mRemoveFavorites = itemView.findViewById(R.id.button_remove_favorite);
        }
    }
}
