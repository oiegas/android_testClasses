package com.example.lenovo.testclasses;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.domain.QuestionAndroid;

import java.util.List;

/**
 * Created by AlexandruOi on 5/8/2016.
 */
public class QuestionAdapter extends PagerAdapter {

    private Context context;
    private List<QuestionAndroid> questions;
    private LayoutInflater layoutInflater;

    public QuestionAdapter(Context context, List<QuestionAndroid> questions) {
        this.questions = questions;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private QuestionAndroid getItem(int position) {
        return questions.get(position);
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.question_item, container, false);
        QuestionAndroid question = getItem(position);
        TextView questionName = (TextView) itemView.findViewById(R.id.question_name);
        TextView firstAnswer = (TextView) itemView.findViewById(R.id.first_answer);
        TextView secondAnswer = (TextView) itemView.findViewById(R.id.second_answer);
        TextView thirdAnswer = (TextView) itemView.findViewById(R.id.third_answer);
        TextView fourthAnswer = (TextView) itemView.findViewById(R.id.fourth_answer);
        TextView nextQuestion = (TextView) itemView.findViewById(R.id.next_question);

        questionName.setText("Question: " + question.getQuestion());
        if (question.getAnswers().size() == 2) {
            firstAnswer.setText("a) " + question.getAnswers().get(0).getAnswer());
            if (question.getAnswers().get(0).isGood() == true)
                firstAnswer.setTextColor(Color.parseColor("#008000"));
            secondAnswer.setText("b) " + question.getAnswers().get(1).getAnswer());
            if (question.getAnswers().get(1).isGood() == true)
                secondAnswer.setTextColor(Color.parseColor("#008000"));
        } else if (question.getAnswers().size() == 3) {
            firstAnswer.setText("a) " + question.getAnswers().get(0).getAnswer());
            if (question.getAnswers().get(0).isGood() == true)
                firstAnswer.setTextColor(Color.parseColor("#008000"));
            secondAnswer.setText("b) " + question.getAnswers().get(1).getAnswer());
            if (question.getAnswers().get(1).isGood() == true)
                secondAnswer.setTextColor(Color.parseColor("#008000"));
            thirdAnswer.setText("c) " + question.getAnswers().get(2).getAnswer());
            if (question.getAnswers().get(2).isGood() == true)
                thirdAnswer.setTextColor(Color.parseColor("#008000"));
        } else {
            firstAnswer.setText("a) " + question.getAnswers().get(0).getAnswer());
            if (question.getAnswers().get(0).isGood() == true)
                firstAnswer.setTextColor(Color.parseColor("#008000"));
            secondAnswer.setText("b) " + question.getAnswers().get(1).getAnswer());
            if (question.getAnswers().get(1).isGood() == true)
                secondAnswer.setTextColor(Color.parseColor("#008000"));
            thirdAnswer.setText("c) " + question.getAnswers().get(2).getAnswer());
            if (question.getAnswers().get(2).isGood() == true)
                thirdAnswer.setTextColor(Color.parseColor("#008000"));
            fourthAnswer.setText("d) " + question.getAnswers().get(3).getAnswer());
            if (question.getAnswers().get(3).isGood() == true)
                fourthAnswer.setTextColor(Color.parseColor("#008000"));
        }
        if (position != questions.size() - 1) {
            QuestionAndroid question2 = getItem(position + 1);
            nextQuestion.setText("Next question: " + question2.getQuestion());
        } else
            nextQuestion.setText("This was the last question");

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
