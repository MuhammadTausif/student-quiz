package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateDeleteQuestionActivity extends AppCompatActivity {

    Bundle extras;
    TextView questionDetail;
    EditText questionText, optionA, optionB, optionC, optionD;
    Button update, delete, back;
    DBHelperSpecific dbHelperSpecific;
    String questionIdString;
    String testIdString;
    Question question;
    Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_question);

        // Getting extras
        getEtrasFromCallingActivity();

        // Inflating feilds
        inflateFeildsAndIntialize();

        // Adding action listener to the events
        addingActionListener();
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
     * Get Question Id from calling activity
     */
    void getEtrasFromCallingActivity() {
        extras = getIntent().getExtras();
        if (extras != null) {
            questionIdString = extras.getString("QUESTION_ID_FOR_UPDATE_DELETE");
            testIdString = extras.getString("TEST_ID_FOR_UPDATE_DELETE");
        } else {
            questionIdString = "-1";
        }


    }

    /**
     * Inflating Feilds And Intializeing
     */
    void inflateFeildsAndIntialize() {
        // Inflating feilds
        TextView questionDetail = (TextView) findViewById(R.id.question_detail_textView_update_delete_question);
        questionText = (EditText) findViewById(R.id.questionText_editText_update_delete_question);
        optionA = (EditText) findViewById(R.id.optionA_editText_update_delete_question);
        optionB = (EditText) findViewById(R.id.optionB_editText_update_delete_question);
        optionC = (EditText) findViewById(R.id.optionC_editText_update_delete_question);
        optionD = (EditText) findViewById(R.id.optionD_editText_update_delete_question);

        delete = (Button) findViewById(R.id.delete_button_update_delete_question);
        update = (Button) findViewById(R.id.update_button_update_delete_question);
        back = (Button) findViewById(R.id.back_button_update_delete_question);

        // intialize feilds
        dbHelperSpecific = new DBHelperSpecific(getApplicationContext());
        test = dbHelperSpecific.getAllTestsRecords(" WHERE " + DBHelper.ID_TEST_TABLE + " = " + testIdString).get(0);

        if (questionIdString != "-1") {
            int tempQuestionID = Integer.parseInt(questionIdString);
            question = dbHelperSpecific.getQuestionFromQuestionId(tempQuestionID);

            questionDetail.setText("Test Detail : ( Class " + test.getClassTest() + ", " + test.getSubject() + ", Chapter # " + test.getChapter() + ", Sections # " + test.getSections() + " )\n" +
                    "Question ID: " + question.getQuestionId() +
                    "\n ");
            questionText.setText(question.getQuestion());
            optionA.setText(question.getOptionA());
            optionB.setText(question.getOptionB());
            optionC.setText(question.getOptionC());
            optionD.setText(question.getOptionD());
        } else {
            questionDetail.setText("There is no question!");
        }

    }

    /**
     * Adding Action Listener
     */
    void addingActionListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Back to Questions", Toast.LENGTH_SHORT).show();
                goBack();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating Alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDeleteQuestionActivity.this);

                // Setting the title, message, and action to delete or go back.
                builder.setTitle("Delete Test")
                        .setMessage("Are you sure to delete the Test?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dbHelperSpecific.deleteQuestion(question)) {
                                    Toast.makeText(getApplicationContext(), "Test is Deleted.", Toast.LENGTH_SHORT).show();
                                    goBack();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Sorry, test can't be deleted.", Toast.LENGTH_SHORT).show();
                                }
                                goBack();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                goBack();
                                Toast.makeText(getApplicationContext(), " Back to Update or Delete activit", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Create Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the dialog
                alertDialog.show();
                Toast.makeText(getApplicationContext(), "Delete is clicked.", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setQuestion(questionText.getText().toString());
                question.setOptionA(optionA.getText().toString());
                question.setOptionB(optionB.getText().toString());
                question.setOptionC(optionC.getText().toString());
                question.setOptionD(optionD.getText().toString());

                if(dbHelperSpecific.updateQuestion(question)){
                    Toast.makeText(getApplicationContext(), "Question ID: " + question.getQuestionId() + " Deleted", Toast.LENGTH_SHORT).show();
                    goBack();
                };
            }
        });

    }

    void goBack() {
        Intent questionListIntent = new Intent(getApplicationContext(), ViewTestQuestionsListActivity.class);
        questionListIntent.putExtra("TEST_ID_FOR_QUESTIONS", testIdString);
        startActivity(questionListIntent);
    }

}
