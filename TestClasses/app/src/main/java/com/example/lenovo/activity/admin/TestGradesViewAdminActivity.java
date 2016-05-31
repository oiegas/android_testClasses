package com.example.lenovo.activity.admin;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.lenovo.com.example.lenovo.fragment.admin.QuestionAdminFragment;
import com.example.lenovo.com.example.lenovo.fragment.admin.TestGradesViewAdminFragment;
import com.example.lenovo.testclasses.R;

/**
 * Created by AlexandruOi on 5/9/2016.
 */
public class TestGradesViewAdminActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_grades);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.admin_view_grades_container, new TestGradesViewAdminFragment())
                    .commit();
        }
    }
}