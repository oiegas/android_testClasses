package com.example.lenovo.com.example.lenovo.fragment.student;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.activity.LoginActivity;
import com.example.lenovo.activity.admin.QuestionsAdminActivity;
import com.example.lenovo.activity.student.StartupStudentActivity;
import com.example.lenovo.com.example.lenovo.fragment.BaseFragment;
import com.example.lenovo.domain.AnswerAndroid;
import com.example.lenovo.domain.Question;
import com.example.lenovo.domain.QuestionAndroid;
import com.example.lenovo.domain.QuestionResponse;
import com.example.lenovo.domain.StudentAnswerResponse;
import com.example.lenovo.domain.TestResponse;
import com.example.lenovo.domain.TokenResponse;
import com.example.lenovo.domain.UserLogin;
import com.example.lenovo.domain.network.ListQuestionsTokenResponse;
import com.example.lenovo.domain.network.StudentAnswerToken;
import com.example.lenovo.testclasses.Apis;
import com.example.lenovo.testclasses.ErrorHandler;
import com.example.lenovo.testclasses.Injector;
import com.example.lenovo.testclasses.NonSwipebaleViewPager;
import com.example.lenovo.testclasses.QuestionAdapter;
import com.example.lenovo.testclasses.QuestionStudentAdapter;
import com.example.lenovo.testclasses.R;
import com.example.lenovo.testclasses.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AlexandruOi on 5/10/2016.
 */
public class TestStudentFragment extends BaseFragment {

    @Bind(R.id.progress_indicator)
    ProgressBar progressBar;
    @Bind(R.id.question_name)
    TextView questionName;
    @Bind(R.id.answer_question)
    NonSwipebaleViewPager selectQuestion;
    @Bind(R.id.submit_answer_button)
    Button submitAnswer;
    @Bind(R.id.pager_layout)
    RelativeLayout pagerLayout;
    List<QuestionAndroid> questions = new ArrayList<QuestionAndroid>();
    Apis apis;
    int testId;
    int userId;
    String token;

