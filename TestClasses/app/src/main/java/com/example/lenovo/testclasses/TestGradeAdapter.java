package com.example.lenovo.testclasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.lenovo.domain.StudentGrade;

import java.util.List;

/**
 * Created by AlexandruOi on 5/9/2016.
 */
public class TestGradeAdapter extends ArrayAdapter<StudentGrade> implements ListAdapter {

    private List<StudentGrade> grades;
    private Activity activity;
    private static LayoutInflater inflater=null;

    public TestGradeAdapter(Activity activity,int resource,List<StudentGrade> grades){
        super(activity,resource,grades);
        this.activity=activity;
        this.grades=grades;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return grades.size();
    }


    public StudentGrade getItem(StudentGrade grade) {
        return grade;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView student_name;
        public TextView student_grade;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View vi = convertView;
        final ViewHolder holder;
        if(convertView==null){
            vi=inflater.inflate(R.layout.grades_info,null);
            holder=new ViewHolder();
            holder.student_name= (TextView) vi.findViewById(R.id.student_name);
            holder.student_grade= (TextView) vi.findViewById(R.id.student_grade);
            vi.setTag(holder);
        }
        else{
            holder=(ViewHolder) vi.getTag();
        }

        holder.student_name.setText("Student: "+grades.get(position).getUserName());
        holder.student_grade.setText("Grade: "+grades.get(position).getGrade());
        return vi;
    }

}