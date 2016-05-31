package com.example.lenovo.com.example.lenovo.fragment.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.lenovo.com.example.lenovo.fragment.BaseFragment;
import com.example.lenovo.domain.Test;
import com.example.lenovo.domain.TokenResponse;
import com.example.lenovo.domain.network.SingleTestTokenResponse;
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
 * Created by AlexandruOi on 4/25/2016.
 */
public class TestViewAdminFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    Apis apis;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.test_listview)
    ListView testsListView;
    TestAdapter testsAdapter;
    int userId;
    String token;
    List<Test> tests;
    Test test;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.admintest_fragment, container, false);
        apis = Injector.getApi(Apis.class);
        userId = Injector.obtain(UserStorage.class).getUserId();
        token = Injector.obtain(UserStorage.class).getUserToken();
        ButterKnife.bind(this, rootView);
        Injector.init(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllTestsOfUser();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void getAllTestsOfUser() {
        runCall(apis.getAllTestsOfUser(userId, token)).enqueue(new Callback<TestTokenResponse>() {
            @Override
            public void onResponse(Call<TestTokenResponse> call, Response<TestTokenResponse> response) {
                List<Test> allTests = new ArrayList<Test>();
                if (response.isSuccess()) {
                    final TestTokenResponse testTokenResponse = response.body();
                    if (testTokenResponse.getTokenResponse().isAvailability()) {
                        allTests = testTokenResponse.getTests();
                        tests = allTests;
                        testsAdapter = new TestAdapter(getActivity(), 0, allTests);
                        testsListView.setAdapter(testsAdapter);
                        testsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                final Intent startTestActivity = new Intent(getActivity(), StartTestAdminActivity.class);
                                startTestActivity.putExtra("TestID", tests.get(position).getTestId());
                                runCall(apis.getTestWithId(tests.get(position).getTestId(), token)).enqueue(new Callback<SingleTestTokenResponse>() {
                                    @Override
                                    public void onResponse(Call<SingleTestTokenResponse> call, Response<SingleTestTokenResponse> response) {
                                        if (response.isSuccess()) {
                                            SingleTestTokenResponse tokenResponse = response.body();
                                            if (tokenResponse.getTokenResponse().isAvailability()) {
                                                test = tokenResponse.getTest();
                                                if (test.isOpened() == false) {
                                                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                                    alertDialog.setMessage("The test is closed!");
                                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                    alertDialog.show();
                                                } else {
                                                    startTestActivity.putExtra("Test", test);
                                                    startTestActivity.putExtra("className", test.getClassForTest().getName());
                                                    startActivity(startTestActivity);
                                                }
                                            } else {
                                                Intent loginActivity = new Intent(getActivity(), LoginActivity.class);
                                                startActivity(loginActivity);
                                                getActivity().finish();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SingleTestTokenResponse> call, Throwable t) {

                                    }
                                });

                            }
                        });
                    } else {
                        Intent loginActivity = new Intent(getActivity(), LoginActivity.class);
                        startActivity(loginActivity);
                        getActivity().finish();
                    }
                } else
                    ErrorHandler.showError(getActivity(), response);
            }

            @Override
            public void onFailure(Call<TestTokenResponse> call, Throwable t) {
                ErrorHandler.showError(getActivity(), t);
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
