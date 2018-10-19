package com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.Result;

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

import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddExamActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddQuestionActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddStudentActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddTestActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelperSpecific;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.MainActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.Question;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.QuizActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.R;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.Result;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.ResultActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.Student;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.ViewClassesActivity;

import java.util.ArrayList;

public class StudentResultsListActivity extends AppCompatActivity {

    // region Fields and Instances
    int studentID=-1;
    Student student;
    Context context;
    ArrayList<Result> results;
    Bundle extras;
    Intent intent;
    DBHelperSpecific dbHelperSpecific;

    // Layout instances
    TextView studentDetailTextView;
    TableLayout studentResultsTableLayout;
    TableLayout.LayoutParams tableLayoutParams;
    TableRow.LayoutParams rowLayoutParams;
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_results_list);

        getExtras();
        inflateFiedls();
        intializeData();
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

    void getExtras(){
        extras = getIntent().getExtras();
        if(extras!=null){
            studentID = extras.getInt("STUDENT_ID_GLOBAL");
        }
    }

    void inflateFiedls(){
        studentDetailTextView = (TextView) findViewById(R.id.studentDetail_textView_studentResultList);
        studentResultsTableLayout = (TableLayout) findViewById(R.id.studentResults_TableLayout_studentResultsList);
    }

    void intializeData(){
        context = getApplicationContext();
        dbHelperSpecific = new DBHelperSpecific(context);

        if(studentID != -1){
            student = dbHelperSpecific.getStudentFromStudentID(studentID);
            results = dbHelperSpecific.getResultOfStudentID(studentID);

            // Intialize Layout elements
            studentDetailTextView.setText(student.getRollNo()+ ": " + student.getName() + " (" + student.getClassStd() + ")");
            addTable();
        }
    }

    // region Adding data to the table
    void addTable() {
        // Layout parameter
        tableLayoutParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT
        );
        rowLayoutParams = new TableRow.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT
        );

        addTableHeader();
        addTableData();
    }

    void addTableHeader() {
        TableRow tableHeader = new TableRow(getApplicationContext());
        tableHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));

        // Creating table header
        tableHeader.setLayoutParams(rowLayoutParams);
        String[] headerText = {"ID", "Test Detail", "%"};
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
        studentResultsTableLayout.addView(tableHeader);

    }

    void addTableData() {
        for (final Result result : results) {
            TableRow tableRow = new TableRow(getApplicationContext());
            tableRow.setBackgroundResource(R.drawable.row_border);
            tableRow.setLayoutParams(rowLayoutParams);

            // Adding id to the row
            TextView idTextView = getTextView();
            idTextView.setText(Integer.toString(result.get_id()));
            tableRow.addView(idTextView);

            // Adding Question to the row
            TextView testIDTextView = getTextView();
            testIDTextView.setText(Integer.toString(result.getTest_id()));
            tableRow.addView(testIDTextView);

            // Adding Question to the row
            TextView percentageTextView = getTextView();
            percentageTextView.setText(result.getResultPercentage());
            float tempPercentageFloat = Float.parseFloat(result.getResultPercentage());
            int tempPercentage = (int)tempPercentageFloat;
            if(tempPercentage<80){
                percentageTextView.setBackgroundColor(Color.RED);
                percentageTextView.setTextColor(Color.YELLOW);
            } else if(tempPercentage>=80 && tempPercentage <100){
                percentageTextView.setBackgroundColor(Color.YELLOW);
                percentageTextView.setTextColor(Color.RED);
            }else {
                percentageTextView.setBackgroundColor(Color.GREEN);
            }
            percentageTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(context, ResultActivity.class);
                    intent.putExtra("RESULT_ID", result.get_id());
                    startActivity(intent);
                }
            });
            tableRow.addView(percentageTextView);

//            // Adding Action action to the row.
//            TextView seletedAnswer = getTextView();
//            ArrayList<String> theResult = getAnsweredOptionOfQuestion(q);
//            seletedAnswer.setText(theResult.get(1));
//            if (theResult.get(0) == "0") {
//                seletedAnswer.setBackgroundColor(Color.RED);
//            } else {
//                seletedAnswer.setBackgroundColor(Color.GREEN);
//            }
//            tableRow.addView(seletedAnswer);

            // Adding row to the table
            studentResultsTableLayout.addView(tableRow);
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

    // endregion


}
