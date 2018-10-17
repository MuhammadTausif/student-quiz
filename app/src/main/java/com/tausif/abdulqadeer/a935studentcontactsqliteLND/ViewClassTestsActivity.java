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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.StudentClasses.ViewStudentClassesListActivity;

import java.util.ArrayList;

public class ViewClassTestsActivity extends AppCompatActivity {

    // region Fields and Instances
    ListView classTestListView;
    TextView classTestTextView;
    TableLayout tableLayout;
    Bundle extras;
    DBHelperSpecific dbHelperSpecific;
    String idString = "0";
    ArrayList<RadioGroup> radioButtons = new ArrayList<RadioGroup>();
    StudentClass studentClass;
    int activeTestID;
    int classId;

    TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT
    );

    // endregion

    public static int CURRENT_CLASS_FOR_CLASS_TESTS_VIEW = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class_tests);

        // region Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_new_test_for_class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTestActivity.class);
                startActivity(intent);
            }
        });
        // endregion

        getExtras();
        inflateViews();
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

    private void inflateViews() {
        classTestTextView = (TextView) findViewById(R.id.class_test_text_test_view);
        classTestListView = (ListView) findViewById(R.id.class_tests_list_veiw_view_class_test);
        tableLayout = (TableLayout) findViewById(R.id.class_tests_table);

        // Intialization
        dbHelperSpecific = new DBHelperSpecific(getApplicationContext());
        classId = Integer.parseInt(extras.getString("CLASS_ID_FOR_TESTS"));

        studentClass = dbHelperSpecific.getStudentClassFromClassIndex(classId);
        activeTestID = studentClass.getActiveTestID();
    }

    private void addTableHeader() {
        TableRow tableHeader = new TableRow(getApplicationContext());
        tableHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));

        tableHeader.setLayoutParams(layoutParams);
        String[] headerText = {"ID", "SUBJECT", "CHAPTER", "SECTIONS", "Questions", "Action", "Status"};
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
        tableLayout.addView(tableHeader);
    }

    private void getExtras() {
        extras = getIntent().getExtras();
    }

    private void attachClassTestsToTable() {

        final Intent intent = getIntent();

        if (extras != null) {
            CURRENT_CLASS_FOR_CLASS_TESTS_VIEW = classId;
            final ArrayList<Test> testArrayList = dbHelperSpecific.getAllTestsRecordsOfClass(extras.getString("CLASS_ID_FOR_TESTS"));
            ArrayList<String> testsArrayListOfClass = new ArrayList<String>();

            for (final Test t : testArrayList) {
                TableRow tableRow = new TableRow(getApplicationContext());
                tableRow.setBackgroundResource(R.drawable.row_border);
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

                // Adding questions action to the row.
                TextView openQuestions = getTextView();
                int questionAdded = dbHelperSpecific.getAllQuestionFromTestId(t.get_id()).size();
                openQuestions.setText(questionAdded + "/" + t.getTotalQuestions());
                if (questionAdded < Integer.parseInt(t.getTotalQuestions())) {
                    openQuestions.setTextColor(0xFFFF0000);
                }
                idString = Integer.toString(t._id);
                openQuestions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "View Questions : " + idString, Toast.LENGTH_SHORT).show();
                        Intent questionsIntent = new Intent(ViewClassTestsActivity.this, ViewTestQuestionsListActivity.class);
                        questionsIntent.putExtra("TEST_ID_FOR_QUESTIONS", Integer.toString(t._id));
                        startActivity(questionsIntent);
                    }
                });
                tableRow.addView(openQuestions);

                // Adding Action to the table
                TextView openTextView = getTextView();
                openTextView.setText("Open");
                idString = Integer.toString(t._id);
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

                // Adding Radio Button
                final RadioGroup radioGroup = new RadioGroup(getApplicationContext());
                final RadioButton status = new RadioButton(getApplicationContext());
                radioGroup.setId(t.get_id());
                if (activeTestID == t.get_id()) {
                    radioGroup.check(status.getId());
                }

                radioButtons.add(radioGroup);
                radioGroup.addView(status);
                int tempTotalQuestionsAdded = dbHelperSpecific.getAllQuestionFromTestId(t.get_id()).size();
                int tempTotalQuestions = Integer.parseInt( t.getTotalQuestions());
                if(tempTotalQuestions!=tempTotalQuestionsAdded){
                    status.setEnabled(false);
                }

                status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        radioGroup.clearCheck();
                        studentClass.setActiveTestID(t.get_id());
                        if (isChecked) {
                            boolean check = dbHelperSpecific.updateStudentClass(studentClass);
                        }
                        for (RadioGroup rg : radioButtons) {
                            if (rg.getId() != t.get_id()) {
                                rg.clearCheck();
                            }
                        }
                        // For going back to calling Student Class List activity
//                        Intent intent = new Intent(getApplicationContext(), ViewStudentClassesListActivity.class);
//                        startActivity(intent);
                    }
                });
                int tempTestID = t.get_id();
                activeTestID = dbHelperSpecific.getStudentClass(studentClass.get_id()).getActiveTestID();
                if (activeTestID == tempTestID) {
//                    radioGroup.check(status.getId());
                }
                tableRow.addView(radioGroup);

                // Adding row to the table
                tableLayout.addView(tableRow);
                testsArrayListOfClass.add(t.toString());
            }

            TestListViewAdapter testListViewAdapter = new TestListViewAdapter(getApplicationContext(), testArrayList);
            ArrayAdapter classTestArrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, testsArrayListOfClass);

            // Adding tests to the list, (Now it is disabled as the table layout is added.)
            // classTestListView.setAdapter(testListViewAdapter);
            classTestTextView.setText("Class: " + classId + "\n Total Tests: " + testArrayList.size() + "\n Active Test ID: ");
            classTestTextView.setGravity(Gravity.CENTER);
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
        tempTextView.setTextColor(0xFF000000);
        tempTextView.setPadding(5, 20, 5, 20);

        return tempTextView;
    }

    private void addActionListener() {

    }
}
