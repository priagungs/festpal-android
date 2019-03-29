package com.example.festpal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.festpal.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilsManager {

    public static final String PREFS_NAME = "UserDataModel";
    public static final String PREFS_KEY = "UserPref";

    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void saveUser(Context context, User user) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonChat = gson.toJson(user);

        editor.putString(PREFS_KEY, jsonChat);

        editor.commit();
    }

    public static void clearAllDataUser(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public static User getUser(Context context) {
        SharedPreferences settings;
        User user;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(PREFS_KEY)) {
            String jsonChat = settings.getString(PREFS_KEY, null);
            Gson gson = new Gson();
            user = gson.fromJson(jsonChat,
                    User.class);

            return user;
        } else
            return null;
    }



}
