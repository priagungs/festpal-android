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

import java.util.List;

public class BookedAdapter extends RecyclerView.Adapter<BookedAdapter.BookedViewHolder> {
    private List<Event> events;
    private Context context;

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public BookedAdapter(List<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }
    @NonNull
    @Override
    public BookedAdapter.BookedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rvitem_festival_tent_booked_list,viewGroup,false);
        return new BookedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedAdapter.BookedViewHolder bookedViewHolder, int i) {
        Glide.with(context).load(events.get(i).getImage()).into(bookedViewHolder.ivFestival);
        bookedViewHolder.tvFestivalName.setText(events.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class BookedViewHolder extends RecyclerView.ViewHolder{
        ImageView ivFestival;
        TextView tvFestivalName;
        TextView tvNumberBook;
        TextView tvTotalPrice;
        Button bBatalFestival;
        Button bBayarFestival;
        public BookedViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFestival = (ImageView) itemView.findViewById(R.id.iv_festival);
            tvFestivalName = (TextView) itemView.findViewById(R.id.tv_festival_name);
            tvNumberBook = (TextView) itemView.findViewById(R.id.tv_number_book);
            tvTotalPrice = (TextView) itemView.findViewById(R.id.tv_total_price);
            bBatalFestival = (Button) itemView.findViewById(R.id.button_cancel_tenant);
            bBayarFestival = (Button) itemView.findViewById(R.id.button_pay_tenant);
        }
    }

}
