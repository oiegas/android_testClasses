package com.example.lenovo.com.example.lenovo.fragment.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lenovo.activity.LoginActivity;
import com.example.lenovo.com.example.lenovo.fragment.BaseFragment;
import com.example.lenovo.domain.StudentGrade;
import com.example.lenovo.domain.Test;
import com.example.lenovo.domain.TokenResponse;
import com.example.lenovo.domain.network.StudentGradesTokenResponse;
import com.example.lenovo.testclasses.Apis;
import com.example.lenovo.testclasses.ErrorHandler;
import com.example.lenovo.testclasses.GradeAdapter;
import com.example.lenovo.testclasses.Injector;
import com.example.lenovo.testclasses.R;
import com.example.lenovo.testclasses.TestAdapter;
import com.example.lenovo.testclasses.TestGradeAdapter;
import com.example.lenovo.testclasses.UserStorage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AlexandruOi on 5/9/2016.
 */
public class TestGradesViewAdminFragment extends BaseFragment {

    Apis apis;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.grades_listview)
    ListView gradesListView;
    TestGradeAdapter gradeAdapter;
    int userId;
    List<StudentGrade> grades;
    int testId;
    String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_view_grades_fragment, container, false);
        apis = Injector.getApi(Apis.class);
        userId = Injector.obtain(UserStorage.class).getUserId();
        token = Injector.obtain(UserStorage.class).getUserToken();
        ButterKnife.bind(this, view);
        Injector.init(getActivity());
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            testId = extras.getInt("TestID");
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getGrades();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void getGrades() {
        runCall(apis.getGradesOfTest(testId, token)).enqueue(new Callback<StudentGradesTokenResponse>() {
            @Override
            public void onResponse(Call<StudentGradesTokenResponse> call, Response<StudentGradesTokenResponse> response) {
                List<StudentGrade> allGrades = new ArrayList<StudentGrade>();
                if (response.isSuccess()) {
                    StudentGradesTokenResponse gradesTokenResponse = response.body();
                    if (gradesTokenResponse.getResponse().isAvailability() == true) {
                        allGrades = gradesTokenResponse.getGrades();
                        grades = allGrades;
                        gradeAdapter = new TestGradeAdapter(getActivity(), 0, allGrades);
                        gradesListView.setAdapter(gradeAdapter);
                    } else {
                        Intent questionsStart = new Intent(getActivity(), LoginActivity.class);
                        startActivity(questionsStart);
                        getActivity().finish();
                    }
                } else
                    ErrorHandler.showError(getActivity(), response);
            }

            @Override
            public void onFailure(Call<StudentGradesTokenResponse> call, Throwable t) {

            }
        });
    }
}