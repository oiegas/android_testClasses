package com.example.lenovo.testclasses;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.domain.QuestionAndroid;

import java.util.List;

/**
 * Created by AlexandruOi on 5/10/2016.
 */
public class QuestionStudentAdapter extends PagerAdapter {
    private Context context;
    private List<QuestionAndroid> questions;
    private LayoutInflater layoutInflater;

    public QuestionStudentAdapter(Context context, List<QuestionAndroid> questions) {
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
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.student_question_item, container, false);
        QuestionAndroid question = getItem(position);
        TextView questionName = (TextView) itemView.findViewById(R.id.question_name);
        CheckBox firstAnswer = (CheckBox) itemView.findViewById(R.id.first_answer);
        CheckBox secondAnswer = (CheckBox) itemView.findViewById(R.id.second_answer);
        CheckBox thirdAnswer = (CheckBox) itemView.findViewById(R.id.third_answer);
        CheckBox fourthAnswer = (CheckBox) itemView.findViewById(R.id.fourth_answer);
        questionName.setTag("name");
        firstAnswer.setTag("firstAnswer"+position);
        secondAnswer.setTag("secondAnswer"+position);
        thirdAnswer.setTag("thirdAnswer"+position);
        fourthAnswer.setTag("fourthAnswer"+position);


        questionName.setText("Question: " + question.getQuestion());
        if (question.getAnswers().size() == 2) {
            firstAnswer.setText(" " + question.getAnswers().get(0).getAnswer());
            secondAnswer.setText(" " + question.getAnswers().get(1).getAnswer());
            thirdAnswer.setVisibility(View.INVISIBLE);
            fourthAnswer.setVisibility(View.INVISIBLE);
        }
        else if(question.getAnswers().size()==3){
            firstAnswer.setText(" " + question.getAnswers().get(0).getAnswer());
            secondAnswer.setText(" " + question.getAnswers().get(1).getAnswer());
            thirdAnswer.setText(" "+question.getAnswers().get(2).getAnswer());
            fourthAnswer.setVisibility(View.INVISIBLE);
        }
        else{
            firstAnswer.setText(" " + question.getAnswers().get(0).getAnswer());
            secondAnswer.setText(" " + question.getAnswers().get(1).getAnswer());
            thirdAnswer.setText(" "+question.getAnswers().get(2).getAnswer());
            fourthAnswer.setText(" "+question.getAnswers().get(3).getAnswer());
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        FragmentManager manager= ((Fragment) object).getFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove((Fragment) object);
        trans.commit();
    }
}
