package com.example.lenovo.activity.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lenovo.com.example.lenovo.fragment.admin.StartTestAdminFragment;
import com.example.lenovo.testclasses.R;

/**
 * Created by AlexandruOi on 5/6/2016.
 */
public class StartTestAdminActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_start_test);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.adminstarttest_container, new StartTestAdminFragment())
                    .commit();
        }
    }
}
