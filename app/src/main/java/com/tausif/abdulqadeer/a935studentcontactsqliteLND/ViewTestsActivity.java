package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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

