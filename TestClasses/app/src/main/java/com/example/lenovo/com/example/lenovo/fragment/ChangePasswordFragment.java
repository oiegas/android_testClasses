package com.example.lenovo.com.example.lenovo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lenovo.activity.LoginActivity;
import com.example.lenovo.domain.PasswordChanger;
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
public class ChangePasswordFragment extends BaseFragment {


  Apis apis;

    String email;

    @Bind(R.id.new_password_wrapper)
    TextInputLayout passwordWrapper;
    @Bind(R.id.confirm_password_wrapper)
    TextInputLayout confirmPasswordWrapper;
    @Bind(R.id.code_wrapper)
    TextInputLayout codeWrapper;
    @Bind(R.id.new_password)
    EditText newPasswordText;
    @Bind(R.id.confirm_password)
    EditText confirmPasswordText;
    @Bind(R.id.code)
    EditText codeText;
    @Bind(R.id.apply_changes_button)
    Button applyChangesButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password_fragment, container, false);
        ButterKnife.bind(this, view);
        Injector.init(getActivity());
        apis = Injector.getApi(Apis.class);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("Email");
        }
        return view;
    }

    @OnClick(R.id.apply_changes_button)
    public void handleChangePassword() {
        String password = passwordWrapper.getEditText().getText().toString();
        String confirmPassword = confirmPasswordWrapper.getEditText().getText().toString();
        String code = codeWrapper.getEditText().getText().toString();

        if (isChangePasswordFormValid(password, confirmPassword, code)) {
            PasswordChanger changer=new PasswordChanger();
            changer.setEmail(email);
            changer.setCode(code);
            changer.setPassword(password);
            changePassword(changer);
        }

    }

    private void changePassword(PasswordChanger changer) {
        showProgress();
       runCall(apis.changePassword(changer)).enqueue(new Callback<Void>() {
           @Override
           public void onResponse(Call<Void> call, Response<Void> response) {
               hideProgress();

               startActivity(new Intent(getActivity(), LoginActivity.class));
               getActivity().finish();
           }

           @Override
           public void onFailure(Call<Void> call, Throwable t) {
               hideProgress();
               ErrorHandler.showError(getActivity(), t);
           }
       });
    }

    public boolean isChangePasswordFormValid(String password, String confirmPassword, String code) {
        boolean validChangePasswordForm = true;

        if (TextUtils.isEmpty(password)) {
            passwordWrapper.setError(getString(R.string.invalid_password_message));
            validChangePasswordForm = false;
        } else {
            passwordWrapper.setErrorEnabled(false);
        }
        if (!Validators.isConfirmPasswordValid(password, confirmPassword)) {
            confirmPasswordWrapper.setError(getString(R.string.password_match_message));
            validChangePasswordForm = false;
        } else {
            confirmPasswordWrapper.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(code)) {
            codeWrapper.setError(getString(R.string.invalid_input_message));
            validChangePasswordForm = false;
        } else {
            codeWrapper.setErrorEnabled(false);
        }
        return validChangePasswordForm;
    }

    private void showProgress() {
        newPasswordText.setEnabled(false);
        confirmPasswordText.setEnabled(false);
        codeText.setEnabled(false);
        applyChangesButton.setEnabled(false);
    }

    private void hideProgress() {
        newPasswordText.setEnabled(true);
        confirmPasswordText.setEnabled(true);
        codeText.setEnabled(true);
        applyChangesButton.setEnabled(true);
    }
}
