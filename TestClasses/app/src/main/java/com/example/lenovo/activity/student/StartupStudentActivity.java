package com.example.lenovo.activity.student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lenovo.com.example.lenovo.fragment.student.StartupStudentFragment;
import com.example.lenovo.testclasses.R;

/**
 * Created by AlexandruOi on 4/24/2016.
 */
public class StartupStudentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentstart);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.studentstartup_container, new StartupStudentFragment())
                    .commit();
        }
    }
}
