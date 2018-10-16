package com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.StudentClasses;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddExamActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddQuestionActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddStudentActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddTestActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AlertMessage;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelperSpecific;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.MainActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.QuizActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.R;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.StudentClass;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.ToastMessage;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.ViewClassesActivity;

public class NewStudentClassActivity extends AppCompatActivity {

    // region Fields and Instances
    StudentClass studentClass;
    EditText serialNoEditText, studentClassNameEditText, activeTestIDEditText;
    Button newStudentClassButton, backButton;
    DBHelperSpecific dbHelperSpecific;
    Intent intent;
    Context context;

    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student_class);

        inflateFeildsAndInitialize();
        addActionListener();
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

    void inflateFeildsAndInitialize() {

        context = getApplicationContext();
        dbHelperSpecific = new DBHelperSpecific(getApplicationContext());

        serialNoEditText = (EditText) findViewById(R.id.serialNo_editText_newStudentClass);
        studentClassNameEditText = (EditText) findViewById(R.id.studentClassName_editText_newStudentClass);
        activeTestIDEditText = (EditText) findViewById(R.id.activeTestID_editText_newStudentClass);
        newStudentClassButton = (Button) findViewById(R.id.newStudentClass_button_newStudentClass);
        backButton = (Button) findViewById(R.id.back_button_newStudentClass);
    }

    void addActionListener() {
        newStudentClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serialNoEditText.getText().toString().length() > 0 && studentClassNameEditText.getText().toString() != null) {
                    int tempSerialNo = Integer.parseInt(serialNoEditText.getText().toString());
                    String tempStudentClassName = studentClassNameEditText.getText().toString();
                    int tempActiveTestID = -1;
                    if(activeTestIDEditText.getText().length()>0 ){
                        tempActiveTestID = Integer.parseInt(activeTestIDEditText.getText().toString());
                    }
                    studentClass = new StudentClass(tempSerialNo, tempStudentClassName, tempActiveTestID);
                    dbHelperSpecific.insertStudentClass(studentClass);
                    intent = new Intent(context, ViewStudentClassesListActivity.class);
                    startActivity(intent);
                } else {
                    ToastMessage.ShowToastMessage(context, "Please enter the required feilds.");
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, ViewStudentClassesListActivity.class);
                startActivity(intent);
            }
        });
    }

}
