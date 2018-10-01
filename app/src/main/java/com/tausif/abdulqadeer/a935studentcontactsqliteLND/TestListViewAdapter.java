package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abdul Qadeer on 10/22/2017.
 */

public class TestListViewAdapter extends BaseAdapter {

    ArrayList<Test> testArrayList;
    Context context;

    public TestListViewAdapter(Context context, ArrayList<Test> testArrayList) {
        this.context = context;
        this.testArrayList = testArrayList;
    }

    @Override
    public int getCount() {
        return testArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return testArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Test testForId = testArrayList.get(position);
        long tempTestId = testForId.get_id();
        return tempTestId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context parentContext = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.single_list_test, parent, false);
        }

        final Test test = testArrayList.get(position);

        int testId;
        TextView  _id= (TextView) convertView.findViewById(R.id.idForTestListItem);
        TextView  classTest= (TextView) convertView.findViewById(R.id.classForTestListItem);
        TextView subject = (TextView) convertView.findViewById(R.id.subjectForTestListItem);
        TextView chapter= (TextView) convertView.findViewById(R.id.chapterForTestListItem);
        TextView sections = (TextView) convertView.findViewById(R.id.sectionsForTestListItem);
        TextView dateTime = (TextView) convertView.findViewById(R.id.dateTimeForTestListItem);
        TextView totalTime = (TextView) convertView.findViewById(R.id.totalTimeForTestListItem);
        TextView  totalQuestions= (TextView) convertView.findViewById(R.id.totalQuestionForTestListItem);

        // making text to view
        testId=test.get_id();

        _id.setText(String.valueOf(test.get_id()));
        classTest.setText(test.getClassTest());
        subject.setText(test.getSections());
        chapter.setText(test.getChapter());
        sections.setText(test.getSections());
        dateTime.setText(test.getDataTime());
        totalTime.setText(test.getTotalTime());
        totalQuestions.setText(test.getTotalQuestions());



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parentContext, UpdateDeleteStudentActivity.class);
//                intent.putExtra("studentObject", test);
//                parentContext.startActivity(intent);

            }
        });
        return convertView;
    }
}
