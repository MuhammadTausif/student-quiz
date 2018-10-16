package com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.StudentClasses;

import android.content.Context;
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
import android.widget.Toast;

import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddExamActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddQuestionActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddStudentActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.AddTestActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelperSpecific;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.MainActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.QuizActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.R;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.StudentClass;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.UpdateDeleteTestActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.ViewClassTestsActivity;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.ViewClassesActivity;

public class UpdateDeleteStudentClassActivity extends AppCompatActivity {

    // region Fields and Instances
    Context context;
    StudentClass studentClass;
    DBHelperSpecific dbHelperSpecific;
    Bundle extras;
    Intent intent;

    int studentClassID;
    EditText serialNoEditText, studentClassNameEditText, activeTestIDEditText;
    Button updateButton, deleteButton, backButton;

    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_student_class);

        getExtras();
        inflateFields();
        setActionListener();
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

    void getExtras() {
        extras = getIntent().getExtras();
        if (extras != null) {
            studentClassID = extras.getInt("STUDENT_CLASS_ID_FOR_UPDATE");
        }
    }

    void inflateFields() {
        context = getApplicationContext();
        dbHelperSpecific = new DBHelperSpecific(context);
        studentClass = dbHelperSpecific.getStudentClass(studentClassID);

        serialNoEditText = (EditText) findViewById(R.id.serialNo_editText_updateDeleteStudentClass);
        serialNoEditText.setText(Integer.toString(studentClass.getSrialNo()));
        studentClassNameEditText = (EditText) findViewById(R.id.studentClassName_editText_updateDeleteStudentClass);
        studentClassNameEditText.setText(studentClass.getName());
        activeTestIDEditText = (EditText) findViewById(R.id.activeTestID_editText_updateDeleteStudentClass);
        activeTestIDEditText.setText(Integer.toString(studentClass.getActiveTestID()));

        updateButton = (Button) findViewById(R.id.update_button_updateDeleteStudentClass);
        deleteButton = (Button) findViewById(R.id.delete_button_updateDeleteStudentClass);
        backButton = (Button) findViewById(R.id.back_button_updateDeleteStudentClass);
    }

    void setActionListener() {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentClass.setSrialNo(Integer.parseInt( serialNoEditText.getText().toString()));
                studentClass.setName(studentClassNameEditText.getText().toString());
                studentClass.setActiveTestID(Integer.parseInt(activeTestIDEditText.getText().toString()));
                dbHelperSpecific.updateStudentClass(studentClass);
                goBack();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // Creating Alert dialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                // Setting the title, message, and action to delete or go back.
//                builder.setTitle("Delete Test")
//                        .setMessage("Are you sure to delete the Test?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (dbHelperSpecific.deleteStudentClass(studentClassID)) {
//                                    Toast.makeText(getApplicationContext(), "Class is Deleted.", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "Sorry, class can't be deleted.", Toast.LENGTH_SHORT).show();
//                                }
//                                goBack();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                goBack();
//                            }
//                        });
//
//                // Create Alert dialog
//                AlertDialog alertDialog = builder.create();
//
//                // Show the dialog
//                alertDialog.show();

                // This two lines of code would be replaced by above commented for confirmation mess
                dbHelperSpecific.deleteStudentClass(studentClassID);
                goBack();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    void goBack() {
        intent = new Intent(context, ViewStudentClassesListActivity.class);
        startActivity(intent);
    }
}
