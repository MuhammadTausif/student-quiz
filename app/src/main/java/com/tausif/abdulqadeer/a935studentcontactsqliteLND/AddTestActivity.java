package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class AddTestActivity extends AppCompatActivity {

    EditText chapter, sections, dataTime, totalTime, totalQuestions;
    Spinner stdClass, subject;
    Button selectDate, saveTest;
    private int mDay, mMonth, mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        inflateViews();
        loadingSpinners();
        addListner();
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
        // Inflating textViews
        chapter = (EditText) findViewById(R.id.chapterEditText);
        sections = (EditText) findViewById(R.id.sectionsEditText);
        dataTime = (EditText) findViewById(R.id.dateTimeEditText);
        totalTime = (EditText) findViewById(R.id.totalTimeEditText);
        totalQuestions = (EditText) findViewById(R.id.totalQuestionsEditText);

        // Inflating spinner
        stdClass = (Spinner) findViewById(R.id.classSpinner);
        subject = (Spinner) findViewById(R.id.subjectSpinner);

        // Inflating Button
        selectDate = (Button) findViewById(R.id.selectDateButton);
        saveTest = (Button) findViewById(R.id.saveButton);
    }

    private void loadingSpinners() {
        Integer[] classes = {-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ArrayAdapter<Integer> classesDataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, classes);
        classesDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stdClass.setAdapter(classesDataAdapter);

        String[] subjectStrings = {"Select Subject", "Urdu", "Punjabi", "English", "Math", "Science"};

        ArrayAdapter<String> subjectDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectStrings);
        classesDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        subject.setAdapter(subjectDataAdapter);
    }

    private void addListner() {
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                mMonth = calendar.get(Calendar.MONTH);
                mYear = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTestActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                dataTime.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });


        saveTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                // Getting values
                int classInt = Integer.parseInt(String.valueOf(stdClass.getSelectedItem()));
                String subjectString = String.valueOf(subject.getSelectedItem());
                int chapterInt = Integer.parseInt(chapter.getText().toString());
                String sectionsString = sections.getText().toString();
                String dateString = dataTime.getText().toString();
                int totalTimeInt = Integer.parseInt(totalTime.getText().toString());
                int totalQuestionsInt = Integer.parseInt(totalQuestions.getText().toString());


                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    if (dbHelper.insertTest(classInt, subjectString, chapterInt, sectionsString, dateString, totalTimeInt, totalQuestionsInt)) {
                        Toast.makeText(getApplicationContext(), "New test added", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Not added", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(AddTestActivity.this, "Please select all feilds", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validateInput() {
        if (Integer.parseInt(String.valueOf(stdClass.getSelectedItem())) == -1 ||
                String.valueOf(subject.getSelectedItem()) == "Select Subjcet" ||
                chapter.length() < 1 ||
                sections.length() < 1 ||
                dataTime.length() < 1 ||
                totalTime.length() < 1 ||
                totalQuestions.length() < 1) {
            return false;
        }
        return true;
    }
}