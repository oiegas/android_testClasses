package com.example.lenovo.com.example.lenovo.fragment.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.activity.LoginActivity;
import com.example.lenovo.activity.admin.QuestionsAdminActivity;
import com.example.lenovo.activity.admin.TestViewAdminActivity;
import com.example.lenovo.com.example.lenovo.fragment.BaseFragment;
import com.example.lenovo.domain.Question;
import com.example.lenovo.domain.QuestionAndroid;
import com.example.lenovo.domain.Test;
import com.example.lenovo.domain.TokenResponse;
import com.example.lenovo.domain.network.ListQuestionsTokenResponse;
import com.example.lenovo.domain.network.SingleTestTokenResponse;
import com.example.lenovo.domain.network.TestToken;
import com.example.lenovo.testclasses.Apis;
import com.example.lenovo.testclasses.ErrorHandler;
import com.example.lenovo.testclasses.Injector;
import com.example.lenovo.testclasses.R;
import com.example.lenovo.testclasses.UserStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AlexandruOi on 5/6/2016.
 */
public class StartTestAdminFragment extends BaseFragment {
    @Bind(R.id.test_name)
    TextView testName;
    @Bind(R.id.test_classname)
    TextView testClassName;
    @Bind(R.id.test_date_start)
    TextView testDate;
    @Bind(R.id.test_opened)
    TextView isOpened;
    @Bind(R.id.test_numberOfQuestions)
    TextView numberOfQuestions;
    @Bind(R.id.test_firstQuestion)
    TextView firstQuestion;
    int userId;
    private Apis apis;
    String token;
    int testId;
    String className;
    Test test;
    List<QuestionAndroid> questions;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_start_test_fragment, container, false);
        userId = Injector.obtain(UserStorage.class).getUserId();
        token = Injector.obtain(UserStorage.class).getUserToken();
        ButterKnife.bind(this, view);
        Injector.init(getActivity());
        apis = Injector.getApi(Apis.class);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            testId = extras.getInt("TestID");
            test = extras.getParcelable("Test");
            className=extras.getString("className");
        }
        Log.d("ACESTA E ID_UL", String.valueOf(testId));
        getTest(test);
        Log.d("NUME", test.getName());
        getQuestions();
        return view;
    }


    public void getTest(Test test) {

        testName.setText(test.getName());
        testClassName.setText("For class: " + className);
        String pattern = "EEE, d MMM yyyy HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = test.getStartDate();
        testDate.setText("Date: " + format.format(date));
        if (test.isOpened()) {
            isOpened.setText("Status: Opened");
        } else
            isOpened.setText("Status: Closed");

    }


    public void getQuestions() {
        runCall(apis.getQuestionsWithTestId(testId, token)).enqueue(new Callback<ListQuestionsTokenResponse>() {
            @Override
            public void onResponse(Call<ListQuestionsTokenResponse> call, Response<ListQuestionsTokenResponse> response) {
                if (response.isSuccess()) {
                    questions = new LinkedList<QuestionAndroid>();
                    ListQuestionsTokenResponse tokenResponse = response.body();
                    if (tokenResponse.getTokenResponse().isAvailability()) {
                        questions = tokenResponse.getQuestions();
                        Log.d("NUMBER OF QUESTIONS", String.valueOf(questions.size()));
                        numberOfQuestions.setText("Number of questions:" + String.valueOf(questions.size()));
                        firstQuestion.setText("First question:" + questions.get(0).getQuestion());
                    }
                } else {
                    Intent loginActivity = new Intent(getActivity(), LoginActivity.class);
                    startActivity(loginActivity);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<ListQuestionsTokenResponse> call, Throwable t) {

            }
        });
    }

    public void makeAvailable() {
        Test test = new Test();
        test.setTestId(testId);
        TestToken testToken = new TestToken();
        testToken.setTest(test);
        testToken.setToken(token);

        runCall(apis.makeTestAvailable(testToken)).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccess()) {
                    TokenResponse tokenResponse = response.body();
                    if (tokenResponse.isAvailability()) {
                        Intent questionsStart = new Intent(getActivity(), QuestionsAdminActivity.class);
                        questionsStart.putExtra("testId", testId);
                        startActivity(questionsStart);
                        getActivity().finish();
                    } else {
                        Intent questionsStart = new Intent(getActivity(), LoginActivity.class);
                        startActivity(questionsStart);
                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.start_test_button)
    public void handleStartButton() {
        runCall(apis.checkIfAvailable(token)).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccess()) {
                    Intent registerIntent;
                    TokenResponse token = new TokenResponse();
                    token = response.body();
                    if (token.isAvailability() == true) {
                        makeAvailable();
                    } else {
                        registerIntent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(registerIntent);
                    }

                } else {
                    ErrorHandler.showError(getActivity(), response);
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
