package com.example.lenovo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lenovo.com.example.lenovo.fragment.RegisterAccountFragment;
import com.example.lenovo.testclasses.R;


public class RegisterAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.register_container, new RegisterAccountFragment())
                    .commit();
        }
    }
}
