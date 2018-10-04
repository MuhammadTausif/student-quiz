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

public class UpdateDeleteTestActivity extends AppCompatActivity {

    // Test to be updated or deleted
    Test test;
    // Columns of TEST_TABLE
    TextView id_test_table;
    EditText class_test;
    EditText subject;
    EditText chapter;
    EditText sections;
    EditText dataTime;
    EditText totalTime;
    EditText totalQuestions;

    // Button for delete and Update
    Button updateButton;
    Button deleteButton;

    // DB instance
    DBHelperSpecific dbHelperSpecific;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_test);

        // Inflating the feilds of XML layout
        inflateFeilds();

        // Getting test by id.
        getTestById();

        // Update the feilds
        updateFeilds();

        // Adding action listener to the Buttons
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

    /**
     * Method for feilds inflation from the XML layout.
     */
    void inflateFeilds() {
        // Columns of TEST_TABLE
        id_test_table = (TextView) findViewById(R.id.id_textView_update_delete_test);
        class_test = (EditText) findViewById(R.id.class_editText_update_delete_test);
        subject = (EditText) findViewById(R.id.subject_editText_update_delete_test);
        chapter = (EditText) findViewById(R.id.chapter_editText_update_delete_test);
        sections = (EditText) findViewById(R.id.section_editText_update_delete_test);
        dataTime = (EditText) findViewById(R.id.date_editText_update_delete_test);
        totalTime = (EditText) findViewById(R.id.totalTime_editText_update_delete_test);
        totalQuestions = (EditText) findViewById(R.id.totalQuestion_editText_update_delete_test);

        // Button for deleteButton and Update
        updateButton = (Button) findViewById(R.id.update_button_update_delete_test);
        deleteButton = (Button) findViewById(R.id.delete_button_update_delete_test);

        // Intialize the DB
        dbHelperSpecific = new DBHelperSpecific(getApplicationContext());
    }

    /**
     * @return Test
     * Used to get the selected test.
     */
    void getTestById() {
        String tempTestId = "Not assigned";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tempTestId = extras.getString("TEST_ID_OF_CLASS");

            ArrayList<Test> testArrayList = dbHelperSpecific.getAllTestsRecords(" WHERE " + DBHelper.ID_TEST_TABLE + " = " + tempTestId);
            test = testArrayList.get(0);
            Toast.makeText(getApplicationContext(),
                    "\n***Test Detail***\n" +
                            "ID: " + test._id + " \n" +
                            "Class: " + test.classTest + "\n" +
                            "Subject: " + test.subject,
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Updating the edit feilds in XML.
     */
    void updateFeilds() {
        if (test != null) {
            id_test_table.setText( Integer.toString(test._id));
            class_test.setText(test.classTest);
            subject.setText(test.subject);
            chapter.setText(test.chapter);
            sections.setText(test.sections);
            dataTime.setText(test.dataTime);
            totalTime.setText(test.totalTime);
            totalQuestions.setText(test.totalQuestions);
        }
    }

    /**
     * Add action listener to the Buttons
     */
    void addActionListener(){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setClassTest(class_test.getText().toString());
                test.setSubject(subject.getText().toString());
                test.setChapter(chapter.getText().toString());
                test.setSections(sections.getText().toString());
                test.setDataTime(dataTime.getText().toString());
                test.setTotalTime(totalTime.getText().toString());
                test.setTotalQuestions(totalQuestions.getText().toString());

                if(dbHelperSpecific.updateTest(test)){
                    Toast.makeText(getApplicationContext(), "Test is Updated.", Toast.LENGTH_SHORT).show();
                    Intent updateDeleteIntent = new Intent(UpdateDeleteTestActivity.this, ViewClassTestsActivity.class);
                    updateDeleteIntent.putExtra("CLASS_ID_FOR_TESTS", class_test.getText().toString());
                    startActivity(updateDeleteIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Sorry, Could not Updated the Test.", Toast.LENGTH_SHORT).show();
                    Intent updateDeleteIntent = new Intent(UpdateDeleteTestActivity.this, ViewClassTestsActivity.class);
                    updateDeleteIntent.putExtra("CLASS_ID_FOR_TESTS", class_test.getText().toString());
                    startActivity(updateDeleteIntent);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Creating Alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDeleteTestActivity.this);

                // Setting the title, message, and action to delete or go back.
                builder.setTitle("Delete Test")
                        .setMessage("Are you sure to delete the Test?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dbHelperSpecific.deleteTest(test)) {
                                    Toast.makeText(getApplicationContext(), "Test is Deleted.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Sorry, test can't be deleted.", Toast.LENGTH_SHORT).show();
                                }
                                Intent updateDeleteIntent = new Intent(UpdateDeleteTestActivity.this, ViewClassTestsActivity.class);
                                updateDeleteIntent.putExtra("CLASS_ID_FOR_TESTS", class_test.getText().toString());
                                startActivity(updateDeleteIntent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent updateDeleteIntent = new Intent(UpdateDeleteTestActivity.this, ViewClassTestsActivity.class);
                                String classID = class_test.getText().toString();
                                updateDeleteIntent.putExtra("CLASS_ID_FOR_TESTS", class_test.getText().toString());
                                startActivity(updateDeleteIntent);
                                Toast.makeText(getApplicationContext(), " Back to Update or Delete activit", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Create Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the dialog
                alertDialog.show();
            }
        });
    }
}
