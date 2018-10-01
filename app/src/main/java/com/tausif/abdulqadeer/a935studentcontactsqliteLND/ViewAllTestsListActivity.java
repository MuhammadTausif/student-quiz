package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ViewAllTestsListActivity extends AppCompatActivity {

    ListView allTestsListViewListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_tests_list);

        inflateViews();
        attachTestsToList();
        addActionListener();
    }

    public void inflateViews(){
        allTestsListViewListView = (ListView) findViewById(R.id.allTestsListViewListView);
    }

    public void attachTestsToList(){
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        ArrayList<String> allTestRecord = dbHelper.getAllTestsRecords();

        ArrayAdapter<String> allTestRecordArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, allTestRecord);
        allTestsListViewListView.setAdapter(allTestRecordArrayAdapter);
    }

    public void addActionListener(){}
}
