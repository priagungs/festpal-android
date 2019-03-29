package com.example.festpal.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.festpal.R;
import com.example.festpal.dialog.LoadingDialog;
import com.example.festpal.model.User;
import com.example.festpal.model.UserRequest;
import com.example.festpal.utils.Constant;
import com.example.festpal.utils.UtilsManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    EditText etName;
    EditText etAddress;
    EditText etPhone;
    RadioGroup rgType;
    EditText etBusinessName;
    EditText etBusinessSector;
    EditText etDescription;
    Button btnRegister;
    LoadingDialog loadingDialog;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loadingDialog = new LoadingDialog(this);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        rgType = findViewById(R.id.rgType);
        etBusinessName = findViewById(R.id.etBusinessName);
        etBusinessSector = findViewById(R.id.etBusinessSector);
        etDescription = findViewById(R.id.etDescription);
        btnRegister = findViewById(R.id.btnRegister);
        Intent intent = getIntent();
        etName.setText(intent.getStringExtra("name"));
        etPhone.setText(intent.getStringExtra("phone"));
        email = intent.getStringExtra("email");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String address = etAddress.getText().toString();
                String phone = etPhone.getText().toString();
                String businessName = etBusinessName.getText().toString();
                String businessSector = etBusinessSector.getText().toString();
                String description = etDescription.getText().toString();
                int idRadio = rgType.getCheckedRadioButtonId();
                Boolean isUMKM = null;
                if (idRadio == R.id.rbTuris) {
                    isUMKM = false;
                }
                else if (idRadio == R.id.rbUmkm) {
                    isUMKM = true;
                }

                Boolean invalid;
                if (isUMKM != null && isUMKM) {
                    invalid = name.isEmpty() || address.isEmpty() || phone.isEmpty() || businessName.isEmpty() || businessSector.isEmpty()
                            || description.isEmpty();
                }
                else if (isUMKM != null && !isUMKM) {
                    invalid = name.isEmpty() || address.isEmpty() || phone.isEmpty();
                }
                else {
                    invalid = true;
                }
                if (invalid) {
                    UtilsManager.showToast("Semua bagian harus terisi", RegisterActivity.this);
                }
                else {
                    new RegisterUser(name, address, phone, businessName, businessSector, description, isUMKM, email).execute();
                    Log.d(TAG, "onClick: name, address, phone, businessName, businessSector, description, isUMKM, email\n" + name+ " " + address+ " " + phone+ " " + businessName + " " + businessSector + " " + description + " " +isUMKM + " " +email);
                }
            }
        });
    }

    private class RegisterUser extends AsyncTask<Void, Void, Boolean> {

        String name;
        String address;
        String phone;
        String businessName;
        String businessSector;
        String description;
        Boolean isUMKM;
        String email;

        String body;

        public RegisterUser(String name, String address, String phone, String businesssName, String businessSector, String description, Boolean isUMKM, String email) {
            this.name =name;
            this.address = address;
            this.businessName = businesssName;
            this.businessSector = businessSector;
            this.phone = phone;
            this.description = description;
            this.isUMKM = isUMKM;
            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            String url = Constant.POST_USER;
            UserRequest userRequest = new UserRequest();
            userRequest.setAddress(address);
            userRequest.setBusinessDesc(description);
            userRequest.setBusinessName(businessName);
            userRequest.setEmail(email);
            userRequest.setName(name);
            userRequest.setPhone(phone);
            userRequest.setUMKM(isUMKM);

            String json = new Gson().toJson(userRequest);
            Log.d(TAG, "doInBackground: json");
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create(mediaType, json);
            Request request = new Request.Builder().url(url).post(requestBody).build();
            try {
                Response response = client.newCall(request).execute();
                Log.d(TAG, "doInBackground: status code " + response.code());
                if (response.code() != 200) {
                    return false;
                }
                body = response.body().string();
                Log.d(TAG, "doInBackground: body " + body);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            loadingDialog.dismiss();
            if (aBoolean) {
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                User user = gson.fromJson(body, User.class);
                UtilsManager.saveUser(RegisterActivity.this, user);
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                UtilsManager.showToast("Gagal mendaftar", RegisterActivity.this);
            }
        }
    }
}
