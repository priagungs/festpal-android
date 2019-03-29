package com.example.festpal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.festpal.activity.DetailsFestivalTennant;
import com.example.festpal.activity.DetailsFestivalTourist;
import com.example.festpal.model.Event;
import com.example.festpal.model.User;
import com.example.festpal.utils.UtilsManager;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

public class ImageSliderTitleAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<Event> events;

    public ImageSliderTitleAdapter(Context context , List<Event> events){
        this.context =context;
        this.events = events;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == ((LinearLayout) o);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.image_title_slide, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        TextView festivalName = (TextView) itemView.findViewById(R.id.festival_name);
        TextView festivalDate = (TextView) itemView.findViewById(R.id.festival_date);

        Glide.with(context).load(events.get(position).getImage()).into(imageView);
        festivalName.setText(events.get(position).getName());
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");

        festivalDate.setText(df.format(events.get(position).getDate()));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event = new Gson().toJson(events.get(position));
                User user = UtilsManager.getUser(context);
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
        container.addView(itemView);
        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
