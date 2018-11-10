package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateDeleteStudentActivity extends AppCompatActivity {

    EditText name, fatherName, address, phone, studentClass, rollNo;
    Button updateBtn, deleteBtn;
    DBHelperSpecific dbHelperSpecific;
    int studentIdForOperation;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_student);

        studentIdForOperation = -1;

        dbHelperSpecific = new DBHelperSpecific(UpdateDeleteStudentActivity.this);

        inflateFeilds();
        addActionListener();
        Intent intent = getIntent();
        student = (Student) intent.getSerializableExtra("studentObject");

        if (student != null) {
            studentIdForOperation = student.get_id();
            name.setText(student.getName());
            fatherName.setText(student.getFatherName());
            address.setText(student.getAddress());
            phone.setText(student.getPhone());
            studentClass.setText(String.valueOf(student.getClassStd()));
            rollNo.setText(String.valueOf(student.getRollNo()));
        }
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

    private void inflateFeilds() {
        // Text feild inflation.
        name = (EditText) findViewById(R.id.editTextNameForUpdateDelete);
        fatherName = (EditText) findViewById(R.id.editTextFatherNameForUpdateDelete);
        address = (EditText) findViewById(R.id.editTextAddressForUpdateDelete);
        phone = (EditText) findViewById(R.id.editTextPhoneForUpdateDelete);
        studentClass = (EditText) findViewById(R.id.editTextClassForUpdateDelete);
        rollNo = (EditText) findViewById(R.id.editTextRollNoForUpdateDelete);

        updateBtn = (Button) findViewById(R.id.update_btn_for_update_delete_student);
        deleteBtn = (Button) findViewById(R.id.delete_btn_for_update_delete_student);
    }

    private void addActionListener() {
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentIdForOperation != -1) {
                    updateStudentObject();
                    if (dbHelperSpecific.updateStudent(student)) {
                        AlertMessage.ShowAlertMessage(UpdateDeleteStudentActivity.this, student.getName() + " updated");
//                        Intent intent = new Intent(UpdateDeleteStudentActivity.this, ViewStudentActivity.class);
                        Intent intent = new Intent(UpdateDeleteStudentActivity.this, ViewClassStudentsActivity.class);
                        intent.putExtra("CLASS_ID_FOR_STUDENTS", String.valueOf(student.getClassStd()));
                        startActivity(intent);
                    } else {
                        AlertMessage.ShowAlertMessage(UpdateDeleteStudentActivity.this, student.getName() + "not updated");
                    }
                } else {
                    AlertMessage.ShowAlertMessage(getApplicationContext(), "No key to updateButton");
                }

            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentIdForOperation != -1) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDeleteStudentActivity.this);
                    builder.setMessage("Are you sure to deleteButton \n Student: " + student.getName());
                    builder.setTitle("Delete Student...");
                    builder.setPositiveButton("Yes, Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Delete the student
                            if (dbHelperSpecific.deleteStudent(student)) {
                                dialog.cancel();
//                                Intent intent = new Intent(UpdateDeleteStudentActivity.this, ViewStudentActivity.class);
                                Intent intent = new Intent(UpdateDeleteStudentActivity.this, ViewClassStudentsActivity.class);
                                intent.putExtra("CLASS_ID_FOR_STUDENTS", String.valueOf(student.getClassStd()));
                                startActivity(intent);
                            }
                            else {
                                AlertMessage.ShowAlertMessage(UpdateDeleteStudentActivity.this, "Can not Delete the Student");
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
//                            Intent intent = new Intent(UpdateDeleteStudentActivity.this, ViewStudentActivity.class);
                            Intent intent = new Intent(UpdateDeleteStudentActivity.this, ViewClassStudentsActivity.class);
                            intent.putExtra("CLASS_ID_FOR_STUDENTS", String.valueOf(student.getClassStd()));
                            startActivity(intent);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    AlertMessage.ShowAlertMessage(getApplicationContext(), "No key to updateButton");
                }

            }
        });
    }

    public void updateStudentObject() {
        student.setName(name.getText().toString());
        student.setFatherName(fatherName.getText().toString());
        student.setAddress(address.getText().toString());
        student.setPhone(phone.getText().toString());
        student.setClassStd(Integer.parseInt(studentClass.getText().toString()));
        student.setRollNo(Integer.parseInt(rollNo.getText().toString()));
    }
}
