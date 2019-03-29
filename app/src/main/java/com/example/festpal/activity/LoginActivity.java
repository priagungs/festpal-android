package com.example.festpal.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.festpal.R;
import com.example.festpal.dialog.LoadingDialog;
import com.example.festpal.model.User;
import com.example.festpal.utils.Constant;
import com.example.festpal.utils.UtilsManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;
    private LinearLayout btnGoogle;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadingDialog = new LoadingDialog(this);

        btnGoogle = findViewById(R.id.google);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            loadingDialog.show();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                loadingDialog.dismiss();
                Log.w(TAG, "Google sign in failed", e);
                UtilsManager.showToast("Google sign in failed", this);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        final AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            new LoginToServerViaEmail(mAuth.getCurrentUser()).execute();
                            Log.d(TAG, "onComplete: email " + mAuth.getCurrentUser().getEmail());
                            Log.d(TAG, "onComplete: phone " + mAuth.getCurrentUser().getPhoneNumber());
                            Log.d(TAG, "onComplete: picture " + mAuth.getCurrentUser().getPhotoUrl());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            loadingDialog.dismiss();
                            UtilsManager.showToast("Authentication Failed.", LoginActivity.this);
                        }
                    }
                });
    }

    private class LoginToServerViaEmail extends AsyncTask<Void, Void, Integer> {

        FirebaseUser user;
        String body;

        public LoginToServerViaEmail(FirebaseUser user) {
            this.user = user;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            String url = Constant.GET_USER + user.getEmail();
            Log.d(TAG, "doInBackground: url " + url);
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 404) {
                    return -1;
                }
                else if (response.code() != 200) {
                    return -2;
                }
                body = response.body().string();
                Log.d(TAG, "doInBackground: bodyyyy " + body);
                return 0;
            } catch (IOException e) {
                e.printStackTrace();
                return -2;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            loadingDialog.dismiss();
            if (integer == -2) {
                UtilsManager.showToast("Koneksi Bermasalah", LoginActivity.this);
            }
            else if (integer == -1) {
                String email = user.getEmail();
                String phone = user.getPhoneNumber();
                String name = user.getDisplayName();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                startActivity(intent);
                finish();
            }
            else {
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                Log.d(TAG, "onPostExecute: body " + body);
                User usr = gson.fromJson(body, User.class);
                usr.setPicture(user.getPhotoUrl().toString());
                UtilsManager.saveUser(LoginActivity.this, usr);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
