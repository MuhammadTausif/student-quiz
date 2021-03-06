
package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.StudentClasses.ViewStudentClassesListActivity;

import java.util.ArrayList;

public class AddStudentActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener {

    EditText name, fatherName, address, phone, studentClass;
    Spinner rollNo;
    Button saveBtn;
    int tempClassIDFromInt=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        // Spinner inflation.
        rollNo = (Spinner) findViewById(R.id.editTextRollNo);
        ArrayList<String> universalList = new ArrayList<String>();
        for (int i = 1; i < 35; i++) {
            universalList.add(String.valueOf(i));
        }

        // Text feild inflation.
        name = (EditText) findViewById(R.id.editTextName);
        fatherName = (EditText) findViewById(R.id.editTextFatherName);
        address = (EditText) findViewById(R.id.editTextAddress);
        phone = (EditText) findViewById(R.id.editTextPhone);
        studentClass = (EditText) findViewById(R.id.editTextClass);

        Bundle extras = new Bundle();
        extras = getIntent().getExtras();
        if (extras != null) {
            // The next line was for ViewStudentActivity Call
//            tempClassIDFromInt = extras.getInt("CLASS_ID_FR0M_VIEW_STUDENTS");
            tempClassIDFromInt = extras.getInt("CLASS_ID_FOR_ADD_STUDENT");
            String tempClassIDFromViewStudent = String.valueOf(tempClassIDFromInt);

            studentClass.setText(tempClassIDFromViewStudent);
            studentClass.setEnabled(false);
            DBHelperSpecific dbHelperSpecific=new DBHelperSpecific(AddStudentActivity.this);

            ArrayList<String> universalList1 =dbHelperSpecific.getAllRollNoOfSpecificClass(tempClassIDFromInt);
            universalList.removeAll(universalList1);
        }

        ArrayAdapter<String> universalListArrayAdapter = new ArrayAdapter<String>(AddStudentActivity.this, android.R.layout.simple_spinner_item, universalList);
        universalListArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rollNo.setAdapter(universalListArrayAdapter);

        name.addTextChangedListener(this);
        name.setOnFocusChangeListener(this);

        saveBtn = (Button) findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validated()) {
                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    dbHelper.insertStudent(
                            name.getText().toString(),
                            fatherName.getText().toString(),
                            address.getText().toString(),
                            phone.getText().toString(),
                            Integer.parseInt(studentClass.getText().toString()),
                            Integer.parseInt(rollNo.getSelectedItem().toString())
                    );
                    Intent intent = new Intent(AddStudentActivity.this, ViewClassStudentsActivity.class);
                    intent.putExtra("CLASS_ID_FOR_STUDENTS", studentClass.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(AddStudentActivity.this, "Please enter as required.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    private boolean validated() {
        if (name.getText().length() < 1 ||
                //rollNo.getText().length() < 1 ||
                fatherName.getText().length() < 1 || address.getText().length() < 1 ||
                studentClass.getText().length() < 1 || phone.getText().length() < 1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    public void onFocusChange(View view, boolean b) {
    }
}




