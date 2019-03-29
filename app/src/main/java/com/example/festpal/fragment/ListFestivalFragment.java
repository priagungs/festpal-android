package com.example.festpal.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.festpal.R;
import com.example.festpal.activity.MainActivity;
import com.example.festpal.adapter.FestivalListAdapter;
import com.example.festpal.dialog.LoadingDialog;
import com.example.festpal.model.Event;
import com.example.festpal.utils.Constant;
import com.example.festpal.utils.UtilsManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListFestivalFragment extends Fragment {

    private static String TAG = ListFestivalFragment.class.getSimpleName();
    private RecyclerView rvItems;
    private ImageView btnBack;
    private ImageView btnSearch;
    private EditText etSearch;
    private String body;
    private String query;
    private LoadingDialog loadingDialog;
    private FestivalListAdapter adapter;


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
            query = getArguments().getString("QUERY");
        }
        loadingDialog = new LoadingDialog(getActivity());
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
        adapter = new FestivalListAdapter(events, getContext());
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(getContext()));

        btnBack = view.findViewById(R.id.btn_back);
        btnSearch = view.findViewById(R.id.btn_search);
        etSearch = view.findViewById(R.id.et_search);
        etSearch.setText(query);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSearch.getText().length() > 0) {
                    new SearchEvent().execute(etSearch.getText().toString());
                }
            }
        });

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER) && etSearch.getText().length() > 0) {
                    new SearchEvent().execute(etSearch.getText().toString());
                }
                return false;
            }
        });

        return view;

    }

    private class SearchEvent extends AsyncTask<String, Void, Integer> {

        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            String query = strings[0];
            Log.d(TAG, "doInBackground: query " + query);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            String url = Constant.GET_EVENTS + "/" + query;
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
            loadingDialog.dismiss();
            if (i == -1) {
                UtilsManager.showToast("Festival tidak ditemukan", getContext());
            }
            else if (i == -2) {
                UtilsManager.showToast("Koneksi Bermasalah", getContext());
            }
            else if (i == 0) {
                Type listOfTestObject = new TypeToken<List<Event>>(){}.getType();
                List<Event> events = new Gson().fromJson(result, listOfTestObject);
                Log.d(TAG, "onPostExecute: jumlah event " + events.size());
                adapter.setEvents(events);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
