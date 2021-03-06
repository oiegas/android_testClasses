package com.example.lenovo.com.example.lenovo.fragment.student;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lenovo.activity.LoginActivity;
import com.example.lenovo.activity.MainActivity;
import com.example.lenovo.activity.admin.StatisticsAdminActivity;
import com.example.lenovo.activity.admin.TestViewAdminActivity;
import com.example.lenovo.activity.student.StatisticsStudentActivity;
import com.example.lenovo.activity.student.StudentTestActivity;
import com.example.lenovo.com.example.lenovo.fragment.BaseFragment;
import com.example.lenovo.domain.Test;
import com.example.lenovo.domain.TokenResponse;
import com.example.lenovo.domain.UserLogin;
import com.example.lenovo.domain.network.SingleTestTokenResponse;
import com.example.lenovo.testclasses.Apis;
import com.example.lenovo.testclasses.ErrorHandler;
import com.example.lenovo.testclasses.Injector;
import com.example.lenovo.testclasses.R;
import com.example.lenovo.testclasses.UserStorage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AlexandruOi on 4/24/2016.
 */
public class StartupStudentFragment extends BaseFragment {

    private Apis apis;
    @Bind(R.id.test_button)
    Button testButton;
    @Bind(R.id.statistics_button)
    Button statisticsButton;
    @Bind(R.id.logout_button)
    Button logoutButton;
    String token;
    int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.studentstart_fragment, container, false);
        token = Injector.obtain(UserStorage.class).getUserToken();
        userId = Injector.obtain(UserStorage.class).getUserId();
        ButterKnife.bind(this, view);
        Injector.init(getActivity());
        apis = Injector.getApi(Apis.class);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.test_button)
    public void handleTest() {
        runCall(apis.getAvailbleTestForUser(userId, token)).enqueue(new Callback<SingleTestTokenResponse>() {
            @Override
            public void onResponse(Call<SingleTestTokenResponse> call, Response<SingleTestTokenResponse> response) {
                if (response.isSuccess()) {
                    SingleTestTokenResponse testTokenResponse = response.body();
                    if (testTokenResponse.getTokenResponse().isAvailability()) {
                        Test test = testTokenResponse.getTest();
                        if (test != null) {
                            Intent registerIntent = new Intent(getActivity(), StudentTestActivity.class);
                            registerIntent.putExtra("testId", test.getTestId());
                            startActivity(registerIntent);
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setMessage("There is no test available!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    } else {
                        Intent loginActivity = new Intent(getActivity(), LoginActivity.class);
                        startActivity(loginActivity);
                        getActivity().finish();
                    }
                } else {
                    ErrorHandler.showError(getActivity(), response);
                }
            }

            @Override
            public void onFailure(Call<SingleTestTokenResponse> call, Throwable t) {
                ErrorHandler.showError(getActivity(), t);
            }
        });


    }

    @OnClick(R.id.statistics_button)
    public void handleStatistics() {
        runCall(apis.checkIfAvailable(token)).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccess()) {
                    Intent registerIntent;
                    Log.d("RASPUNS", response.body().toString());
                    TokenResponse token = new TokenResponse();
                    token = response.body();
                    if (token.isAvailability() == true) {
                        Log.d("MUIE STEAUA", "SDA");
                        registerIntent = new Intent(getActivity(), StatisticsStudentActivity.class);

                    } else {
                        registerIntent = new Intent(getActivity(), LoginActivity.class);
                    }
                    startActivity(registerIntent);
                } else {
                    ErrorHandler.showError(getActivity(), response);
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.logout_button)
    public void handleLogout() {
        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(userId);
        userLogin.setToken(token);
        runCall(apis.logout(userLogin)).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccess()) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

            }
        });


    }


    @Override
    public void onStop() {
        super.onStop();
    }
}