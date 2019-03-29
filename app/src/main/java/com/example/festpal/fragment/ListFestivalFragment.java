package com.example.festpal.fragment;

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

import com.example.festpal.R;
import com.example.festpal.adapter.FestivalListAdapter;
import com.example.festpal.model.Event;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListFestivalFragment extends Fragment {

    private static String TAG = ListFestivalFragment.class.getSimpleName();
    private RecyclerView rvItems;
    private ImageView btnBack;
    private ImageView btnSearch;
    private EditText etSearch;
    private String body;


    public ListFestivalFragment() {
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
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_festival_list, container, false);
        Log.d(TAG, "onCreateView: body\n" + body);
        Type listOfTestObject = new TypeToken<List<Event>>(){}.getType();
        List<Event> events = new Gson().fromJson(body, listOfTestObject);
        Log.d(TAG, "onCreateView: events\n" + events);
        rvItems = view.findViewById(R.id.rv_items);
        FestivalListAdapter adapter = new FestivalListAdapter(events, getContext());
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(getContext()));

        btnBack = view.findViewById(R.id.btn_back);
        btnSearch = view.findViewById(R.id.btn_search);
        etSearch = view.findViewById(R.id.et_search);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExploreFragment exploreFragment = new ExploreFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.root_frame, exploreFragment).commit();
            }
        });

        return view;

    }
}
