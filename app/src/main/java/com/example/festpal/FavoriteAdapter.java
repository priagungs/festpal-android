package com.example.festpal;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.festpal.activity.DetailsFestivalTennant;
import com.example.festpal.activity.DetailsFestivalTourist;
import com.example.festpal.model.Event;
import com.example.festpal.model.User;
import com.example.festpal.utils.Constant;
import com.example.festpal.utils.UtilsManager;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private static final String TAG = FavoriteAdapter.class.getSimpleName();
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
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder favoriteViewHolder, final int i) {
        Glide.with(context).load(events.get(i).getImage()).into(favoriteViewHolder.mPhotoFestival);
        favoriteViewHolder.mFestivalTitle.setText(events.get(i).getName());
        favoriteViewHolder.mFestivalPlace.setText(events.get(i).getVenue());
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");

        favoriteViewHolder.mFestivalDate.setText(df.format(events.get(i).getDate()));
        favoriteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event = new Gson().toJson(events.get(i));
                Intent intent;
                User user = UtilsManager.getUser(context);
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
        favoriteViewHolder.mRemoveFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clickedddd");
                Event event = events.remove(i);
                notifyDataSetChanged();
                new RemoveFavorite(UtilsManager.getUser(context), event, i).execute();
            }
        });
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
        public View itemView;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            mPhotoFestival = itemView.findViewById(R.id.iv_festival);
            mFestivalTitle = itemView.findViewById(R.id.tv_festival_name);
            mFestivalDate = itemView.findViewById(R.id.tv_date);
            mFestivalPlace = itemView.findViewById(R.id.tv_place);
            mRemoveFavorites = itemView.findViewById(R.id.button_remove_favorite);
        }
    }

    private class RemoveFavorite extends AsyncTask<Void, Void, Boolean> {

        private User user;
        private Event event;
        private Integer id;
        public RemoveFavorite(User user, Event event, Integer id) {
            this.user = user;
            this.event = event;
            this.id = id;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            String url = null;
            url = Constant.PUT_FAVORITE + "/remove";
            Map<String, String> val = new HashMap<>();
            val.put("idUser", user.getId());
            val.put("idEvent", event.getId());
            String json = new Gson().toJson(val);
            Log.d(TAG, "doInBackground: json " + json);
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create(mediaType, json);
            Request request = new Request.Builder().url(url).put(requestBody).build();
            try {
                Response response = client.newCall(request).execute();
                Log.d(TAG, "doInBackground: response code " + response.code());
                Log.d(TAG, "doInBackground: response body " + response.body().string());
                if (response.code() != 200) {
                    return false;
                }
                else {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                user.getFavoriteFestivals().remove(event.getId());
                UtilsManager.saveUser(context, user);

            }
        }
    }
}
