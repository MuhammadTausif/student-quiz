package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewTestsActivity extends AppCompatActivity implements View.OnClickListener {

    // Varibles
    ListView testsListView;
    Button viewAllTestsListButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), AddTestActivity.class);
                startActivity(intent);

            }
        });

        inflateViews();
        attachTestsToList();
        addActionListener();
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

    public void inflateViews(){
        testsListView = (ListView) findViewById(R.id.allClassesViewForTestsListView);
        viewAllTestsListButton = (Button) findViewById(R.id.viewAllTestsListButton);
    }

    public void attachTestsToList(){
        /**
         * Old code
         */
//        DBHelper dbHelper = new DBHelper(getApplicationContext());
//        ArrayList<String> allTestRecord = dbHelper.getAllTestsRecords();
//
//        ArrayAdapter<String> allTestRecordArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
//                android.R.layout.simple_list_item_1, allTestRecord);
//        testsListView.setAdapter(allTestRecordArrayAdapter);
    }

    public void addActionListener(){
        viewAllTestsListButton.setOnClickListener(this);
        testsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String classOfTests = ((TextView) view).getText().toString();
                Intent intent=new Intent(getApplicationContext(), ViewClassTestsActivity.class);
                intent.putExtra("CLASS_ID_FOR_TESTS", classOfTests);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), " Test for Class: " +classOfTests, Toast.LENGTH_SHORT ).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Class launchingClass=ViewAllTestsListActivity.class;

        switch (v.getId()) {
            case R.id.view_student_main_text_view:
                launchingClass = ViewClassesActivity.class;
                break;
        }

        intent=new Intent(getApplicationContext(), launchingClass);
        startActivity(intent);
        }
    }

