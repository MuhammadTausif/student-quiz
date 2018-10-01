package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewStudentActivity extends AppCompatActivity {

    ListView studentListView;
    TextView classTextView;
    Button addNewStudentButtonFromStudentsViewActivity, backToClassesButton;
    public static int CURRENT_CLASS_FOR_STUDENT_VIEW = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        inflateViews();
        attachStudentToList();
        addActionListener();

    }

    private void inflateViews() {
        studentListView = (ListView) findViewById(R.id.students_list_veiw_view_student);
        classTextView = (TextView) findViewById(R.id.class_text_view_student_view);
        addNewStudentButtonFromStudentsViewActivity = (Button) findViewById(R.id.add_new_student_link_button_in_view_students);
        backToClassesButton = (Button) findViewById(R.id.back_to_classes);
    }

    private void attachStudentToList() {
        DBHelperSpecific dbHelperSpecific = new DBHelperSpecific(ViewStudentActivity.this);

        Intent intent = getIntent();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int classId = Integer.parseInt(extras.getString("CLASS_ID_FOR_STUDENTS"));
             CURRENT_CLASS_FOR_STUDENT_VIEW = classId;

            ArrayList<Student> studentsObjArrayListOfClass = dbHelperSpecific.getAllStudentsOfSpecificClass(classId);
            ArrayList<String> studentsArrayListOfClass = new ArrayList<String>();

            for (Student s :
                    studentsObjArrayListOfClass) {
                studentsArrayListOfClass.add(s.toString());
            }

            StudentListViewAdapter studentListViewAdapter=new StudentListViewAdapter(ViewStudentActivity.this,studentsObjArrayListOfClass);
            ArrayAdapter studentArrayAdapter = new ArrayAdapter(ViewStudentActivity.this, android.R.layout.simple_list_item_1, studentsArrayListOfClass);

//            studentListView.setAdapter(studentArrayAdapter);
            studentListView.setAdapter(studentListViewAdapter);
            classTextView.setText("Class: " + classId);
        }
    }

    private void addActionListener() {
        addNewStudentButtonFromStudentsViewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewStudentActivity.this, AddStudentActivity.class);
                intent.putExtra("CLASS_ID_FR0M_VIEW_STUDENTS", CURRENT_CLASS_FOR_STUDENT_VIEW);

                startActivity(intent);
            }
        });
        backToClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewStudentActivity.this, ViewClassesActivity.class);
                startActivity(intent);
            }
        });
    }
}
