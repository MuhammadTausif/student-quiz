package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.StudentClasses.ViewStudentClassesListActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    // region Fields and Instances
    Context context;
    DBHelperSpecific dbHelperSpecific;
    Bundle extras;
    Intent intent;

    // Layout instances
    TextView studentDetailForQuizTextDisplay, testDetailForQuizTextDisplay;
    TextView questionCurrentTextView, questionNumberTextView;
    Button optionAButton, optionBButton, optionCButton, optionDButton;

    int examIdForQuiz;
    int testIdForQuiz;
    int questionIdForCurrentQuestion;
    String[] tempOptionsForQuestion = {null, null, null, null};
//    String[] resultOfQuiz = {"-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"};
    String[] resultOfQuiz;
    int globalResultOfQuizStringIncreament = 0;

    // Reference IDs and instances
    int studentID, studentClassIndex, activeTestID, totalQuestions, currentQustionIndex, score = 0;
    String percentage;
    long examID;
    long resultID;
    ArrayList<Question> questionsListForQuiz;
    StudentClass studentClass;
    Student student;
    Test test;

    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        inflateFields();
        getIntentExtrasAndIntializeData();
        produceRandomQuestions();
        addActionListener();
    }

    private void checkCorrectOption(View view) {
        // Checking the correct Option
        TextView tempOptionView = (TextView) view;
        String tempOptionString = tempOptionView.getText().toString();
        if (tempOptionString == tempOptionsForQuestion[0]) {
//            AlertMessage.ShowAlertMessage(QuizActivity.this, "Correct A");
            score++;
            ToastMessage.ShowToastMessage(context, "Correct");
            resultOfQuiz[globalResultOfQuizStringIncreament] = questionIdForCurrentQuestion + ",A";
            globalResultOfQuizStringIncreament++;
        } else if (tempOptionString == tempOptionsForQuestion[1]) {
//            AlertMessage.ShowAlertMessage(QuizActivity.this, "Wrong B");
            ToastMessage.ShowToastMessage(context, "Wrong");
            resultOfQuiz[globalResultOfQuizStringIncreament] = questionIdForCurrentQuestion + ",B";
            globalResultOfQuizStringIncreament++;

        } else if (tempOptionString == tempOptionsForQuestion[2]) {
//            AlertMessage.ShowAlertMessage(QuizActivity.this, "Wrong C");
            ToastMessage.ShowToastMessage(context, "Wrong");

            resultOfQuiz[globalResultOfQuizStringIncreament] = questionIdForCurrentQuestion + ",C";
            globalResultOfQuizStringIncreament++;
        } else if (tempOptionString == tempOptionsForQuestion[3]) {
//            AlertMessage.ShowAlertMessage(QuizActivity.this, "Wrong D");
            ToastMessage.ShowToastMessage(context, "Wrong");
            resultOfQuiz[globalResultOfQuizStringIncreament] = questionIdForCurrentQuestion + ",D";
            globalResultOfQuizStringIncreament++;
        }
    }

    private void produceRandomQuestions() {
        if (questionsListForQuiz.size() > 0) {

            Random randomQuestion = new Random();
            Question questionCurrent = questionsListForQuiz.get(randomQuestion.nextInt(questionsListForQuiz.size()));
            questionCurrentTextView.setText(questionCurrent.getQuestion());
            questionIdForCurrentQuestion = questionCurrent.getQuestionId();

            tempOptionsForQuestion[0] = questionCurrent.getOptionA();
            tempOptionsForQuestion[1] = questionCurrent.getOptionB();
            tempOptionsForQuestion[2] = questionCurrent.getOptionC();
            tempOptionsForQuestion[3] = questionCurrent.getOptionD();

            ArrayList<String> tempOptionsStringArrayList = new ArrayList<String>();

            tempOptionsStringArrayList.add(questionCurrent.getOptionA());
            tempOptionsStringArrayList.add(questionCurrent.getOptionB());
            tempOptionsStringArrayList.add(questionCurrent.getOptionC());
            tempOptionsStringArrayList.add(questionCurrent.getOptionD());

            Random randomOptions = new Random();

            String tempOptionToBeAssigned = tempOptionsStringArrayList.get(randomOptions.nextInt(tempOptionsStringArrayList.size()));
            optionAButton.setText(tempOptionToBeAssigned);
            tempOptionsStringArrayList.remove(tempOptionToBeAssigned);

            tempOptionToBeAssigned = tempOptionsStringArrayList.get(randomOptions.nextInt(tempOptionsStringArrayList.size()));
            optionBButton.setText(tempOptionToBeAssigned);
            tempOptionsStringArrayList.remove(tempOptionToBeAssigned);

            tempOptionToBeAssigned = tempOptionsStringArrayList.get(randomOptions.nextInt(tempOptionsStringArrayList.size()));
            optionCButton.setText(tempOptionToBeAssigned);
            tempOptionsStringArrayList.remove(tempOptionToBeAssigned);

            tempOptionToBeAssigned = tempOptionsStringArrayList.get(randomOptions.nextInt(tempOptionsStringArrayList.size()));
            optionDButton.setText(tempOptionToBeAssigned);
            tempOptionsStringArrayList.remove(tempOptionToBeAssigned);

            questionsListForQuiz.remove(questionCurrent);
        } else {
            AlertMessage.ShowAlertMessage(QuizActivity.this, "No Question.");
            StringBuilder sb = new StringBuilder();
            for (String s :
                    resultOfQuiz) {
                sb.append(s).append(",");
            }
            AlertMessage.ShowAlertMessage(QuizActivity.this, sb.toString());
            DBHelper dbHelper = new DBHelper(QuizActivity.this);
            String examDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            float tempPercentage = 100 * ((float)score / (float)totalQuestions);
            percentage = Float.toString(tempPercentage);
            resultID = dbHelper.insertResult(activeTestID,
                    studentID,
                    examDate,
                    resultOfQuiz[0],
                    resultOfQuiz[1],
                    resultOfQuiz[2],
                    resultOfQuiz[3],
                    resultOfQuiz[4],
                    resultOfQuiz[5],
                    resultOfQuiz[6],
                    resultOfQuiz[7],
                    resultOfQuiz[8],
                    resultOfQuiz[9],
                    percentage
            );
            if (resultID != -1) {
                ToastMessage.ShowToastMessage(context, "Data Inserted");
            }
        }
    }

    private void inflateFields() {
        studentDetailForQuizTextDisplay = (TextView) findViewById(R.id.student_detail_for_quiz_text_view);
        testDetailForQuizTextDisplay = (TextView) findViewById(R.id.test_detail_for_quiz_text_view);
        questionNumberTextView = (TextView) findViewById(R.id.question_number_textView);

        // Current Question Text
        questionCurrentTextView = (TextView) findViewById(R.id.question_current_textView);

        // Current Question Options
        optionAButton = (Button) findViewById(R.id.optionA_button);
        optionBButton = (Button) findViewById(R.id.optionB_button);
        optionCButton = (Button) findViewById(R.id.optionC_button);
        optionDButton = (Button) findViewById(R.id.optionD_button);

        // Array of Questions
        questionsListForQuiz = new ArrayList<Question>();

        // Reference variables
        testIdForQuiz = -1;
        questionIdForCurrentQuestion = -1;
        examIdForQuiz = -1;
    }

    private void getIntentExtrasAndIntializeData() {
        extras = getIntent().getExtras();
        if (extras != null) {
            // Getting extras from Student calling class
            studentClassIndex = extras.getInt("STUDENT_CLASS_ID_FOR_QUIZ");
            studentID = extras.getInt("STUDENT_ID_FOR_QUIZ");

            intializeData();
        }
    }

    private void intializeData() {

        context = getApplicationContext();
        dbHelperSpecific = new DBHelperSpecific(context);
        studentClass = dbHelperSpecific.getStudentClassFromClassIndex(studentClassIndex);
        activeTestID = studentClass.getActiveTestID();
        student = dbHelperSpecific.getStudentFromStudentID(studentID);
        test = dbHelperSpecific.getTestFromID(activeTestID);
        questionsListForQuiz = dbHelperSpecific.getAllQuestionFromTestId(activeTestID);

        // Intializing the number of questions
        currentQustionIndex = 1;
        totalQuestions = questionsListForQuiz.size();
        resultOfQuiz = new String[totalQuestions];

        questionNumberTextView.setText("Question " + currentQustionIndex + "/" + totalQuestions);
        studentDetailForQuizTextDisplay.setText(student.getRollNo() + ": " + student.getName() + " (" + student.getClassStd() + ")");
        testDetailForQuizTextDisplay.setText(test.getSubject() + ", Ch# " + test.getChapter() + ", Sec# " + test.getSections());

        // Inserting Quiz Exam
        examID = dataInsertedIntoExamTable();

        // old extras
//        String tempStudentName = extras.get("studentName").toString();
//        String tempStudentClass = extras.get("studentClass").toString();
//        String tempStudentRollNo = extras.get("studentRollNo").toString();
//        testIdForQuiz = Integer.parseInt(extras.get("test_id").toString());
//        examIdForQuiz=Integer.parseInt(extras.get("exam_id").toString());
//
//        studentDetailForQuizTextDisplay.setText(tempStudentName +
//                " ( " +
//                "Class: " + tempStudentClass +
//                ", Roll No: " + tempStudentRollNo +
//                " )");
//
//        String tempTestSubject = extras.get("testSubject").toString();
//        String tempTestChapter = extras.get("testChapter").toString();
//        String tempTestSections = extras.get("testSections").toString();
//
//        testDetailForQuizTextDisplay.setText(
//                tempTestSubject +
//                        " ( " +
//                        "Chapter: " + tempTestChapter +
//                        ", Sections: " + tempTestSections +
//                        " )");

    }

    private long dataInsertedIntoExamTable() {
        DBHelper dbHelper = new DBHelper(context);
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        long statusForInsertion = dbHelper.insertExam(date, studentID, activeTestID);
        examIdForQuiz = (int) statusForInsertion;
        // Temp code for checking start
        if (statusForInsertion != -1) {
//            AlertMessage.ShowAlertMessage(context, date + "\nExam ID: " + statusForInsertion + "\nStudent ID: " + studentID + " \nTest ID: " + activeTestID);
            ToastMessage.ShowToastMessage(context, "Starting Exam");
        } else {
//            AlertMessage.ShowAlertMessage(context, "No inserted.");
            ToastMessage.ShowToastMessage(context, "Could not start Exam");
        }
        // Temp code for checking end

        return statusForInsertion;
    }

    // Action Listeners single method
    private void addActionListener() {
        optionAButton.setOnClickListener(this);
        optionBButton.setOnClickListener(this);
        optionCButton.setOnClickListener(this);
        optionDButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        checkCorrectOption(v);
        produceRandomQuestions();

        if (currentQustionIndex == totalQuestions) {

            intent = new Intent(context, ResultActivity.class);
            int tempResultID = (int) resultID;
            intent.putExtra("RESULT_ID", tempResultID);
            startActivity(intent);
        }
        // Incrementing the Current Question Number
        currentQustionIndex++;
        questionNumberTextView.setText("Question " + currentQustionIndex + "/" + totalQuestions);

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

    // region Unused

    // This is old action Listener for each method
    private void addActionListenerOld() {
        optionAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TextView tempOptionAView = (TextView) v;
//                String tempOptionAString = tempOptionAView.getText().toString();
//                if (tempOptionAString == tempOptionsForQuestion[0]) {
//                    AlertMessage.ShowAlertMessage(QuizActivity.this, "Correct A");
//                } else if (tempOptionAString == tempOptionsForQuestion[1]) {
//                    AlertMessage.ShowAlertMessage(QuizActivity.this, "Wrong B");
//                } else if (tempOptionAString == tempOptionsForQuestion[2]) {
//                    AlertMessage.ShowAlertMessage(QuizActivity.this, "Wrong C");
//                } else if (tempOptionAString == tempOptionsForQuestion[3]) {
//                    AlertMessage.ShowAlertMessage(QuizActivity.this, "Wrong D");
//                }
                checkCorrectOption(v);
                produceRandomQuestions();
            }
        });
        optionBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCorrectOption(v);
                produceRandomQuestions();
            }
        });
        optionCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCorrectOption(v);
                produceRandomQuestions();
            }
        });
        optionDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCorrectOption(v);
                produceRandomQuestions();
            }
        });
    }

    private void produceRandomQuestionsForTest() {
        if (questionsListForQuiz.size() > 0) {
            while (questionsListForQuiz.size() > 0) {
                Random random = new Random();
                Question questionCurrent = questionsListForQuiz.get(random.nextInt(questionsListForQuiz.size()));
                AlertMessage.ShowAlertMessage(QuizActivity.this, questionCurrent.toString());
                questionsListForQuiz.remove(questionCurrent);
            }
        }
    }

    private void getQuestionList() {
        questionsListForQuiz = dbHelperSpecific.getAllQuestionFromTestId(activeTestID);
    }

    // endregion
}