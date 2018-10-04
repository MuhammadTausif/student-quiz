package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewClassTestsActivity extends AppCompatActivity {

    ListView classTestListView;
    TextView classTestTextView;
    TableLayout tableLayout;
    Bundle extras;
    DBHelperSpecific dbHelperSpecific;

    TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT
    );

    public static int CURRENT_CLASS_FOR_CLASS_TESTS_VIEW = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class_tests);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_new_test_for_class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTestActivity.class);
                startActivity(intent);

            }
        });

        inflateViews();
        getExtras();
        addTableHeader();
//        attachClassTestsToTable();
        addActionListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        tableLayout.removeAllViews();
        addTableHeader();
        attachClassTestsToTable();
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

    private void inflateViews() {
        classTestTextView = (TextView) findViewById(R.id.class_test_text_test_view);
        classTestListView = (ListView) findViewById(R.id.class_tests_list_veiw_view_class_test);
        tableLayout = (TableLayout) findViewById(R.id.class_tests_table);
    }

    private void addTableHeader() {
        TableRow tableHeader = new TableRow(getApplicationContext());
        tableHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));

        tableHeader.setLayoutParams(layoutParams);
        String[] headerText = {"ID", "SUBJECT", "CHAPTER", "SECTIONS", "TOTAL QUESTION", "TOTAL TIME", "Action"};
        for (String c : headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(
                    new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT)
            );
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            tableHeader.addView(tv);
        }
        tableLayout.addView(tableHeader);
    }

    private void getExtras() {
        extras = getIntent().getExtras();
    }

    private void attachClassTestsToTable() {
        dbHelperSpecific = new DBHelperSpecific(getApplicationContext());
        final Intent intent = getIntent();
        if (extras != null) {
            int classId = Integer.parseInt(extras.getString("CLASS_ID_FOR_TESTS"));
            CURRENT_CLASS_FOR_CLASS_TESTS_VIEW = classId;
            ArrayList<Test> testArrayList = dbHelperSpecific.getAllTestsRecordsOfClass(extras.getString("CLASS_ID_FOR_TESTS"));
            ArrayList<String> testsArrayListOfClass = new ArrayList<String>();

            for (final Test t : testArrayList) {
                TableRow tableRow = new TableRow(getApplicationContext());
                tableRow.setBackgroundColor(Color.BLACK);
                tableRow.setLayoutParams(layoutParams);

                // Adding id to the row
                TextView idTextView = getTextView();
                idTextView.setText(Integer.toString(t._id));
                tableRow.addView(idTextView);

                // Adding subject to the row
                TextView subjectTextView = getTextView();
                subjectTextView.setText(t.subject);
                tableRow.addView(subjectTextView);

                // Adding chapters to the row
                TextView chapterTextView = getTextView();
                chapterTextView.setText(t.chapter);
                tableRow.addView(chapterTextView);

                // Adding sections to the row
                TextView sectionsTextView = getTextView();
                sectionsTextView.setText(t.sections);
                tableRow.addView(sectionsTextView);

                // Adding Total Time to the row
                TextView totalTimeTextView = getTextView();
                totalTimeTextView.setText(t.totalTime);
                tableRow.addView(totalTimeTextView);

                // Adding Total Question to the row
                TextView totalQuestionsTextView = getTextView();
                totalQuestionsTextView.setText(t.totalQuestions);
                tableRow.addView(totalQuestionsTextView);

                // Adding Action to the table
                TextView openTextView = getTextView();
                openTextView.setText("Open");
                final String idString = Integer.toString(t._id);
                openTextView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(getApplicationContext(), "Test ID No: " + Integer.toString(t._id), Toast.LENGTH_SHORT).show();
                                Intent updateDeleteTestIntent = new Intent(getApplicationContext(), UpdateDeleteTestActivity.class);
                                updateDeleteTestIntent.putExtra("TEST_ID_OF_CLASS", Integer.toString(t._id));
                                startActivity(updateDeleteTestIntent);
                            }
                        }
                );
                tableRow.addView(openTextView);

                // Adding row to the table
                tableLayout.addView(tableRow);
                testsArrayListOfClass.add(t.toString());
            }

            TestListViewAdapter testListViewAdapter = new TestListViewAdapter(getApplicationContext(), testArrayList);
            ArrayAdapter classTestArrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, testsArrayListOfClass);

            // Adding tests to the list, (Now it is disabled as the table layout is added.)
            // classTestListView.setAdapter(testListViewAdapter);
            classTestTextView.setText("Class: " + classId);
        }
    }

    @NonNull
    private TextView getTextView() {
        // Creating TextView
        TextView tempTextView = new TextView(getApplicationContext());
        tempTextView.setLayoutParams(
                new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT)
        );
        tempTextView.setGravity(Gravity.CENTER);
        tempTextView.setTextSize(16);
        tempTextView.setPadding(5, 15, 5, 15);

        return tempTextView;
    }

    private void addActionListener() {

    }
}
