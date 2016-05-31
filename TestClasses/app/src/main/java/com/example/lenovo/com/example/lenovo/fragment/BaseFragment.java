package com.example.lenovo.com.example.lenovo.fragment;

import android.support.v4.app.Fragment;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;

public class BaseFragment extends Fragment {

    private Set<Call> runningCalls = Collections.synchronizedSet(new HashSet<Call>());

    public <T> Call<T> runCall(Call<T> call) {
        runningCalls.add(call);
        return call;
    }

    @Override
    public void onStop() {
        super.onStop();
        stopRunningCalls();
    }

    private void stopRunningCalls() {
        for (Call call : runningCalls) {
            call.cancel();
        }
    }
}
