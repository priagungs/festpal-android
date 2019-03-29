package com.example.festpal.utils;

import android.content.Context;
import android.widget.Toast;

public class UtilsManager {

    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
