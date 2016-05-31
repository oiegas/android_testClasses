package com.example.lenovo.com.example.lenovo.fragment.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.activity.LoginActivity;
import com.example.lenovo.activity.admin.StartupAdminActivity;
import com.example.lenovo.activity.admin.TestViewAdminActivity;
import com.example.lenovo.com.example.lenovo.fragment.BaseFragment;
import com.example.lenovo.domain.Question;
import com.example.lenovo.domain.QuestionAndroid;
import com.example.lenovo.domain.Test;
import com.example.lenovo.domain.TokenResponse;
import com.example.lenovo.domain.network.ListQuestionsTokenResponse;
import com.example.lenovo.domain.network.QuestionToken;
import com.example.lenovo.domain.network.TestToken;
import com.example.lenovo.testclasses.Apis;
import com.example.lenovo.testclasses.ErrorHandler;
import com.example.lenovo.testclasses.Injector;
import com.example.lenovo.testclasses.NonSwipebaleViewPager;
import com.example.lenovo.testclasses.QuestionAdapter;
import com.example.lenovo.testclasses.R;
import com.example.lenovo.testclasses.UserStorage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AlexandruOi on 5/7/2016.
 */
public class QuestionAdminFragment extends BaseFragment {

    @Bind(R.id.progress_indicator)
    ProgressBar progressBar;
    @Bind(R.id.question_name)
    TextView questionName;
    @Bind(R.id.select_question)
    NonSwipebaleViewPager selectQuestion;
    @Bind(R.id.next_question_button)
    Button nextQuestion;
    @Bind(R.id.stop_test_button)
    Button finishTest;
    @Bind(R.id.pager_layout)
    RelativeLayout pagerLayout;
    List<QuestionAndroid> questions = new ArrayList<QuestionAndroid>();
    Apis apis;
    int testId;
    int userId;
    String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_question_fragment, container, false);
        ButterKnife.bind(this, view);
        userId = Injector.obtain(UserStorage.class).getUserId();
        token = Injector.obtain(UserStorage.class).getUserToken();
        Injector.init(getActivity());
        apis = Injector.getApi(Apis.class);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            testId = extras.getInt("testId");
        }
        getQuestions();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void updateView() {
        progressBar.setVisibility(View.GONE);
        pagerLayout.setVisibility(View.VISIBLE);
        selectQuestion.setAdapter(new QuestionAdapter(getActivity(), questions));
        selectQuestion.setHorizontalScrollBarEnabled(false);
        selectQuestion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {

                } else {
                    //The current page is the last one
                    if (position == questions.size() - 1) {
                        finishTest.setEnabled(true);
                        nextQuestion.setEnabled(false);
                    } else {
                        finishTest.setEnabled(false);
                        nextQuestion.setEnabled(true);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.next_question_button)
    public void onClickButton() {
        QuestionAndroid question = questions.get(selectQuestion.getCurrentItem() + 1);
        Question newQuestion = new Question();
        newQuestion.setQuestionId(question.getQuestionId());
        QuestionToken questionToken = new QuestionToken();
        questionToken.setQuestion(newQuestion);
        questionToken.setToken(token);
        runCall(apis.makeQuestionAvailable(questionToken)).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccess()) {
                    TokenResponse tokenResponse = response.body();
                    if (tokenResponse.isAvailability()) {
                        selectQuestion.setCurrentItem(selectQuestion.getCurrentItem() + 1);
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

    @OnClick(R.id.stop_test_button)
    public void onClickStopButton() {
        Test test = new Test();
        test.setTestId(testId);
        TestToken testToken = new TestToken();
        testToken.setTest(test);
        testToken.setToken(token);
        runCall(apis.makeTestCloseAndUnavailable(testToken)).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccess()) {
                    TokenResponse tokenResponse = response.body();
                    if (tokenResponse.isAvailability()) {
                        Intent startupAdmin = new Intent(getActivity(), StartupAdminActivity.class);
                        startActivity(startupAdmin);
                    } else {
                        Intent startupAdmin = new Intent(getActivity(), LoginActivity.class);
                        startActivity(startupAdmin);
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

            }
        });
    }


    public void getQuestions() {
        runCall(apis.getQuestionsWithTestId(testId, token)).enqueue(new Callback<ListQuestionsTokenResponse>() {
            @Override
            public void onResponse(Call<ListQuestionsTokenResponse> call, Response<ListQuestionsTokenResponse> response) {
                if (response.isSuccess()) {
                    ListQuestionsTokenResponse tokenResponse = response.body();
                    if (tokenResponse.getTokenResponse().isAvailability()) {
                        questions = tokenResponse.getQuestions();
                        updateView();
                    } else {
                        Intent loginActivity = new Intent(getActivity(), LoginActivity.class);
                        startActivity(loginActivity);
                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListQuestionsTokenResponse> call, Throwable t) {
                ErrorHandler.showError(getActivity(), t);
            }
        });
    }

}
