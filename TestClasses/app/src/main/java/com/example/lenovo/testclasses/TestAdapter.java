package com.example.lenovo.testclasses;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.lenovo.domain.Test;

import java.util.List;

/**
 * Created by AlexandruOi on 4/26/2016.
 */
public class TestAdapter extends ArrayAdapter<Test> implements ListAdapter {
    private List<Test> tests;
    private Activity activity;
    private static LayoutInflater inflater = null;

    public TestAdapter(Activity activity, int resource, List<Test> tests) {
        super(activity, resource, tests);
        this.activity = activity;
        this.tests = tests;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tests.size();
    }


    public Test getItem(Test test) {
        return test;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView test_name;
        public TextView test_class;
        public TextView test_status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.test_info, null);
            holder = new ViewHolder();
            holder.test_name = (TextView) vi.findViewById(R.id.test_name);
            holder.test_class = (TextView) vi.findViewById(R.id.test_class);
            holder.test_status = (TextView) vi.findViewById(R.id.test_status);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        holder.test_name.setText(tests.get(position).getName());
        holder.test_class.setText(tests.get(position).getClassForTest().getName());
        if (tests.get(position).isOpened()) {
            holder.test_status.setText("Opened");
            holder.test_status.setTextColor(Color.parseColor("#008000"));
        }
        else {
            holder.test_status.setText("Closed");
            holder.test_status.setTextColor(Color.parseColor("#FF0000"));
        }return vi;
    }
}
