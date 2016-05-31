package com.example.lenovo.com.example.lenovo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.lenovo.activity.ForgotPasswordActivity;
import com.example.lenovo.activity.MainActivity;
import com.example.lenovo.domain.User;
import com.example.lenovo.domain.UserLogin;
import com.example.lenovo.testclasses.Apis;
import com.example.lenovo.testclasses.ErrorHandler;
import com.example.lenovo.testclasses.Injector;
import com.example.lenovo.testclasses.R;
import com.example.lenovo.activity.RegisterAccountActivity;
import com.example.lenovo.activity.admin.StartupAdminActivity;
import com.example.lenovo.activity.student.StartupStudentActivity;
import com.example.lenovo.testclasses.UserStorage;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Madalina.Chis on 3/1/2016.
 */
public class LoginFragment extends BaseFragment {

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.login_button)
    Button loginButton;
    @Bind(R.id.email_wrapper)
    TextInputLayout emailWrapper;
    @Bind(R.id.email)
    EditText emailText;
    @Bind(R.id.password_wrapper)
    TextInputLayout passwordWrapper;
    @Bind(R.id.password)
    EditText passwordText;
    int userId;

    Apis loginApi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.init(getActivity());
        loginApi = Injector.getApi(Apis.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgress();
    }

    @OnClick(R.id.login_button)
    public void handleLogin() {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        if (TextUtils.isEmpty(email)) {

        }
        doLogin(email, password);

    }

    @OnClick(R.id.register_button)
    public void handleRegister() {
        Intent registerIntent = new Intent(getActivity(), RegisterAccountActivity.class);
        startActivity(registerIntent);
    }

    public void doLogin(String user, String pass) {
        showProgress();
        runCall(loginApi.getUserLogin(user, pass)).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.isSuccess()) {
                    if (response.body() != null) {
                        UserLogin user = response.body();
                        Injector.obtain(UserStorage.class).storeUserId(user.getUserId());
                        Injector.obtain(UserStorage.class).storeUserToken(user.getToken());
                        userId = user.getUserId();
                        hideProgress();
                        if (user.getRole().getName().equals("ROLE_ADMIN")) {
                            Intent startupAdminActivityIntent = new Intent(getActivity(), StartupAdminActivity.class);
                            startActivity(startupAdminActivityIntent);
                            getActivity().finish();
                        } else if (user.getRole().getName().equals("ROLE_STUDENT")) {
                            Intent startupStudentActivityIntent = new Intent(getActivity(), StartupStudentActivity.class);
                            startActivity(startupStudentActivityIntent);
                            getActivity().finish();
                        }
                    } else {
                        emailWrapper.setError("Please introduce a valid username");
                        passwordWrapper.setError("Please introduce a valid password");
                        hideProgress();
                    }

                } else {
                    ErrorHandler.showError(getActivity(), response);
                    hideProgress();
                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                hideProgress();
                ErrorHandler.showError(getActivity(), t);
            }
        });
    }

    @OnClick(R.id.password_forgot)
    public void resetPassword() {
        Intent resetPasswordIntent = new Intent(getActivity(), ForgotPasswordActivity.class);
        startActivity(resetPasswordIntent);
    }


    private void showProgress() {
        loginButton.setEnabled(false);
        emailText.setEnabled(false);
        passwordText.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        loginButton.setEnabled(true);
        emailText.setEnabled(true);
        passwordText.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }


}
