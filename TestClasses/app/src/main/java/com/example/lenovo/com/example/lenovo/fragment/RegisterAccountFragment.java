package com.example.lenovo.com.example.lenovo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lenovo.domain.Clas;
import com.example.lenovo.domain.User;
import com.example.lenovo.testclasses.Apis;
import com.example.lenovo.testclasses.ErrorHandler;
import com.example.lenovo.testclasses.Injector;
import com.example.lenovo.activity.LoginActivity;
import com.example.lenovo.testclasses.R;
import com.example.lenovo.testclasses.Validators;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Madalina.Chis on 3/1/2016.
 */
public class RegisterAccountFragment extends BaseFragment {

    private Apis apis;
    private int selectedClassPosition;
    private Clas selectedClass;
    private static List<Clas> classes;

    @Bind(R.id.name_wrapper)
    TextInputLayout nameWrapper;
    @Bind(R.id.username_wrapper)
    TextInputLayout usernameWrapper;
    @Bind(R.id.email_wrapper)
    TextInputLayout emailWrapper;
    @Bind(R.id.password_wrapper)
    TextInputLayout newPasswordWrapper;
    @Bind(R.id.confirm_password_wrapper)
    TextInputLayout confirmPasswordWrapper;
    @Bind(R.id.sign_up_button)
    Button signUpButton;
    @Bind(R.id.name)
    EditText nameText;
    @Bind(R.id.username)
    EditText usernameText;
    @Bind(R.id.email)
    EditText emailText;
    @Bind(R.id.password)
    EditText passwordText;
    @Bind(R.id.confirm_password)
    EditText confirmPasswordText;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.classes_spinner)
    Spinner classesSpinner;
    ArrayAdapter<Clas> classesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        ButterKnife.bind(this, view);
        Injector.init(getActivity());
        apis = Injector.getApi(Apis.class);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getClasses();
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgress();
    }

    @OnClick(R.id.sign_up_button)
    public void handleSignup() {
        String name = nameWrapper.getEditText().getText().toString();
        String email = emailWrapper.getEditText().getText().toString();
        String password = newPasswordWrapper.getEditText().getText().toString();
        String confirmPassword = confirmPasswordWrapper.getEditText().getText().toString();
        String username = usernameWrapper.getEditText().getText().toString();

        User user = new User().create()
                .email(email)
                .password(password)
                .username(username)
                .name(name);

        user.setClassForUser(selectedClass);

        doRegister(user);


    }

    @OnClick(R.id.link_login)
    public void handleLinkToLogin() {
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
    }

    public void getClasses() {
        runCall(apis.getClasses()).enqueue(new Callback<List<Clas>>() {
            @Override
            public void onResponse(Call<List<Clas>> call, Response<List<Clas>> response) {
                if (response.isSuccess()) {
                    classes = response.body();
                    for (Clas c : classes) {
                        Log.d("clasa", c.getName());
                    }
                    classesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, classes);
                    classesSpinner.setAdapter(classesAdapter);
                    classesSpinner.setOnItemSelectedListener(new ClassesChangeListener());
                } else {
                    ErrorHandler.showError(getActivity(), response);
                }
            }

            @Override
            public void onFailure(Call<List<Clas>> call, Throwable t) {
                ErrorHandler.showError(getActivity(), t);
            }
        });

    }

    private class ClassesChangeListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedClassPosition = position;
            selectedClass = classesAdapter.getItem(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    public boolean isRegistrationFormValid(String name, String email, String password, String confirmPassword) {
        boolean validRegistrationForm = true;

        if (TextUtils.isEmpty(name)) {
            nameWrapper.setError(getString(R.string.invalid_input_message));
            validRegistrationForm = false;
        } else {
            nameWrapper.setErrorEnabled(false);
        }
        if (!Validators.isEmailValid(email)) {
            emailWrapper.setError(getString(R.string.invalid_email_message));
            validRegistrationForm = false;
        } else {
            emailWrapper.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(password)) {
            newPasswordWrapper.setError(getString(R.string.invalid_password_message));
            validRegistrationForm = false;
        } else {
            newPasswordWrapper.setErrorEnabled(false);
        }
        if (!Validators.isConfirmPasswordValid(password, confirmPassword)) {
            confirmPasswordWrapper.setError(getString(R.string.password_match_message));
            validRegistrationForm = false;
        } else {
            confirmPasswordWrapper.setErrorEnabled(false);
        }
        return validRegistrationForm;
    }

    public void doRegister(final User user) {
        showProgress();
        runCall(apis.createUser(user)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccess()) {
                    hideProgress();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                } else {
                    ErrorHandler.showError(getActivity(), response);
                    hideProgress();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                hideProgress();
                ErrorHandler.showError(getActivity(), t);
            }
        });
    }

    private void showRegisterError(String body) {
        hideProgress();
        Toast.makeText(getActivity(), body, Toast.LENGTH_LONG).show();
    }

    private void showProgress() {
        signUpButton.setEnabled(false);
        nameText.setEnabled(false);
        emailText.setEnabled(false);
        passwordText.setEnabled(false);
        confirmPasswordText.setEnabled(false);
        progressBar.setEnabled(false);
    }

    private void hideProgress() {
        signUpButton.setEnabled(true);
        nameText.setEnabled(true);
        emailText.setEnabled(true);
        passwordText.setEnabled(true);
        confirmPasswordText.setEnabled(true);
        progressBar.setEnabled(true);
    }
}
