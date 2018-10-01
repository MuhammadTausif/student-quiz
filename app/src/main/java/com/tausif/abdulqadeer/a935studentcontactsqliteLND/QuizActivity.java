package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    TextView studentDetailForQuizTextDisplay, testDetailForQuizTextDisplay;

    TextView questionCurrentTextView;
    ArrayList<Question> questionsListForQuiz;

    Button optionAButton, optionBButton, optionCButton, optionDButton;

    int examIdForQuiz;
    int testIdForQuiz;
    int questionIdForCurrentQuestion;
    String[] tempOptionsForQuestion = {null, null, null, null};
    String[] resultOfQuiz = {"-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"};
    int globalResultOfQuizStringIncreament=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        inflateFields();
        getIntentExtras();
        getQuestionList();
        produceRandomQuestions();
        addActionListener();
    }

    private void checkCorrectOption(View view){
        TextView tempOptionView = (TextView) view;
        String tempOptionString = tempOptionView.getText().toString();
        if (tempOptionString == tempOptionsForQuestion[0]) {
            AlertMessage.ShowAlertMessage(QuizActivity.this, "Correct A");
            resultOfQuiz[globalResultOfQuizStringIncreament]=questionIdForCurrentQuestion+"A";
            globalResultOfQuizStringIncreament++;
        } else if (tempOptionString == tempOptionsForQuestion[1]) {
            AlertMessage.ShowAlertMessage(QuizActivity.this, "Wrong B");
            resultOfQuiz[globalResultOfQuizStringIncreament]=questionIdForCurrentQuestion+"B";
            globalResultOfQuizStringIncreament++;

        } else if (tempOptionString == tempOptionsForQuestion[2]) {
            AlertMessage.ShowAlertMessage(QuizActivity.this, "Wrong C");

            resultOfQuiz[globalResultOfQuizStringIncreament]=questionIdForCurrentQuestion+"C";
            globalResultOfQuizStringIncreament++;
        } else if (tempOptionString == tempOptionsForQuestion[3]) {
            AlertMessage.ShowAlertMessage(QuizActivity.this, "Wrong D");

            resultOfQuiz[globalResultOfQuizStringIncreament]=questionIdForCurrentQuestion+"D";
            globalResultOfQuizStringIncreament++;
        }

    }

    private void addActionListener() {
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
            StringBuilder sb=new StringBuilder();
            for (String s :
                    resultOfQuiz) {
                sb.append(s).append(",");
            }
            AlertMessage.ShowAlertMessage(QuizActivity.this, sb.toString());
            DBHelper dbHelper=new DBHelper(QuizActivity.this);
            dbHelper.insertResult(examIdForQuiz,
                    resultOfQuiz[0],
                    resultOfQuiz[1],
                    resultOfQuiz[2],
                    resultOfQuiz[3],
                    resultOfQuiz[4],
                    resultOfQuiz[5],
                    resultOfQuiz[6],
                    resultOfQuiz[7],
                    resultOfQuiz[8],
                    resultOfQuiz[9]
            );
        }
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
        if (testIdForQuiz != -1) {
            DBHelperSpecific dbHelperSpecific = new DBHelperSpecific(QuizActivity.this);

            questionsListForQuiz = dbHelperSpecific.getAllQuestionFromTestId(testIdForQuiz);
        }
    }

    private void inflateFields() {
        studentDetailForQuizTextDisplay = (TextView) findViewById(R.id.student_detail_for_quiz_text_view);
        testDetailForQuizTextDisplay = (TextView) findViewById(R.id.test_detail_for_quiz_text_view);

        questionCurrentTextView = (TextView) findViewById(R.id.question_current_textView);

        optionAButton = (Button) findViewById(R.id.optionA_button);
        optionBButton = (Button) findViewById(R.id.optionB_button);
        optionCButton = (Button) findViewById(R.id.optionC_button);
        optionDButton = (Button) findViewById(R.id.optionD_button);

        questionsListForQuiz = new ArrayList<Question>();
        testIdForQuiz = -1;
        questionIdForCurrentQuestion = -1;
        examIdForQuiz=-1;
    }

    private void getIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String tempStudentName = extras.get("studentName").toString();
            String tempStudentClass = extras.get("studentClass").toString();
            String tempStudentRollNo = extras.get("studentRollNo").toString();
            testIdForQuiz = Integer.parseInt(extras.get("test_id").toString());
            examIdForQuiz=Integer.parseInt(extras.get("exam_id").toString());

            studentDetailForQuizTextDisplay.setText(tempStudentName +
                    " ( " +
                    "Class: " + tempStudentClass +
                    ", Roll No: " + tempStudentRollNo +
                    " )");

            String tempTestSubject = extras.get("testSubject").toString();
            String tempTestChapter = extras.get("testChapter").toString();
            String tempTestSections = extras.get("testSections").toString();

            testDetailForQuizTextDisplay.setText(
                    tempTestSubject +
                            " ( " +
                            "Chapter: " + tempTestChapter +
                            ", Sections: " + tempTestSections +
                            " )");
        }
    }
}