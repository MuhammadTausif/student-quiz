package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Abdul Qadeer on 10/22/2017.
 */

public class StudentListViewAdapter extends BaseAdapter {

    ArrayList<Student> studentsList;
    Context context;

    public StudentListViewAdapter(Context context, ArrayList<Student> studentsList) {
        this.context = context;
        this.studentsList = studentsList;
    }

    @Override
    public int getCount() {
        return studentsList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Student studentForId = studentsList.get(position);
        long tempStudentId = studentForId.get_id();
        return tempStudentId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context parentContext = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.single_list_student, parent, false);
        }

        final Student student = studentsList.get(position);

        int studentId;
        TextView name = (TextView) convertView.findViewById(R.id.nameForListItem);
        TextView fatherName = (TextView) convertView.findViewById(R.id.fatherNameForListItem);
        TextView address = (TextView) convertView.findViewById(R.id.addressForListItem);
        TextView phone = (TextView) convertView.findViewById(R.id.phoneForListItem);
//        TextView classStd = (TextView) convertView.findViewById(R.id.classStdForListItem);
        TextView rollNo = (TextView) convertView.findViewById(R.id.rollNoForListItem);

        studentId = student.get_id();
        name.setText(student.getName());
        fatherName.setText(student.getFatherName());
        address.setText(student.getAddress());
        phone.setText(student.getPhone());
//        classStd.setText(String.valueOf(student.getClassStd()));
        rollNo.setText(String.valueOf(student.getRollNo()));


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parentContext, "Student Data: \n" +
                        "- Name: " + student.getName() + "\n" +
                        "- Father Name: " + student.getFatherName() + "\n" +
                        "- Address: " + student.getAddress() + "\n" +
                        "- Phone: " + student.getPhone() + "\n" +
                        "- Class: " + student.getClassStd() + "\n" +
                        "- Roll No: " + student.getRollNo() + "\n" +
                        "End", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(parentContext, UpdateDeleteStudentActivity.class);
                intent.putExtra("studentObject", student);
                parentContext.startActivity(intent);

            }
        });
        return convertView;
    }
}
