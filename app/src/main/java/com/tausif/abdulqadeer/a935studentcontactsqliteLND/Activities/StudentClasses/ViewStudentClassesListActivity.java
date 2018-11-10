package com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.StudentClasses;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddExamActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddQuestionActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddStudentActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddTestActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelperSpecific;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.MainActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.QuizActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.R;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.StudentClass;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.ToastMessage;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.ViewClassStudentsActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.ViewClassTestsActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.ViewClassesActivity;

import java.util.ArrayList;

public class ViewStudentClassesListActivity extends AppCompatActivity {

    // region Feilds and Variable
    TableLayout studentClassesListTable;
    DBHelperSpecific dbHelperSpecific;
    ArrayList<StudentClass> studentClasses;
    TableLayout.LayoutParams layoutParams;
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_classes_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inflateAndIntialize();
        addTableHeader();
        attachStudentClassesListToTable();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // region Floating action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Create New Student Class", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent newStudentClassIntent = new Intent(getApplicationContext(), NewStudentClassActivity.class);
                startActivity(newStudentClassIntent);
            }
        });
        // endregion
    }

    @Override
    protected void onResume() {
        super.onResume();
        studentClasses = dbHelperSpecific.getAllStudentClasses();
        studentClassesListTable.removeAllViews();
        addTableHeader();
        attachStudentClassesListToTable();
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
//                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent = new Intent(getApplicationContext(), ViewStudentClassesListActivity.class);
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

    void inflateAndIntialize() {
        studentClassesListTable = (TableLayout) findViewById(R.id.studentClassesList_table_viewStudentClassesList);
        dbHelperSpecific = new DBHelperSpecific(getApplicationContext());
        studentClasses = dbHelperSpecific.getAllStudentClasses();

        // Setting layout parameter
        layoutParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT
        );

    }

    /**
     * Adding header to the table.
     */
    void addTableHeader() {
        TableRow tableHeader = new TableRow(getApplicationContext());
        tableHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));

        // Creating table header
        tableHeader.setLayoutParams(layoutParams);
        String[] headerText = {"ID", "Serial NO", "Name", "Active Test ID", "Students", "Tests"};
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
        studentClassesListTable.addView(tableHeader);
    }

    void attachStudentClassesListToTable() {
        int tableBackgroundColor = 100;
        // Setting layout parameter
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT
        );

        for (final StudentClass studentClass : studentClasses) {

            // Intializing the Table Row
            TableRow tableRow = new TableRow(getApplicationContext());
            tableRow.setBackgroundColor(Color.rgb(tableBackgroundColor, tableBackgroundColor, tableBackgroundColor));
            tableRow.setLayoutParams(layoutParams);
            tableRow.setBackgroundResource(R.drawable.row_border);


            // Adding class number to the row
            TextView studentClassID = getTextView();
            studentClassID.setText(Integer.toString(studentClass.get_id()));
            tableRow.addView(studentClassID);

            // Adding class number to the row
            TextView classSrNumber = getTextView();
            classSrNumber.setText(Integer.toString(studentClass.getSrialNo()));
            tableRow.addView(classSrNumber);

            // Adding class name to the row
            TextView classNameTextView = getTextView();
            classNameTextView.setText(studentClass.getName());
            classNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent updateStudentClassIntent = new Intent(ViewStudentClassesListActivity.this, UpdateDeleteStudentClassActivity.class);
                    updateStudentClassIntent.putExtra("STUDENT_CLASS_ID_FOR_UPDATE", studentClass.get_id());
                    startActivity(updateStudentClassIntent);
                }
            });
            tableRow.addView(classNameTextView);

            // Adding class name to the row
            TextView activeTestIDTextView = getTextView();
            if (studentClass.getActiveTestID() == -1) {
                activeTestIDTextView.setText("Null");
            } else {
                activeTestIDTextView.setText(Integer.toString(studentClass.getActiveTestID()));
            }
            tableRow.addView(activeTestIDTextView);

            // Adding total Students to the row
            TextView studentTextView = getTextView();
            String numberOfStudent = Integer.toString(dbHelperSpecific.getAllStudentsOfSpecificClass(studentClass.getSrialNo()).size());
            studentTextView.setText(numberOfStudent);
            studentTextView.setBackgroundColor(0xEEEEEEEE);
            studentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Yet to implement", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), "Class: " + studentClass.getSrialNo(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ViewStudentClassesListActivity.this, ViewClassStudentsActivity.class);
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
                    Toast.makeText(getApplicationContext(), "Yet to implement", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), ViewClassTestsActivity.class);
                    intent.putExtra("CLASS_ID_FOR_TESTS", Integer.toString(studentClass.getSrialNo()));
                    startActivity(intent);
                }
            });
            tableRow.addView(testTextView);

            studentClassesListTable.addView(tableRow);
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

}
