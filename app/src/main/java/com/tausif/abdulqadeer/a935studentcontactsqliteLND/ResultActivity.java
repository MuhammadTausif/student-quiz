package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.StudentClasses.UpdateDeleteStudentClassActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.StudentClasses.ViewStudentClassesListActivity;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    // region Fields and Instances
    // Data instances
    int examID;
    Exam exam;
    Bundle extras;
    Result result;
    Test test;
    Student student;
    StudentClass studentClass;
    ArrayList<QuestionResult> questionsResult;
    ArrayList<Question> questions;

    // Reference instances
    Intent intent;
    Context context;
    DBHelperSpecific dbHelperSpecific;

    // Layout fields
    TextView studentDetailTextView, testDetailTextView, resultSummaryTextVeiw;
    TableLayout resultTable;
    TableLayout.LayoutParams tableLayoutParams;
    TableRow.LayoutParams rowLayoutParams;

    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getExtras();
        inflateFieldsAndIntialization();
        addTable();
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

    void getExtras() {
        extras = getIntent().getExtras();
        if (extras != null) {
            examID = extras.getInt("RESULT_ID");
        }
    }

    void inflateFieldsAndIntialization() {
        context = getApplicationContext();
        dbHelperSpecific = new DBHelperSpecific(context);

        // getting result of exam, test, questions, student etc
        exam = dbHelperSpecific.getExamFromExamID(examID);
        result = dbHelperSpecific.getResultOfExamID(examID);
        test = dbHelperSpecific.getTestFromID(result.getTest_id());
        student = dbHelperSpecific.getStudentFromStudentID(result.getStudent_id());
        questions = dbHelperSpecific.getAllQuestionFromTestId(test.get_id());
        questionsResult = ManipulateResult.manipulateResult(result);

        // Layout parameter
        tableLayoutParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT
        );
        rowLayoutParams = new TableRow.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT
        );

        // Inflating the layouts views
        studentDetailTextView = (TextView) findViewById(R.id.studentDetail_textView_result);
        testDetailTextView = (TextView) findViewById(R.id.testDetail_textView_result);
        resultSummaryTextVeiw = (TextView) findViewById(R.id.resultSummary_textView_result);
        resultTable = (TableLayout) findViewById(R.id.resultDetail_table_result);

        updatingTextViews();
    }

    void updatingTextViews() {
        studentDetailTextView.setText(student.getRollNo() + ": " + student.getName() + " (" + student.getClassStd() + ")");
        testDetailTextView.setText(test.getSubject() + " Ch# " + test.getChapter() + ", Sec#" + test.getSections());
        int[] currentResultSummary = getResultSummary();
        if(currentResultSummary[3]<100){
            resultSummaryTextVeiw.setBackgroundColor(Color.RED);
            resultSummaryTextVeiw.setTextColor(Color.YELLOW);
            resultSummaryTextVeiw.setPadding(5,10,5,10);
        }else {
            resultSummaryTextVeiw.setBackgroundColor(Color.GREEN);
        }
        resultSummaryTextVeiw.setText( "Result No: " + result.get_id() + ", Time: " + result.getDateTime() + "\n" + "Summary: " + currentResultSummary[0] +"/"+ currentResultSummary[2] + ", " + currentResultSummary[3]+"%");
    }

    // region Adding data to the table
    void addTable() {
        addTableHeader();
        addTableData();
    }

    void addTableHeader() {
        TableRow tableHeader = new TableRow(getApplicationContext());
        tableHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));

        // Creating table header
        tableHeader.setLayoutParams(rowLayoutParams);
        String[] headerText = {"ID", "Question", "Answer", "Your Answer"};
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
        resultTable.addView(tableHeader);

    }

    void addTableData() {
        for (final Question q : questions) {
            TableRow tableRow = new TableRow(getApplicationContext());
            tableRow.setBackgroundResource(R.drawable.row_border);
            tableRow.setLayoutParams(rowLayoutParams);

            // Adding id to the row
            TextView idTextView = getTextView();
            idTextView.setText(Integer.toString(q.getQuestionId()));
            tableRow.addView(idTextView);

            // Adding Question to the row
            TextView questionTextView = getTextView();
            questionTextView.setText(q.getQuestion());
            tableRow.addView(questionTextView);

            // Adding Answer to the row
            TextView correctOptionTextView = getTextView();
            correctOptionTextView.setText(q.getOptionA());
            correctOptionTextView.setMaxWidth(100);
            tableRow.addView(correctOptionTextView);


            // Adding Action action to the row.
            TextView seletedAnswer = getTextView();
            ArrayList<String> theResult = getAnsweredOptionOfQuestion(q);
            seletedAnswer.setMaxWidth(100);
            seletedAnswer.setText(theResult.get(1));
            if (theResult.get(0) == "0") {
                seletedAnswer.setBackgroundColor(Color.RED);
            } else {
                seletedAnswer.setBackgroundColor(Color.GREEN);
            }
            tableRow.addView(seletedAnswer);

            // Adding row to the table
            resultTable.addView(tableRow);
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
        tempTextView.setMaxWidth(300);
        tempTextView.setGravity(Gravity.CENTER);
        tempTextView.setTextSize(16);
        tempTextView.setPadding(5, 20, 5, 20);
        tempTextView.setTextColor(0xFF000000);

        return tempTextView;
    }

    // endregion

    int[] getResultSummary() {
        int totalQuestions = questions.size();
        int correctAnsweredQuestion = 0;
        int percentage = 0;

        for (Question question : questions) {
            if (getAnsweredOptionOfQuestion(question).get(0) == "1") {
                correctAnsweredQuestion++;
            }
        }

        int wrongAttemptedQuestion = totalQuestions - correctAnsweredQuestion;
         percentage =  (int)( 100 * (float)((float)correctAnsweredQuestion / (float)totalQuestions));
        int[] resultSummary = {correctAnsweredQuestion, wrongAttemptedQuestion, totalQuestions, percentage};
        return resultSummary;
    }

    ArrayList<String> getAnsweredOptionOfQuestion(Question question) {
        ArrayList<String> theResult = new ArrayList<String>();
        String selectedOption = null;
        String selectedOptionText = null;
        String status = "0";
        for (QuestionResult questionResult :
                questionsResult) {
            if (question.getQuestionId() == questionResult.getQuestionID()) {
                selectedOption = questionResult.getSelectedOption();
                switch (selectedOption) {
                    case "A":
                        selectedOptionText = question.getOptionA();
                        status = "1";
                        break;
                    case "B":
                        selectedOptionText = question.getOptionB();
                        break;
                    case "C":
                        selectedOptionText = question.getOptionC();
                        break;
                    case "D":
                        selectedOptionText = question.getOptionD();
                        break;
                }
            }
        }
        theResult.add(status);
        theResult.add(selectedOptionText);
        theResult.add(selectedOption);

        return theResult;
    }
}





