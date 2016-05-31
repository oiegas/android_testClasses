package com.example.lenovo.testclasses;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by Lenovo on 4/12/2016.
 */
public class ErrorHandler {

    public static void showError(Context context, Response response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        String errorMessage;
        try {
            String errorString = response.errorBody().string();
            errorMessage = gsonBuilder.create().fromJson(errorString, ApiError.class).getErrorMessage();
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage = context.getString(R.string.unknown_api_error);
        }
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
    }

    public static void showError(Context context, Throwable t) {
        String errorString = t.getMessage();
        Toast.makeText(context, errorString, Toast.LENGTH_LONG).show();
    }
}
