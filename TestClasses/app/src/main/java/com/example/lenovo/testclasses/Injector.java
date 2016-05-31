package com.example.lenovo.testclasses;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ovidiu.latcu on 3/1/2016.
 */
public class Injector {

    private static Map<Class, Object> objectMap = new HashMap<>();

    public static void init(Context context) {

        UserStorage userStorage = new UserStorage(context);
        objectMap.put(UserStorage.class, userStorage);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        objectMap.put(OkHttpClient.class, okHttpClient);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .callbackExecutor(new UiThreadExecutor())
                .build();
        objectMap.put(Retrofit.class, retrofit);
    }

    public static <T> T obtain(Class<T> type) {
        return (T) objectMap.get(type);
    }

    public static <T> T getApi(Class<T> apiType) {
        Retrofit retrofit = (Retrofit) objectMap.get(Retrofit.class);
        return retrofit.create(apiType);
    }

    private static class UiThreadExecutor implements Executor {

        private final Handler uiHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            uiHandler.post(command);
        }
    }
}
