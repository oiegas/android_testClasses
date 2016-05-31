package com.example.lenovo.testclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by AlexandruOi on 4/25/2016.
 */
public class UserStorage {

    private static final String USER_ID_KEY = "userId";
    private static final String USER_TOKEN = "userToken";
    SharedPreferences sharedPreferences;

    public UserStorage(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getUserId() {
        return sharedPreferences.getInt(USER_ID_KEY, 0);
    }

    public String getUserToken() { return sharedPreferences.getString(USER_TOKEN,"NoToken");}
    public void storeUserId(int userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    public void storeUserToken(String token){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }
}
