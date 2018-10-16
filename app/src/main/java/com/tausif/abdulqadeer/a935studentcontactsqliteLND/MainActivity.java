package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.StudentClasses.ViewStudentClassesListActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // region Fields and Instances
    DBHelper dbHelper;
    ListView studentsListView, testsListView, questionsListView, examsListView, resultsListView;
    Button viewStudentBtn, viewTestBtn, viewExamBtn, viewResult, newButtonMainActivity;
    TableLayout mainTable, headerTable;
    TableLayout.LayoutParams layoutParams;
    DBHelperSpecific dbHelperSpecific;
    ArrayList<StudentClass> classes;
    final static String[] CLASSES_NAME = {"Nursary", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};

    // endregion
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

        DBHelper db = new DBHelper(getApplicationContext());
        ArrayList<String> classesList = db.getAllStudentsClassRecordsInString();
        for (String student_class : classesList) {
//            Toast.makeText(getApplicationContext(), student_class, Toast.LENGTH_SHORT).show();
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

        // Creating classes
//        classes = new ArrayList<StudentClass>();
//        classes.add(new StudentClass(0, "Nursary"));
//        classes.add(new StudentClass(1, "One"));
//        classes.add(new StudentClass(2, "Two"));
//        classes.add(new StudentClass(3, "Three"));
//        classes.add(new StudentClass(4, "Four"));
//        classes.add(new StudentClass(5, "Five"));
//        classes.add(new StudentClass(6, "Six"));
//        classes.add(new StudentClass(7, "Seven"));
//        classes.add(new StudentClass(8, "Eight"));
//        classes.add(new StudentClass(9, "Nine"));
//        classes.add(new StudentClass(10, "Ten"));

        // Intializing list
        dbHelperSpecific = new DBHelperSpecific(getApplicationContext());
        classes = dbHelperSpecific.getAllStudentClasses();
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
        newButtonMainActivity.setOnClickListener(this);
    }

    // region Option Menu
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

    // endregion

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
                LaunchingClass = ViewStudentClassesListActivity.class;
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
        int tableBackgroundColor = 100;
        for (final StudentClass studentClass : classes) {

            // Intializing the Table Row
            TableRow tableRow = new TableRow(getApplicationContext());
            tableRow.setBackgroundColor(Color.rgb(tableBackgroundColor, tableBackgroundColor, tableBackgroundColor));
            tableRow.setLayoutParams(layoutParams);
            tableRow.setBackgroundResource(R.drawable.row_border);

            // Adding class name to the row
            TextView classSrNumber = getTextView();
            classSrNumber.setText(Integer.toString(studentClass.getSrialNo() + 1));
            tableRow.addView(classSrNumber);

            // Adding class name to the row
            TextView classNameTextView = getTextView();
            classNameTextView.setText(studentClass.getName());
            tableRow.addView(classNameTextView);

            // Adding total Students to the row
            TextView studentTextView = getTextView();
            String numberOfStudent = Integer.toString(dbHelperSpecific.getAllStudentsOfSpecificClass(studentClass.getSrialNo()).size());
            studentTextView.setText(numberOfStudent);
            studentTextView.setBackgroundColor(0xEEEEEEEE);
            studentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Class: " + studentClass.getSrialNo(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ViewClassStudentsActivity.class);
                    String classID = Integer.toString(studentClass.getSrialNo());
                    intent.putExtra("CLASS_ID_FOR_STUDENTS", classID);
                    startActivity(intent);

                }
            });
            tableRow.addView(studentTextView);

            // Adding Total Tests to the row
            TextView testTextView = getTextView();
            String numberOfTests = Integer.toString(dbHelperSpecific.getAllTestsRecordsOfClass(Integer.toString(studentClass.getSrialNo())).size());
            testTextView.setText(numberOfTests);
            testTextView.setBackgroundColor(0xEEEEEEFF);
            testTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ViewClassTestsActivity.class);
                    intent.putExtra("CLASS_ID_FOR_TESTS", Integer.toString(studentClass.getSrialNo()));
                    startActivity(intent);
                }
            });
            tableRow.addView(testTextView);

            mainTable.addView(tableRow);
        }
    }

    private TextView getTextView() {
        // Creating TextView
        TextView tempTextView = new TextView(getApplicationContext());

        tempTextView.setLayoutParams(
                new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT)
        );
        tempTextView.setGravity(Gravity.CENTER);
        tempTextView.setTextSize(16);
        tempTextView.setPadding(5, 20, 5, 20);
        tempTextView.setTextColor(0xFF000000);

        return tempTextView;
    }

    private Button getButton() {
        // Creating TextView
        Button tempButton = new Button(getApplicationContext());
        tempButton.setLayoutParams(
                new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT)
        );
        tempButton.setGravity(Gravity.CENTER);
        tempButton.setTextSize(16);
        tempButton.setPadding(5, 20, 5, 20);
        tempButton.setTextColor(0xFF000000);

        return tempButton;
    }

}