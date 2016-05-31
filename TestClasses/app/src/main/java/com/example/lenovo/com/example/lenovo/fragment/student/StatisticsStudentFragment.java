package com.example.lenovo.com.example.lenovo.fragment.student;

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
import com.example.lenovo.domain.network.StudentGradesTokenResponse;
import com.example.lenovo.testclasses.Apis;
import com.example.lenovo.testclasses.ErrorHandler;
import com.example.lenovo.testclasses.GradeAdapter;
import com.example.lenovo.testclasses.Injector;
import com.example.lenovo.testclasses.R;
import com.example.lenovo.testclasses.UserStorage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AlexandruOi on 4/27/2016.
 */
public class StatisticsStudentFragment extends BaseFragment {

    private Apis apis;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.studentstatistics_listview)
    ListView studentStatisticsListView;
    GradeAdapter gradeAdapter;
    int userId;
    List<StudentGrade> grades;
    String token;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.studentstatistics_fragment, container, false);
        apis = Injector.getApi(Apis.class);
        userId = Injector.obtain(UserStorage.class).getUserId();
        token = Injector.obtain(UserStorage.class).getUserToken();
        ButterKnife.bind(this, view);
        Injector.init(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllStudentTestGrades();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getAllStudentTestGrades() {
        runCall(apis.getGradesOfStudent(userId,token)).enqueue(new Callback<StudentGradesTokenResponse>() {
            @Override
            public void onResponse(Call<StudentGradesTokenResponse> call, Response<StudentGradesTokenResponse> response) {
                List<StudentGrade> allGrades = new ArrayList<StudentGrade>();
                if (response.isSuccess()) {
                    StudentGradesTokenResponse tokenResponse=response.body();
                    if(tokenResponse.getResponse().isAvailability()==true) {
                        allGrades = tokenResponse.getGrades();
                        grades = allGrades;
                        gradeAdapter = new GradeAdapter(getActivity(), 0, allGrades);
                        studentStatisticsListView.setAdapter(gradeAdapter);
                    }
                    else{
                        Intent questionsStart = new Intent(getActivity(), LoginActivity.class);
                        startActivity(questionsStart);
                        getActivity().finish();
                    }
                } else
                    ErrorHandler.showError(getActivity(), response);
            }

            @Override
            public void onFailure(Call<StudentGradesTokenResponse> call, Throwable t) {
                ErrorHandler.showError(getActivity(), t);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
