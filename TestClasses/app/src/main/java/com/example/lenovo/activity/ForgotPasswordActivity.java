package com.example.lenovo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lenovo.com.example.lenovo.fragment.ForgotPasswordFragment;
import com.example.lenovo.testclasses.R;

/**
 * Created by AlexandruOi on 5/5/2016.
 */
public class ForgotPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.forgot_password_container, new ForgotPasswordFragment())
                    .commit();
        }
    }
}