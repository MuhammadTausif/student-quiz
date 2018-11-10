package com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.StudentTests;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.StudentClasses.ViewStudentClassesListActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddExamActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddQuestionActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddStudentActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddTestActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelperSpecific;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.QuizActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.R;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.ViewClassTestsActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.ViewClassesActivity;

import java.util.Calendar;

public class AddStudentTestActivity extends AppCompatActivity {

    EditText studentClassEditText, chapterEditText, sectionsEditText, dataTimeEditText, totalTimeEditText, totalQuestionsEditText;
    Spinner stdClassSpinner, subjectSpinner;
    Button selectDateButton, saveTestButton;
    private int mDay, mMonth, mYear;
    Bundle extras;
    int studentClassID = -1;
    int studentClassSerialNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_test);

        getExtraFromCallingActivity();
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
//                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent = new Intent(getApplicationContext(), ViewStudentClassesListActivity.class);
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

    void getExtraFromCallingActivity() {
        extras = getIntent().getExtras();
        if (extras != null) {
            studentClassID = extras.getInt("STUDENT_CLASS_ID");
        }
    }

    private void inflateViews() {
        // Inflating textViews
        studentClassEditText = (EditText) findViewById(R.id.studentClass_editText_addStudentTest);
        chapterEditText = (EditText) findViewById(R.id.chapter_editText_addStudentTest);
        sectionsEditText = (EditText) findViewById(R.id.sections_editText_addStudentTest);
        dataTimeEditText = (EditText) findViewById(R.id.dateTime_editText_addStudentTest);
        totalTimeEditText = (EditText) findViewById(R.id.totalTime_editText_addStudentTest);
        totalQuestionsEditText = (EditText) findViewById(R.id.totalQuestions_editText_addStudentTest);

        // Inflating spinner
        subjectSpinner = (Spinner) findViewById(R.id.subject_spinner_addStudentTest);

        // Inflating Button
        selectDateButton = (Button) findViewById(R.id.selectDate_button_addStudentTest);
        saveTestButton = (Button) findViewById(R.id.save_button_addStudentTest);

        DBHelperSpecific dbHelperSpecific = new DBHelperSpecific(getApplicationContext());
        studentClassSerialNo = dbHelperSpecific.getStudentClass(studentClassID).getSrialNo();
        studentClassEditText.setText(Integer.toString(studentClassSerialNo));
    }

    private void loadingSpinners() {

        String[] subjectStrings = {"Select Subject", "Urdu", "Punjabi", "English", "Math", "Science"};

        ArrayAdapter<String> subjectDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectStrings);
        subjectSpinner.setAdapter(subjectDataAdapter);
    }

    private void addListner() {
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                mMonth = calendar.get(Calendar.MONTH);
                mYear = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddStudentTestActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                dataTimeEditText.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });


        saveTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    // Getting values
                    int classInt = Integer.parseInt(String.valueOf(studentClassEditText.getText().toString()));
                    String subjectString = String.valueOf(subjectSpinner.getSelectedItem());
                    int chapterInt = Integer.parseInt(chapterEditText.getText().toString());
                    String sectionsString = sectionsEditText.getText().toString();
                    String dateString = dataTimeEditText.getText().toString();
                    int totalTimeInt = Integer.parseInt(totalTimeEditText.getText().toString());
                    int totalQuestionsInt = Integer.parseInt(totalQuestionsEditText.getText().toString());


                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    if (dbHelper.insertTest(classInt, subjectString, chapterInt, sectionsString, dateString, totalTimeInt, totalQuestionsInt)) {
                        Toast.makeText(getApplicationContext(), "New test added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ViewClassTestsActivity.class);
                        String tempStudentClassSerialNo = Integer.toString(studentClassSerialNo);
                        intent.putExtra("CLASS_ID_FOR_TESTS", tempStudentClassSerialNo);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Not added", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddStudentTestActivity.this, "Please select all feilds", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validateInput() {
        if (Integer.parseInt(
                String.valueOf(studentClassEditText.getText().toString())) == -1 ||
                studentClassEditText.length() < 1 ||
                String.valueOf(subjectSpinner.getSelectedItem()) == "Select Subjcet" ||
                chapterEditText.length() < 1 ||
                sectionsEditText.length() < 1 ||
                dataTimeEditText.length() < 1 ||
                totalTimeEditText.length() < 1 ||
                totalQuestionsEditText.length() < 1) {
            return false;
        }
        return true;
    }
}
