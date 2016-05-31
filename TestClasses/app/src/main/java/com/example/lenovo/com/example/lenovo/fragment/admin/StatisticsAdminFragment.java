package com.example.lenovo.com.example.lenovo.fragment.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lenovo.activity.LoginActivity;
import com.example.lenovo.activity.admin.StartTestAdminActivity;
import com.example.lenovo.activity.admin.TestGradesViewAdminActivity;
import com.example.lenovo.com.example.lenovo.fragment.BaseFragment;
import com.example.lenovo.domain.Test;
import com.example.lenovo.domain.network.TestTokenResponse;
import com.example.lenovo.testclasses.Apis;
import com.example.lenovo.testclasses.ErrorHandler;
import com.example.lenovo.testclasses.Injector;
import com.example.lenovo.testclasses.R;
import com.example.lenovo.testclasses.TestAdapter;
import com.example.lenovo.testclasses.UserStorage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AlexandruOi on 4/27/2016.
 */
public class StatisticsAdminFragment extends BaseFragment {

    Apis apis;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.statisticstest_listview)
    ListView statisticsTestListView;
    TestAdapter testsAdapter;
    int userId;
    List<Test> tests;
    String token;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adminstatistics_fragment, container, false);
        apis = Injector.getApi(Apis.class);
        userId = Injector.obtain(UserStorage.class).getUserId();
        token = Injector.obtain(UserStorage.class).getUserToken();
        ButterKnife.bind(this, view);
        Injector.init(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllTestsOfUser();
    }

    private void getAllTestsOfUser() {
        runCall(apis.getAllTestsOfUser(userId,token)).enqueue(new Callback<TestTokenResponse>() {
            @Override
            public void onResponse(Call<TestTokenResponse> call, Response<TestTokenResponse> response) {
                List<Test> allTests;
                Log.d("IDDDDDDDDDDDDDD", Integer.toString(userId));
                if(response.isSuccess()) {
                    TestTokenResponse testTokenResponse=response.body();
                            if(testTokenResponse.getTokenResponse().isAvailability()) {
                                allTests = testTokenResponse.getTests();
                                tests = allTests;
                                for (Test test : allTests) {
                                    Log.d("tests", test.getName());
                                    Log.d("AVAILABLE", String.valueOf(test.isAvailable()));
                                }
                                testsAdapter = new TestAdapter(getActivity(), 0, allTests);
                                statisticsTestListView.setAdapter(testsAdapter);
                                statisticsTestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent startTestActivity = new Intent(getActivity(), TestGradesViewAdminActivity.class);
                                        startTestActivity.putExtra("TestID", tests.get(position).getTestId());
                                        startActivity(startTestActivity);
                                    }
                                });
                            }
                    else{
                                Intent loginActivity = new Intent(getActivity(), LoginActivity.class);
                                startActivity(loginActivity);
                                getActivity().finish();

                            }
                }
                else
                    ErrorHandler.showError(getActivity(),response);
            }

            @Override
            public void onFailure(Call<TestTokenResponse> call, Throwable t) {
                ErrorHandler.showError(getActivity(), t);
            }
        });
        Log.d("XXXXXXXXX", "9999");

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
