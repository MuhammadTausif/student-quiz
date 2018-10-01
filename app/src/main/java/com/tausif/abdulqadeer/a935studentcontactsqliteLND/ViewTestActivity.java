package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewTestActivity extends Activity {

    ListView testListView;
    DBHelperSpecific dbHelperSpecific;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);

//        addAddTestBtn();
//        inflateFeilds();
//        addData();

    }

    private void addAddTestBtn() {

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void inflateFeilds() {
        testListView= (ListView) findViewById(R.id.test_record_list_view_test_view);
        dbHelperSpecific=new DBHelperSpecific(ViewTestActivity.this);
    }

    private void addData() {
        ArrayList<Test> testArrayListFromDB=new ArrayList<Test>();
        testArrayListFromDB=dbHelperSpecific.getAllTestsRecords(" ");
        TestListViewAdapter testListViewAdapter=new TestListViewAdapter(ViewTestActivity.this, testArrayListFromDB);
        testListView.setAdapter(testListViewAdapter);
    }
}
