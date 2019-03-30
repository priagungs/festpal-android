package com.example.festpal.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.festpal.BookedAdapter;
import com.example.festpal.FavoriteAdapter;
import com.example.festpal.FestivalCardAdapter;
import com.example.festpal.R;
import com.example.festpal.model.BookingResponse;
import com.example.festpal.model.Event;
import com.example.festpal.model.User;
import com.example.festpal.utils.Constant;
import com.example.festpal.utils.UtilsManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class BookedFragment extends Fragment {
    private static final String TAG = BookedFragment.class.getSimpleName();
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;


    private EditText etSearch;
    private ImageView btnSearch;
    private RecyclerView booked;
    BookedAdapter bookedAdapter;

    private User user;

    public BookedFragment() {
        // Required empty public constructor
    }

//    public static BookedFragment newInstance(String param1, String param2) {
//        BookedFragment fragment = new BookedFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booked, container, false);
        booked = view.findViewById(R.id.rv_items);
        user = UtilsManager.getUser(getContext());

        new BookedFestival().execute();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new BookedFestival().execute();
    }

    private class BookedFestival extends AsyncTask<String, Void, Integer> {

        private String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            String url = Constant.GET_EVENTS_BOOKED_USER + user.getEmail();
            Log.d(TAG, "doInBackground: url " + url);
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = client.newCall(request).execute();
                Log.d(TAG, "doInBackground: response code " + response.code());
                if (response.code() != 200) {
                    return -1;
                }
                result = response.body().string();
                return 0;
            } catch (IOException e) {
                e.printStackTrace();
                return -2;
            }
        }

        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            if (i == -1) {
                UtilsManager.showToast("Festival tidak ditemukan", getActivity());
            } else if (i == -2) {
                UtilsManager.showToast("Koneksi Bermasalah", getActivity());
            } else if (i == 0) {
                Type listOfTestObject = new TypeToken<List<BookingResponse>>() {
                }.getType();
                Log.d(TAG, "onPostExecute: result\n" + result);
                List<BookingResponse> events = new Gson().fromJson(result, listOfTestObject);
                Log.d(TAG, "onPostExecute: eventsss\n" + events);
                bookedAdapter = new BookedAdapter(events, BookedFragment.this.getContext());
                booked.setAdapter(bookedAdapter);
                booked.setLayoutManager(new LinearLayoutManager(getContext()));

            }
        }
    }

}
