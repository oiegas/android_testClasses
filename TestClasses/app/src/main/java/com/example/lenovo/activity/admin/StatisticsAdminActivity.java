package com.example.lenovo.activity.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.example.lenovo.com.example.lenovo.fragment.admin.StatisticsAdminFragment;
import com.example.lenovo.testclasses.R;

/**
 * Created by AlexandruOi on 4/27/2016.
 */
public class StatisticsAdminActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminstatistics);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.adminstatistics_container, new StatisticsAdminFragment())
                    .commit();
        }
    }
}
