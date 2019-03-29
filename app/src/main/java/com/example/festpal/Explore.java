package com.example.festpal;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class Explore extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    private EditText etSearch;
    private ImageView btnSearch;
    private ImageView btnBack;
//
//    private String mParam1;
//    private String mParam2;
    private ViewPager mImageSlider;
    private TabLayout mDotsIndicator;
    private RecyclerView mFestivalTerdekat;
    public Explore() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment Explore.
//     */
//    public static Explore newInstance(String param1, String param2) {
//        Explore fragment = new Explore();
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
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        etSearch = view.findViewById(R.id.et_search);
        btnSearch = view.findViewById(R.id.btn_search);
        mImageSlider = (ViewPager) view.findViewById(R.id.image_slider);
        mDotsIndicator = (TabLayout) view.findViewById(R.id.tabDots);
        mFestivalTerdekat = (RecyclerView) view.findViewById(R.id.festival_terdekat);
        FestivalCardAdapter festivalCardAdapter = new FestivalCardAdapter(this.getContext());
        ImageSliderTitleAdapter adapter = new ImageSliderTitleAdapter(this.getContext());
        mImageSlider.setAdapter(adapter);
        mFestivalTerdekat.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL,false));
        mFestivalTerdekat.setAdapter(festivalCardAdapter);
        mDotsIndicator.setupWithViewPager(mImageSlider);
        return view;
    }
}
