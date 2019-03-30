package com.example.festpal;

import android.content.Context;
import android.content.Intent;
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
import com.example.festpal.model.BookingResponse;
import com.example.festpal.model.Event;
import com.example.festpal.model.User;
import com.example.festpal.utils.UtilsManager;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

public class BookedAdapter extends RecyclerView.Adapter<BookedAdapter.BookedViewHolder> {
    private List<BookingResponse> bookings;
    private Context context;

    public void setEvents(List<BookingResponse> bookings) {
        this.bookings = bookings;
    }

    public BookedAdapter(List<BookingResponse> bookings, Context context) {
        this.bookings = bookings;
        this.context = context;
        Log.d("HUYU", "BookedAdapter: " + bookings);
    }
    @NonNull
    @Override
    public BookedAdapter.BookedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if(bookings.get(i).getStatus().equals("false")){
            view = LayoutInflater.from(context).inflate(R.layout.rvitem_festival_tent_booked_list,viewGroup,false);
        }
        else {
            view = LayoutInflater.from(context).inflate(R.layout.rvitem_festival_tent_booked_list_lunas,viewGroup,false);
        }
        return new BookedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedAdapter.BookedViewHolder bookedViewHolder, final int i) {
        Log.d("HUYU", "onBindViewHolder: image url " + bookings.get(i).getEvent().getImage());
        Glide.with(context).load(bookings.get(i).getEvent().getImage()).into(bookedViewHolder.ivFestival);
        bookedViewHolder.tvFestivalName.setText(bookings.get(i).getEvent().getName());
        bookedViewHolder.tvTotalPrice.setText("Rp " + bookings.get(i).getEvent().getPricePerStands() + "/stand");
        bookedViewHolder.tvNumberBook.setText("1 Stand");
        bookedViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event = new Gson().toJson(bookings.get(i).getEvent());
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
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public class BookedViewHolder extends RecyclerView.ViewHolder{
        ImageView ivFestival;
        TextView tvFestivalName;
        TextView tvNumberBook;
        TextView tvTotalPrice;
        View itemView;
//        Button bBatalFestival;
//        Button bBayarFestival;
        public BookedViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivFestival = (ImageView) itemView.findViewById(R.id.iv_festival);
            tvFestivalName = (TextView) itemView.findViewById(R.id.tv_festival_name);
            tvNumberBook = (TextView) itemView.findViewById(R.id.tv_number_book);
            tvTotalPrice = (TextView) itemView.findViewById(R.id.tv_total_price);
//            bBatalFestival = (Button) itemView.findViewById(R.id.button_cancel_tenant);
//            bBayarFestival = (Button) itemView.findViewById(R.id.button_pay_tenant);
        }
    }

}
