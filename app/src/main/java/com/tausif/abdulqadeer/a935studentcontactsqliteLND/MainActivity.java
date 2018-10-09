package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.app.Dialog;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;
    ListView studentsListView, testsListView, questionsListView, examsListView, resultsListView;
    Button viewStudentBtn, viewTestBtn, viewExamBtn, viewResult, newButtonMainActivity;
    TableLayout mainTable, headerTable;
    TableLayout.LayoutParams layoutParams;
    DBHelperSpecific dbHelperSpecific;
    final static String[] CLASSES_NAME = {"Nursary", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflateFeilds();
        addActionListener();
        addTableHeader();
        addDataToTable();

        // These lines of code are to seed the data when the application is created.
        dbHelper = new DBHelper(this.getApplicationContext());
        if (dbHelper.getAllStudentsRecords().size() < 1) {
            dbHelper.seedData();
        }
    }

    private void inflateFeilds() {
        viewStudentBtn = (Button) findViewById(R.id.view_student_main_text_view);
        viewTestBtn = (Button) findViewById(R.id.view_test_main_text_view);
        viewExamBtn = (Button) findViewById(R.id.view_exam_main_text_view);
        viewResult = (Button) findViewById(R.id.view_result_main_text_view);
        newButtonMainActivity = (Button) findViewById(R.id.new_button_mainActivity);
        mainTable = (TableLayout) findViewById(R.id.main_table_main);
        headerTable = (TableLayout) findViewById(R.id.header_table_main);

        // Intializing list
        dbHelperSpecific = new DBHelperSpecific(getApplicationContext());
        String[] classesNameList = getResources().getStringArray(R.array.classes_name_list);

        // Setting layout parameter
        layoutParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT
        );
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
                int test = 1;
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
        Class LaunchingClass = ViewClassesActivity.class;

        switch (v.getId()) {
            case R.id.view_student_main_text_view:
                LaunchingClass = ViewClassesActivity.class;
                break;
            case R.id.view_test_main_text_view:
                LaunchingClass = ViewTestsActivity.class;
//                LaunchingClass = ViewClassesActivity.class;
                break;
            case R.id.view_exam_main_text_view:
//                myClass=ViewExamActivity.class;
                break;
            case R.id.view_result_main_text_view:
//                myClass=ViewResultActivity.class;
                break;
            // This is new doing
            case R.id.new_button_mainActivity:
                // do some thing
                Toast.makeText(this, "New is touched.", Toast.LENGTH_SHORT).show();
                break;
        }
        intent = new Intent(MainActivity.this, LaunchingClass);
        startActivity(intent);

    }

    /**
     * Adding header to the table.
     */
    void addTableHeader() {
        TableRow tableHeader = new TableRow(getApplicationContext());
        tableHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));

        // Creating table header
        tableHeader.setLayoutParams(layoutParams);
        String[] headerText = {"No", "Class", "Student", "Tests"};
        for (String c : headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(
                    new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT)
            );
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 25, 5, 25);
            tv.setText(c);
            tableHeader.addView(tv);
        }
//        headerTable.addView(tableHeader);
        mainTable.addView(tableHeader);
    }

    /**
     * Adding data to the Main Table
     */
    void addDataToTable() {
        int i= 1;
        int tableBackgroundColor = 100;
        for (String className : CLASSES_NAME) {

            // Intializing the Table Row
            TableRow tableRow = new TableRow(getApplicationContext());
            tableRow.setBackgroundColor(Color.rgb(tableBackgroundColor,tableBackgroundColor,tableBackgroundColor));
            tableRow.setLayoutParams(layoutParams);
            tableRow.setBackgroundResource(R.drawable.row_border);

            // Adding class name to the row
            TextView classSrNumber = getTextView();
            classSrNumber.setText(Integer.toString(i));
            tableRow.addView(classSrNumber);

            // Adding class name to the row
            TextView classNameTextView = getTextView();
            classNameTextView.setText(className);
            tableRow.addView(classNameTextView);

            // Adding total Students to the row
            TextView studentTextView = getTextView();
            String numberOfStudent = Integer.toString(dbHelperSpecific.getAllStudentsOfSpecificClass(i).size());
            studentTextView.setText(numberOfStudent);
            tableRow.addView(studentTextView);

            // Adding Total Tests to the row
            TextView testTextView = getTextView();
            String numberOfTests = Integer.toString(dbHelperSpecific.getAllTestsRecordsOfClass(Integer.toString(i)).size());
            testTextView.setText(numberOfTests);
            tableRow.addView(testTextView);

            i++;

            mainTable.addView(tableRow);
        }
    }


    private TextView getTextView() {
        // Creating TextView
        TextView tempTextView = new TextView(getApplicationContext());
        tempTextView.setLayoutParams(
                new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT)
        );
        tempTextView.setGravity(Gravity.CENTER);
        tempTextView.setTextSize(16);
        tempTextView.setPadding(5, 20, 5, 20);
        tempTextView.setTextColor(0xFF000000);

        return tempTextView;
    }

}