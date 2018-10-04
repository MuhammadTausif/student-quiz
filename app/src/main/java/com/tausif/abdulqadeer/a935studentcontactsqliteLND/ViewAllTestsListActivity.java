package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ViewAllTestsListActivity extends AppCompatActivity {

    ListView allTestsListViewListView;
    Button buttunBackToClassesFromAllTests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_tests_list);

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
        allTestsListViewListView = (ListView) findViewById(R.id.allTestsListViewListView);
        buttunBackToClassesFromAllTests = (Button) findViewById(R.id.buttun_back_to_classes_from_all_tests);
    }

    public void attachTestsToList(){
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        ArrayList<String> allTestRecord = dbHelper.getAllTestsRecords();

        ArrayAdapter<String> allTestRecordArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, allTestRecord);
        allTestsListViewListView.setAdapter(allTestRecordArrayAdapter);
    }

    public void addActionListener(){
        buttunBackToClassesFromAllTests.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewTestsActivity.class);
                startActivity(intent);
            }
        });
    }
}
