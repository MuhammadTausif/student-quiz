package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.app.Dialog;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;
    ListView studentsListView, testsListView, questionsListView, examsListView, resultsListView;
    Button viewStudentBtn, viewTestBtn, viewExamBtn, viewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflateFeilds();
        addActionListener();

        // These lines of code are to seed the data when the application is created.
        dbHelper = new DBHelper(this.getApplicationContext());
        if(dbHelper.getAllStudentsRecords().size()<1){
            dbHelper.seedData();
        }
    }

    private void inflateFeilds() {
        viewStudentBtn = (Button) findViewById(R.id.view_student_main_text_view);
        viewTestBtn = (Button) findViewById(R.id.view_test_main_text_view);
        viewExamBtn = (Button) findViewById(R.id.view_exam_main_text_view);
        viewResult = (Button) findViewById(R.id.view_result_main_text_view);
    }

    private void addActionListener() {
        viewStudentBtn.setOnClickListener(this);
        viewTestBtn.setOnClickListener(this);
        viewExamBtn.setOnClickListener(this);
        viewResult.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent intent;
        switch (item.getItemId()) {

            case R.id.home_menu:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.students_menu:
                intent = new Intent(getApplicationContext(), ViewClassesActivity.class);
                startActivity(intent);
                return true;

            case R.id.add_student_menu:
                intent = new Intent(getApplicationContext(), AddStudentActivity.class);
                startActivity(intent);
                return true;

            case R.id.add_test_menu:
                intent = new Intent(getApplicationContext(), AddTestActivity.class);
                startActivity(intent);
                return true;

            case R.id.add_questions_menu:
                int test=1;
                intent = new Intent(getApplicationContext(), AddQuestionActivity.class);
                startActivity(intent);
                return true;

            case R.id.add_exam_menu:
                intent = new Intent(getApplicationContext(), AddExamActivity.class);
                startActivity(intent);
                return true;

            case R.id.take_quiz_menu:
                intent = new Intent(getApplicationContext(), QuizActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Class LaunchingClass=ViewClassesActivity.class;

        switch (v.getId()) {
            case R.id.view_student_main_text_view:
                LaunchingClass = ViewClassesActivity.class;
                break;
            case R.id.view_test_main_text_view:
                LaunchingClass=ViewTestsActivity.class;
//                LaunchingClass = ViewClassesActivity.class;
                break;
            case R.id.view_exam_main_text_view:
//                myClass=ViewExamActivity.class;
                break;
            case R.id.view_result_main_text_view:
//                myClass=ViewResultActivity.class;
                break;
        }
        intent=new Intent(MainActivity.this, LaunchingClass);
        startActivity(intent);

    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }

    // This was testing code used before
    private void testingCode() {

        dbHelper = new DBHelper(this);
        boolean testInsert = false;

        ArrayList<String> al = new ArrayList<String>();
        ArrayList<String> a2 = new ArrayList<String>();

        al.add("A");
        al.add("B");
        al.add("C");
        al.add("D");
        al.add("E");
        al.add("F");

        a2.add("A");
        a2.add("B");
        a2.add("C");
        a2.add("D");
        a2.add("E");

        al.removeAll(a2);

        int a = 1;

        studentsListView = (ListView) findViewById(R.id.students_record);
        testsListView = (ListView) findViewById(R.id.tests_record);
        questionsListView = (ListView) findViewById(R.id.questions_record);
        examsListView = (ListView) findViewById(R.id.exams_record);
        resultsListView = (ListView) findViewById(R.id.results_record);

        // Getting and Displaying Students data
        ArrayList studentsArrayList = dbHelper.getAllStudentsRecords();
        ArrayAdapter studentsArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, studentsArrayList);
        studentsListView.setAdapter(studentsArrayAdapter);

        // Getting and Displaying Tests data
        ArrayList testsArrayList = dbHelper.getAllTestsRecords();
        ArrayAdapter testsArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, testsArrayList);

        testsListView.setAdapter(testsArrayAdapter);

        // Getting and Displaying Question data
        ArrayList questionsArrayList = dbHelper.getAllQuestionsRecords();
        ArrayAdapter questionsArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, questionsArrayList);

        questionsListView.setAdapter(questionsArrayAdapter);
//        questionsListView.setAdapter(questionArrayAdapter1);

        // Getting and Displaying Exam data
        ArrayList examsArrayList = dbHelper.getAllExamsRecords();
        ArrayAdapter examsArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, examsArrayList);

        examsListView.setAdapter(examsArrayAdapter);

        // Getting and Displaying Result data
        ArrayList resultsArrayList = dbHelper.getAllResultsRecords();
        ArrayAdapter resultsArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, resultsArrayList);

        resultsListView.setAdapter(resultsArrayAdapter);
    }
}