    CheckBox firstAnswer;
    CheckBox secondAnswer;
    CheckBox thirdAnswer;
    CheckBox fourthAnswer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_test_fragment, container, false);
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
        reapeat();
    }


    private void nextQuestion() {


        if (questions.size() - 1 != selectQuestion.getCurrentItem()) {
            QuestionAndroid question = questions.get(selectQuestion.getCurrentItem() + 1);
            runCall(apis.verifyIfQuestionIsAvailable(question.getQuestionId())).enqueue(new Callback<QuestionResponse>() {
                @Override
                public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                    if (response.isSuccess()) {
                        QuestionResponse questionResponse = response.body();
                        if (questionResponse.isAvailability())
                            selectQuestion.setCurrentItem(selectQuestion.getCurrentItem() + 1);
                    }
                }

                @Override
                public void onFailure(Call<QuestionResponse> call, Throwable t) {

                }
            });
            submitAnswer.setEnabled(true);
        }
    }


    private void reapeat() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (questions.size() - 1 != selectQuestion.getCurrentItem()) {
                    nextQuestion();
                    Log.d("DA", "DA");
                    handler.postDelayed(this, 5000);
                }
            }
        }, 3000);
    }

    private void updateView() {
        progressBar.setVisibility(View.GONE);
        pagerLayout.setVisibility(View.VISIBLE);
        selectQuestion.setAdapter(new QuestionStudentAdapter(getActivity(), questions));
        selectQuestion.setHorizontalScrollBarEnabled(false);
        selectQuestion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {

                } else {
                    //The current page is the last one
                    if (position == questions.size() - 1) {
                        submitAnswer.setText("Finish test");
                        submitAnswer.setBackgroundColor(Color.parseColor("#FF0000"));
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


    @OnClick(R.id.submit_answer_button)
    public void submitAnswer() {
        runCall(apis.verifyIfTestIsAvailable(testId)).enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (response.isSuccess()) {
                    TestResponse testResponse = response.body();
                    if (testResponse.isAvailability()) {

                        TextView name = (TextView) selectQuestion.findViewWithTag("name");
                        firstAnswer = (CheckBox) selectQuestion.findViewWithTag("firstAnswer" + selectQuestion.getCurrentItem());
                        secondAnswer = (CheckBox) selectQuestion.findViewWithTag("secondAnswer" + selectQuestion.getCurrentItem());
                        thirdAnswer = (CheckBox) selectQuestion.findViewWithTag("thirdAnswer" + selectQuestion.getCurrentItem());
                        fourthAnswer = (CheckBox) selectQuestion.findViewWithTag("fourthAnswer" + selectQuestion.getCurrentItem());

                        List<StudentAnswerResponse> studentAnswers = new ArrayList<StudentAnswerResponse>();
                        if (firstAnswer.isChecked()) {
                            StudentAnswerResponse studentResponse = new StudentAnswerResponse();
                            QuestionAndroid question = questions.get(selectQuestion.getCurrentItem());
                            studentResponse.setQuestionId(question.getQuestionId());
                            AnswerAndroid answer = question.getAnswers().get(0);
                            studentResponse.setAnswerId(answer.getAnswerId());
                            studentResponse.setTestId(testId);
                            studentResponse.setUserId(userId);
                            studentAnswers.add(studentResponse);

                        }
                        if (secondAnswer.isChecked()) {
                            StudentAnswerResponse studentResponse = new StudentAnswerResponse();
                            QuestionAndroid question = questions.get(selectQuestion.getCurrentItem());
                            studentResponse.setQuestionId(question.getQuestionId());
                            AnswerAndroid answer = question.getAnswers().get(1);
                            studentResponse.setAnswerId(answer.getAnswerId());
                            studentResponse.setTestId(testId);
                            studentResponse.setUserId(userId);
                            studentAnswers.add(studentResponse);
                        }
                        if (thirdAnswer.isChecked()) {
                            StudentAnswerResponse studentResponse = new StudentAnswerResponse();
                            QuestionAndroid question = questions.get(selectQuestion.getCurrentItem());
                            studentResponse.setQuestionId(question.getQuestionId());
                            AnswerAndroid answer = question.getAnswers().get(2);
                            studentResponse.setAnswerId(answer.getAnswerId());
                            studentResponse.setTestId(testId);
                            studentResponse.setUserId(userId);
                            studentAnswers.add(studentResponse);
                        }
                        if (fourthAnswer.isChecked()) {
                            StudentAnswerResponse studentResponse = new StudentAnswerResponse();
                            QuestionAndroid question = questions.get(selectQuestion.getCurrentItem());
                            studentResponse.setQuestionId(question.getQuestionId());
                            AnswerAndroid answer = question.getAnswers().get(3);
                            studentResponse.setAnswerId(answer.getAnswerId());
                            studentResponse.setTestId(testId);
                            studentResponse.setUserId(userId);
                            studentAnswers.add(studentResponse);
                        }
                        respondToQuestion(studentAnswers);
                        if (selectQuestion.getCurrentItem() == questions.size() - 1) {
                            Intent questionsStart = new Intent(getActivity(), StartupStudentActivity.class);
                            startActivity(questionsStart);
                            getActivity().finish();
                        }
                    } else {
                        Intent questionsStart = new Intent(getActivity(), StartupStudentActivity.class);
                        startActivity(questionsStart);
                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {

            }
        });
        submitAnswer.setEnabled(false);
    }

    public void respondToQuestion(List<StudentAnswerResponse> studentResponse) {
        StudentAnswerToken studentAnswerToken = new StudentAnswerToken();
        studentAnswerToken.setUserId(userId);
        studentAnswerToken.setToken(token);
        studentAnswerToken.setStudentResponse(studentResponse);
        runCall(apis.sendAnswer(studentAnswerToken)).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccess()) {
                    TokenResponse tokenResponse = response.body();
                    if (tokenResponse.isAvailability() == false) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

            }
        });
    }
}
