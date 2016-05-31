package com.example.lenovo.activity.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lenovo.com.example.lenovo.fragment.admin.StartupAdminFragment;
import com.example.lenovo.testclasses.R;

/**
 * Created by AlexandruOi on 4/24/2016.
 */
public class StartupAdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminstart);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.adminstartup_container, new StartupAdminFragment())
                    .commit();
        }
    }
}
