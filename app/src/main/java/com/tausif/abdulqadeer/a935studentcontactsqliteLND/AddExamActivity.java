package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.StudentClasses.ViewStudentClassesListActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddExamActivity extends AppCompatActivity {

    Spinner classExam, studentExam, subjectExam, chapterExam, sectionsExam;
    Button startFromExamQuizButton;
    TextView studentDetailForExam, testDetailForExam;
    static int studentIdForQuizStartPass = -1;
    static int testIdForQuizStartPass = -1;
    static String studentNamePass = "Not selected.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        inflateFields();
        addListener();
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

    // endregion

    private void inflateFields() {
        classExam = (Spinner) findViewById(R.id.select_class_for_exam_spinner);
        studentExam = (Spinner) findViewById(R.id.select_student_for_exam_spinner);
        subjectExam = (Spinner) findViewById(R.id.select_subject_for_student_exam_spinner);
        chapterExam = (Spinner) findViewById(R.id.select_chapter_for_student_exam_spinner);
        sectionsExam = (Spinner) findViewById(R.id.select_sections_for_student_exam_spinner);

        studentDetailForExam = (TextView) findViewById(R.id.student_detail_for_exam);
        testDetailForExam = (TextView) findViewById(R.id.test_detail_for_exam_id);

        startFromExamQuizButton = (Button) findViewById(R.id.start_from_exam_quiz_button);
    }

    private void addListener() {
        final DBHelperSpecific dbHelperSpecific = new DBHelperSpecific(AddExamActivity.this);
        final ArrayList<String> classesList = dbHelperSpecific.getDistinctStudentClasses();

        ArrayAdapter<String> classesDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classesList);
        classesDataAdapter.setDropDownViewResource(android.R.layout.select_dialog_multichoice);

        classExam.setAdapter(classesDataAdapter);

        classExam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int tempClass = Integer.parseInt(classExam.getSelectedItem().toString());

                // Selection of roll No
                ArrayList<String> studentsRollNoList = dbHelperSpecific.getDistinctStudentRollNoInAClass(tempClass);
                ArrayAdapter<String> studentRollNoArrayAdapter = new ArrayAdapter<String>(AddExamActivity.this, android.R.layout.simple_spinner_item, studentsRollNoList);
                studentRollNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                studentExam.setAdapter(studentRollNoArrayAdapter);

                // Selection of subjects
                ArrayList<String> subjectInAClassList = dbHelperSpecific.getDistinctTestSubjects(tempClass);
                ArrayAdapter<String> subjectInAClassArrayAdapter = new ArrayAdapter<String>(AddExamActivity.this, android.R.layout.simple_spinner_item, subjectInAClassList);
                subjectInAClassArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                subjectExam.setAdapter(subjectInAClassArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        studentExam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int tempStdClass = Integer.parseInt(classExam.getSelectedItem().toString());
                int tempStdRollNo = Integer.parseInt(studentExam.getSelectedItem().toString());
                ArrayList<String> studentIdAndName = dbHelperSpecific.getDistinctStudentDetailFromRollNoInAClass(tempStdClass, tempStdRollNo);
                if (studentIdAndName.size() > 1) {
                    studentIdForQuizStartPass = Integer.parseInt(studentIdAndName.get(1));
                    studentNamePass = studentIdAndName.get(2);
                    studentDetailForExam.setText(studentNamePass + " ( Class:" + tempStdClass + ", Roll No:" + tempStdRollNo + " )");
                } else {
                    studentDetailForExam.setText("Not yet selected.");
                    studentIdForQuizStartPass = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subjectExam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tempClassForGettingChapter;
                tempClassForGettingChapter = classExam.getSelectedItem().toString();
                String tempSubjectForGettingChapter;
                tempSubjectForGettingChapter = adapterView.getSelectedItem().toString();

                ArrayList<String> chaptersForStartQuiz = dbHelperSpecific.getDistinctTestChapters(tempClassForGettingChapter, tempSubjectForGettingChapter);
                ArrayAdapter<String> chaptersArrayAdaptor = new ArrayAdapter<String>(AddExamActivity.this, android.R.layout.simple_spinner_item, chaptersForStartQuiz);
                chaptersArrayAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                chapterExam.setAdapter(chaptersArrayAdaptor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        chapterExam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String classForSectionsSelection;
                classForSectionsSelection = classExam.getSelectedItem().toString();
                String subjectForSectionsSelection;
                subjectForSectionsSelection = subjectExam.getSelectedItem().toString();
                String chapterForSectionsSelection;
                chapterForSectionsSelection = adapterView.getSelectedItem().toString();

                ArrayList<String> sectionsForQuizStart = dbHelperSpecific.getDistinctTestSections(classForSectionsSelection, subjectForSectionsSelection, chapterForSectionsSelection);

                ArrayAdapter<String> sectionsForQuizStartArrayAdapter = new ArrayAdapter<String>(AddExamActivity.this, android.R.layout.simple_spinner_item, sectionsForQuizStart);
                sectionsForQuizStartArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                sectionsExam.setAdapter(sectionsForQuizStartArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sectionsExam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String classForTestIdSelection;
                classForTestIdSelection = classExam.getSelectedItem().toString();
                String subjectForTestIdSelection;
                subjectForTestIdSelection = subjectExam.getSelectedItem().toString();
                String chapterForTestIdSelection;
                chapterForTestIdSelection = chapterExam.getSelectedItem().toString();
                String sectionsForTestIdSelection;
                sectionsForTestIdSelection = adapterView.getSelectedItem().toString();

                ArrayList<String> testIdForQuizStart = dbHelperSpecific.getDistinctTestIdFromClassSubjectChapterSections(
                        classForTestIdSelection, subjectForTestIdSelection,
                        chapterForTestIdSelection, sectionsForTestIdSelection);

                if (testIdForQuizStart.size() > 1) {

                    String tempTestId;
                    tempTestId = testIdForQuizStart.get(1).toString();
                    testIdForQuizStartPass = Integer.parseInt(tempTestId);
                    testDetailForExam.setText("Test ID: " + tempTestId + "( " +
                            "Class:" + classForTestIdSelection +
                            " ,Subject:" + subjectForTestIdSelection +
                            " ,Chapter:" + chapterForTestIdSelection +
                            " ,Sections:" + sectionsForTestIdSelection +
                            " )");
                } else {
                    testDetailForExam.setText(" Yet Test not selected.");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        startFromExamQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputForTakeQuiz()) {
                    long tempExamId = dataInsertedIntoExamTable();
                    if (tempExamId != -1) {
                        AlertMessage.ShowAlertMessage(AddExamActivity.this, "Start Quiz");
                        Intent intent = new Intent(AddExamActivity.this, QuizActivity.class);

                        intent.putExtra("student_id", studentIdForQuizStartPass);
                        intent.putExtra("test_id", testIdForQuizStartPass);
                        intent.putExtra("exam_id", tempExamId);
                        intent.putExtra("studentName", studentNamePass);
                        intent.putExtra("studentRollNo", studentExam.getSelectedItem().toString());
                        intent.putExtra("studentClass", classExam.getSelectedItem().toString());
                        intent.putExtra("testSubject", subjectExam.getSelectedItem().toString());
                        intent.putExtra("testChapter", chapterExam.getSelectedItem().toString());
                        intent.putExtra("testSections", sectionsExam.getSelectedItem().toString());

                        startActivity(intent);
                    }

                } else {
                    AlertMessage.ShowAlertMessage(AddExamActivity.this, "Select All feilds to start quiz.");
                }

            }

            private long dataInsertedIntoExamTable() {
                DBHelper dbHelper = new DBHelper(AddExamActivity.this);
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                long statusForInsertion = dbHelper.insertExam(date, studentIdForQuizStartPass, testIdForQuizStartPass);
                // Temp code for checking start
                if (statusForInsertion != -1) {
                    AlertMessage.ShowAlertMessage(AddExamActivity.this, date + "\nExam ID: " + statusForInsertion + "\nStudent ID: " + studentIdForQuizStartPass + " \nTest ID: " + testIdForQuizStartPass);
                } else {
                    AlertMessage.ShowAlertMessage(AddExamActivity.this, "No inserted.");
                }
                // Temp code for checking end

                return statusForInsertion;
            }

            private boolean validateInputForTakeQuiz() {
                if (
                        classExam.getSelectedItem().toString() == "-1" ||
                                studentExam.getSelectedItem().toString() == "-1" ||
                                subjectExam.getSelectedItem().toString() == "-1" ||
                                chapterExam.getSelectedItem().toString() == "-1" ||
                                sectionsExam.getSelectedItem().toString() == "-1"
                        ) {
                    return false;
                }
                return true;
            }
        });
    }
}