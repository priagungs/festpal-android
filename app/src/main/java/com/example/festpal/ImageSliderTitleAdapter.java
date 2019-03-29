package com.example.festpal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.festpal.model.Event;

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
        return events.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == ((LinearLayout) o);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.image_title_slide, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        TextView festivalName = (TextView) itemView.findViewById(R.id.festival_name);
        TextView festivalDate = (TextView) itemView.findViewById(R.id.festival_date);

        Glide.with(context).load(events.get(position).getImage()).into(imageView);
        festivalName.setText(events.get(position).getName());
        festivalDate.setText(events.get(position).getDate().toString());
        container.addView(itemView);
        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
