package com.example.festpal.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.festpal.R;
import com.example.festpal.activity.LoginActivity;
import com.example.festpal.model.User;
import com.example.festpal.utils.UtilsManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;

    LinearLayout linlayLogout;
    LinearLayout linlayProfileBusiness;
    ImageView profilePic;
    TextView profileName;
    TextView businessName;
    TextView businessSector;
    TextView businessDesc;
    TextView profileEmail;
    TextView profileAddress;
    TextView profileNumber;

    public ProfileFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ProfileFragment.
//     */
//    public static ProfileFragment newInstance(String param1, String param2) {
//        ProfileFragment fragment = new ProfileFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        linlayLogout = view.findViewById(R.id.profile_logout);
        linlayLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.google_client_id))
                        .requestEmail()
                        .build();
                GoogleSignInClient client = GoogleSignIn.getClient(getContext(), gso);
                client.signOut();
                UtilsManager.clearAllDataUser(getContext());
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        linlayProfileBusiness = view.findViewById(R.id.profile_bussiness_name);
        profileName = view.findViewById(R.id.profile_name);
        businessName = view.findViewById(R.id.name_bussiness);
        businessSector = view.findViewById(R.id.profile_bussiness_sector);
        businessDesc = view.findViewById(R.id.profile_bussiness_desc);
        profileEmail = view.findViewById(R.id.profile_email);
        profileAddress = view.findViewById(R.id.profile_address);
        profileNumber = view.findViewById(R.id.profile_number);
        profilePic = view.findViewById(R.id.profile_photo);

        User user = UtilsManager.getUser(getContext());
        profileName.setText(user.getName());
        profileEmail.setText(user.getEmail());
        profileAddress.setText(user.getAddress());
        profileNumber.setText(user.getPhone());
        Glide.with(getContext()).load(user.getPicture()).into(profilePic);
        if (!user.getUMKM()) {
            linlayProfileBusiness.setVisibility(View.GONE);
        }
        else {
            businessName.setText(user.getBusiness().getName());
            businessDesc.setText(user.getBusiness().getDescription());
            businessSector.setText(user.getBusiness().getSector());
        }

        return view;
    }
}
