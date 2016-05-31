package com.example.lenovo.activity.admin;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.lenovo.com.example.lenovo.fragment.admin.QuestionAdminFragment;
import com.example.lenovo.com.example.lenovo.fragment.admin.StartTestAdminFragment;
import com.example.lenovo.testclasses.R;

/**
 * Created by AlexandruOi on 5/7/2016.
 */
public class QuestionsAdminActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_start_question);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.adminquestion_container, new QuestionAdminFragment())
                    .commit();
        }
    }
}