package com.example.lenovo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lenovo.com.example.lenovo.fragment.ChangePasswordFragment;
import com.example.lenovo.com.example.lenovo.fragment.RegisterAccountFragment;
import com.example.lenovo.testclasses.R;

/**
 * Created by AlexandruOi on 5/5/2016.
 */
public class ChangePasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.changePassword_container, new ChangePasswordFragment())
                    .commit();
        }
    }
}