package com.example.festpal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.festpal.FavoriteAdapter;
import com.example.festpal.R;
import com.example.festpal.model.Event;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class FavoriteFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;
    RecyclerView mFavoriteList;
    String body;

    public FavoriteFragment() {
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
        if (getArguments() != null) {
            body = getArguments().getString("EVENTS");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        mFavoriteList = view.findViewById(R.id.rv_items);
        Type listOfTestObject = new TypeToken<List<Event>>(){}.getType();
        List<Event> events = new Gson().fromJson(body, listOfTestObject);
        FavoriteAdapter adapter = new FavoriteAdapter(events,getContext());
        mFavoriteList.setAdapter(adapter);
        return view;
    }
}
