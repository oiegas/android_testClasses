package com.example.lenovo.testclasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.lenovo.domain.Grade;
import com.example.lenovo.domain.StudentGrade;
import com.example.lenovo.domain.Test;

import java.util.List;

/**
 * Created by AlexandruOi on 4/28/2016.
 */
public class GradeAdapter extends ArrayAdapter<StudentGrade> implements ListAdapter{

    private List<StudentGrade> grades;
    private Activity activity;
    private static LayoutInflater inflater=null;

    public GradeAdapter(Activity activity,int resource,List<StudentGrade> grades){
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
        public TextView test_name;
        public TextView test_grade;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent){
        View vi = convertView;
        final ViewHolder holder;
        if(convertView==null){
            vi=inflater.inflate(R.layout.studentstatistics_info,null);
            holder=new ViewHolder();
            holder.test_name= (TextView) vi.findViewById(R.id.test_name);
            holder.test_grade= (TextView) vi.findViewById(R.id.test_grade);
            vi.setTag(holder);
        }
        else{
            holder=(ViewHolder) vi.getTag();
        }

        holder.test_name.setText("Test Name: "+grades.get(position).getTestName());
        holder.test_grade.setText("Grade: "+grades.get(position).getGrade());
        return vi;
    }

}

