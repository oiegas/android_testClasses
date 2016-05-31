package com.example.lenovo.activity.student;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.lenovo.com.example.lenovo.fragment.student.TestStudentFragment;
import com.example.lenovo.testclasses.R;

/**
 * Created by AlexandruOi on 5/10/2016.
 */
public class StudentTestActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_take_test);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.student_test_container, new TestStudentFragment())
                    .commit();
        }
    }
}