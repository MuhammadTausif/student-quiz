package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddQuestionActivity extends AppCompatActivity {

    Spinner classesQuestionSpinner, subjectQuestionSpinner, chapterQuestionSpinner, sectionsQuestionSpinner;
    EditText question, optionA, optionB, optionC, optionD;
    Button saveQuestion;
    static int question_test_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        inflateFields();
        loadingSpinners();
        addListener();

//        testMethod();
    }

    private void loadingSpinners() {
        DBHelperSpecific dbHelperSpecific = new DBHelperSpecific(AddQuestionActivity.this);
        ArrayList<String> classesList = dbHelperSpecific.getDistinctTestClasses();

        ArrayAdapter<String> classesDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classesList);
        classesDataAdapter.setDropDownViewResource(android.R.layout.select_dialog_multichoice);

        classesQuestionSpinner.setAdapter(classesDataAdapter);
    }

    private void addListener() {

        classesQuestionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DBHelperSpecific dbHelperSpecific = new DBHelperSpecific(AddQuestionActivity.this);

                ArrayList<String> classesList = dbHelperSpecific.getDistinctTestSubjects(Integer.parseInt(String.valueOf(adapterView.getSelectedItem())));

                ArrayAdapter<String> classesDataAdapter = new ArrayAdapter<String>(AddQuestionActivity.this, android.R.layout.simple_spinner_item, classesList);
                classesDataAdapter.setDropDownViewResource(android.R.layout.select_dialog_multichoice);

                subjectQuestionSpinner.setAdapter(classesDataAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subjectQuestionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DBHelperSpecific dbHelperSpecific = new DBHelperSpecific(AddQuestionActivity.this);

                String classTest = classesQuestionSpinner.getSelectedItem().toString();

                ArrayList<String> classesList = dbHelperSpecific.getDistinctTestChapters(classTest, String.valueOf(adapterView.getSelectedItem()));

                ArrayAdapter<String> classesDataAdapter = new ArrayAdapter<String>(AddQuestionActivity.this, android.R.layout.simple_spinner_item, classesList);
                classesDataAdapter.setDropDownViewResource(android.R.layout.select_dialog_multichoice);

                chapterQuestionSpinner.setAdapter(classesDataAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        chapterQuestionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DBHelperSpecific dbHelperSpecific = new DBHelperSpecific(AddQuestionActivity.this);

                String classTest = classesQuestionSpinner.getSelectedItem().toString();
                String subjectTest = subjectQuestionSpinner.getSelectedItem().toString();

                ArrayList<String> sectionsList = dbHelperSpecific.getDistinctTestSections(classTest, subjectTest, String.valueOf(adapterView.getSelectedItem()));

                ArrayAdapter<String> sectionsDataAdapter = new ArrayAdapter<String>(AddQuestionActivity.this, android.R.layout.simple_spinner_item, sectionsList);
                sectionsDataAdapter.setDropDownViewResource(android.R.layout.select_dialog_multichoice);

                sectionsQuestionSpinner.setAdapter(sectionsDataAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sectionsQuestionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DBHelperSpecific dbHelperSpecific = new DBHelperSpecific(AddQuestionActivity.this);

                String classTest = classesQuestionSpinner.getSelectedItem().toString();
                String subjectTest = subjectQuestionSpinner.getSelectedItem().toString();
                String chapterTest = chapterQuestionSpinner.getSelectedItem().toString();
                String sectionsTest = sectionsQuestionSpinner.getSelectedItem().toString();

                int testIdQuestion = dbHelperSpecific.getTestId(classTest, subjectTest, chapterTest, sectionsTest);
                TextView testIdTextView = (TextView) findViewById(R.id.testIdTextView);
                testIdTextView.setText("Test ID: " + testIdQuestion);

                question_test_id = testIdQuestion;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private boolean validateQuestionsInput() {
        if (question.length() < 1 || optionA.length() < 1 ||
                optionB.length() < 1 || optionC.length() < 1 ||
                optionD.length() < 1 || question_test_id < 0) {
            return false;
        }
        return true;
    }

    private void inflateFields() {
        classesQuestionSpinner = (Spinner) findViewById(R.id.classSpinner);
        subjectQuestionSpinner = (Spinner) findViewById(R.id.subjectSpinner);
        chapterQuestionSpinner = (Spinner) findViewById(R.id.chapterSpinner);
        sectionsQuestionSpinner = (Spinner) findViewById(R.id.sectionsSpinner);

        question = (EditText) findViewById(R.id.editTextQuestion);
        optionA = (EditText) findViewById(R.id.editTextOptionA);
        optionB = (EditText) findViewById(R.id.editTextOptionB);
        optionC = (EditText) findViewById(R.id.editTextOptionC);
        optionD = (EditText) findViewById(R.id.editTextOptionD);

        Button saveQuestion = (Button) findViewById(R.id.saveQuestionButton);
        saveQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateQuestionsInput()) {
                    DBHelper dbHelper=new DBHelper(AddQuestionActivity.this);

                    int tempTestID=question_test_id;
                    String tempQuestion=question.getText().toString();
                    String tempOptionA=optionA.getText().toString();
                    String tempOptionB=optionB.getText().toString();
                    String tempOptionC=optionC.getText().toString();
                    String tempOptionD=optionD.getText().toString();

                    dbHelper.insertQuestion(tempTestID, tempQuestion, tempOptionA, tempOptionB, tempOptionC, tempOptionD);
                    AlertMessage.ShowAlertMessage(AddQuestionActivity.this, "New question added");
                } else {
                    AlertMessage.ShowAlertMessage(AddQuestionActivity.this, "Please select test and enter question along with options.");
                }
            }
        });
    }

    private void testMethod() {
        NumberPicker numberPicker1;

        numberPicker1 = (NumberPicker) findViewById(R.id.numberPicker1);
        final String genders[] = {"unassessed", "Skipped", "Incorrect", "Correct", "1 mark"};

        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(genders.length - 1);
        numberPicker1.setDisplayedValues(genders);
        numberPicker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        NumberPicker.OnValueChangeListener myValChangedListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                btnBar.setText("Value: " + genders[newVal]);
                Toast.makeText(AddQuestionActivity.this, "Value: " + genders[newVal], Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder_test = new AlertDialog.Builder(AddQuestionActivity.this);
                builder_test.setMessage(genders[newVal]);
                AlertDialog dialog_test = builder_test.create();
                dialog_test.show();
            }
        };

        numberPicker1.setOnValueChangedListener(myValChangedListener);

        AlertDialog.Builder builder = new AlertDialog.Builder(AddQuestionActivity.this);
        String[] ar = {"abc", "cdf", "adf", "hif"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddQuestionActivity.this, android.R.layout.simple_spinner_item, ar);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        builder.setTitle("Test Dialog");
//        builder.setMessage("This is message");
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(AddQuestionActivity.this, "Dialog", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();

        // Second Custom Dialog Builder
        AlertDialog.Builder builder_c = new AlertDialog.Builder(AddQuestionActivity.this);
        builder_c.setTitle("Custom Dialog")
                .setItems(R.array.myColors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog_c = builder_c.create();
        dialog_c.show();

        // Third Custom Dialog Builder
        AlertDialog.Builder builder_3 = new AlertDialog.Builder(AddQuestionActivity.this);
        builder_3.setTitle("Custom Dialog")
                .setMultiChoiceItems(R.array.myColors, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                    }
                });
        AlertDialog dialog_3 = builder_3.create();
        dialog_3.show();
    }
}