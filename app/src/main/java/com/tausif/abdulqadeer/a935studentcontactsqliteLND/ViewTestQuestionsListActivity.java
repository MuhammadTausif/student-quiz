package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class ViewTestQuestionsListActivity extends AppCompatActivity {

    Bundle extras;
    String testIdString;
    TextView testDetail, questionsCount;
    TableView<String[]> tableView;
    DBHelperSpecific dbHelperSpecific;
    ArrayList<Question> questions;
    Test test;
    TableLayout questionsListTableViewTestQuestions;
    TableLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test_questions_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Getting extras
        getExtrasFromCallingActivity();

        // Inflate Veiws
        inflateAndIntializeFeilds();


        // Adding questions data to the table
        addTableHeader();
        attachQuestionToTable();

        // Adding global data
        addingGlobalData();

        // Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.new_fab_view_test_questions);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Test No: " + testIdString, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent testInent = new Intent(ViewTestQuestionsListActivity.this, AddQuestionActivity.class);
                String test_id = Integer.toString(test.get_id());
                testInent.putExtra("TEST_ID_FOR_NEW_QUESTION", test_id);
                startActivity(testInent);

            }
        });
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

    /**
     * Getting extras from calling activity
     */
    private void getExtrasFromCallingActivity() {
        extras = getIntent().getExtras();
        if (extras != null) {
            testIdString = extras.getString("TEST_ID_FOR_QUESTIONS");
        } else {
            testIdString = "-1";
        }
    }

    /**
     * Inflate Feilds
     */
    void inflateAndIntializeFeilds() {

        // Test and Questions detail
        testDetail = (TextView) findViewById(R.id.test_detail_textView_view_test_questions);
        questionsCount = (TextView) findViewById(R.id.questions_count_textView_view_test_questions);
        questionsListTableViewTestQuestions = (TableLayout) findViewById(R.id.questionsList_table_view_test_questions);

        // Intializing data
        dbHelperSpecific = new DBHelperSpecific(getApplicationContext());
        questions = dbHelperSpecific.getAllQuestionFromTestId(Integer.parseInt(testIdString));
        test = dbHelperSpecific.getAllTestsRecords(" WHERE " + DBHelper.ID_TEST_TABLE + " = " + testIdString).get(0);

        // Setting text for test and questions
        testDetail.setText(" Test Detail: ( ID : " + test.get_id() + ", Class "
                + test.classTest + ", " +
                test.getSubject() +
                ", Ch # " + test.getChapter() +
                ", Sec # " + test.getSections() + " )");
        questionsCount.setText("Questions: " + questions.size() + "/" + test.getTotalQuestions());

//        // Test Table data
//        String[] TABLE_HEADERS = { "This", "is", "a", "header" };
//        String[][] DATA_TO_SHOW = {{"This", "is", "a", "test"},
//                {"and", "a", "second", "test"}};
//        tableView = (TableView<String[]>) findViewById(R.id.tableView_for_test);
//        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
//        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));
//
        layoutParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT
        );
    }

    void addTableHeader() {
        TableRow tableHeader = new TableRow(getApplicationContext());
        tableHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));

        tableHeader.setLayoutParams(layoutParams);
        String[] headerText = {"ID", "QUESTION", "ANSWER", "Action"};
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
        questionsListTableViewTestQuestions.addView(tableHeader);
    }

    private void attachQuestionToTable() {
        if (extras != null) {
            for (final Question q : questions) {
                TableRow tableRow = new TableRow(getApplicationContext());
                tableRow.setBackgroundColor(Color.BLACK);
                tableRow.setLayoutParams(layoutParams);

                // Adding id to the row
                TextView idTextView = getTextView();
                idTextView.setText(Integer.toString(q.getQuestionId()));
                tableRow.addView(idTextView);

                // Adding Question to the row
                TextView subjectTextView = getTextView();
                subjectTextView.setText(q.getQuestion());
                tableRow.addView(subjectTextView);

                // Adding Answer to the row
                TextView chapterTextView = getTextView();
                chapterTextView.setText(q.getOptionA());
                tableRow.addView(chapterTextView);


                // Adding Action action to the row.
                TextView openQuestions = getTextView();
                openQuestions.setText("Open");
                openQuestions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getApplicationContext(), "View Questions : " + q.getQuestionId(), Toast.LENGTH_SHORT).show();
                        Intent questionsDeleteUpdateIntent = new Intent(ViewTestQuestionsListActivity.this, UpdateDeleteQuestionActivity.class);
                        questionsDeleteUpdateIntent.putExtra("QUESTION_ID_FOR_UPDATE_DELETE", Integer.toString(q.getQuestionId()));
                        questionsDeleteUpdateIntent.putExtra("TEST_ID_FOR_UPDATE_DELETE", Integer.toString(test.get_id()));
                        startActivity(questionsDeleteUpdateIntent);
                    }
                });
                tableRow.addView(openQuestions);

                // Adding row to the table
                questionsListTableViewTestQuestions.addView(tableRow);
            }

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
        tempTextView.setPadding(5, 20, 5, 20);

        return tempTextView;
    }

    /**
     * Adding global data for tests and questions
     */
    void addingGlobalData() {

    }
}
