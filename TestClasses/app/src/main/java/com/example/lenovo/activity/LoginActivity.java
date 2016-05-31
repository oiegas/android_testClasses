package com.example.lenovo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lenovo.com.example.lenovo.fragment.LoginFragment;
import com.example.lenovo.testclasses.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.login_container, new LoginFragment())
                    .commit();
        }
        setTitle(R.string.login_title);
    }
}
