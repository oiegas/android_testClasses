package com.example.lenovo.com.example.lenovo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.activity.ChangePasswordActivity;
import com.example.lenovo.activity.admin.StartupAdminActivity;
import com.example.lenovo.domain.User;
import com.example.lenovo.testclasses.Apis;
import com.example.lenovo.testclasses.ErrorHandler;
import com.example.lenovo.testclasses.Injector;
import com.example.lenovo.testclasses.R;
import com.example.lenovo.testclasses.Validators;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AlexandruOi on 5/5/2016.
 */
public class ForgotPasswordFragment extends BaseFragment {

    public static final String EMAIL_KEY = "email";

    //@Inject
    Apis apis;

    @Bind(R.id.email_wrapper)
    TextInputLayout emailWrapper;
    @Bind(R.id.email)
    EditText emailText;
    @Bind(R.id.get_code_button)
    Button sendEmailButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password_fragment, container, false);
        ButterKnife.bind(this, view);
        Injector.init(getActivity());
        apis = Injector.getApi(Apis.class);
        return view;
    }

    @OnClick(R.id.get_code_button)
    public void sendMail() {
        final String emailString = emailWrapper.getEditText().getText().toString();
        if (!Validators.isEmailValid(emailString)) {
            emailWrapper.setError(getString(R.string.invalid_email_message));
        } else {
            emailWrapper.setErrorEnabled(false);
            showProgress();
            User user=new User();
            user.setUserId(1);
            user.setEmail(emailString);
            runCall(apis.sendEmail(user)).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    hideProgress();
                    if (response.isSuccess()) {
                        Intent changePasswordActivityIntent = new Intent(getActivity(), ChangePasswordActivity.class);
                        changePasswordActivityIntent.putExtra("Email", emailString);
                        startActivity(changePasswordActivityIntent);
                        getActivity().finish();
                        Log.d("SUCCESS","");
                    }
                    else {
                        ErrorHandler.showError(getActivity(), response);
                    }

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    hideProgress();
                    ErrorHandler.showError(getActivity(), t);
                }
            });
            }
    }


    private void showProgress() {
        emailText.setEnabled(false);
        sendEmailButton.setEnabled(false);
    }

    private void hideProgress() {
        emailText.setEnabled(true);
        sendEmailButton.setEnabled(true);
    }
}