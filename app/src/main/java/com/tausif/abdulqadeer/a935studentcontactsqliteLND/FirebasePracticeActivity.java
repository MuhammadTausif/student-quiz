package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.Activities.StudentClasses.ViewStudentClassesListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebasePracticeActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;
    // ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_practice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // region firebase

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final TextView userNameTextView = findViewById(R.id.userName_editText_firebasePractice);
        final TextView emailTextView = findViewById(R.id.email_editText_firebasePractice);
        Button addUserButton = findViewById(R.id.addUser_button_firebasePractice);
        Button syncButton = findViewById(R.id.sync_button_firebasePractice);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewUser("1", userNameTextView.getText().toString(), emailTextView.getText().toString());
            }
        });

        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastMessage.ShowToastMessage(getApplicationContext(), "Sync Started");
                uploadStudent();
                uploadStudentClasses();
                uploadTests();
                // endregion
            }
        });

    }

    public void uploadStudent() {
        ArrayList<Student> students = new ArrayList<Student>();
        DBHelperSpecific dbHelperSpecific = new DBHelperSpecific(getApplicationContext());
        students = dbHelperSpecific.getAllStudents();
        Map<String, Object> studentMap = new HashMap<String, Object>();
        for (Student student : students) {
            studentMap.put("_id", student.get_id());
            studentMap.put("address", student.getAddress());
            studentMap.put("classStd", student.getClassStd());
            studentMap.put("fatherName", student.getFatherName());
            studentMap.put("name", student.getName());
            studentMap.put("phone", student.getPhone());
            studentMap.put("rollNo", student.getRollNo());
            mDatabase
                    .child("37230015")
                    .child("studentClasses")
                    .child(Integer.toString(student.getClassStd()))
                    .child("students")
                    .child(Integer.toString(student.getRollNo()))
                    .updateChildren(studentMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Write was successful!
                    // ...
                    Log.d("LND", "Data insertion Completed");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Write failed
                    // ...
                    Log.e("LND", "Data insertion Not completed");
                }
            });
        }

//                Map<String, Object> resultMap = new HashMap<String, Object>();
//                resultMap.put("uid", "123");
//                resultMap.put("author", "Aslam");
//
//                mDatabase.child("37230015").updateChildren(resultMap);
    }

    public void uploadStudentClasses() {
        ArrayList<StudentClass> studentsClasses = new ArrayList<StudentClass>();
        DBHelperSpecific dbHelperSpecific = new DBHelperSpecific(getApplicationContext());
        studentsClasses = dbHelperSpecific.getAllStudentClasses();
        Map<String, Object> studentsClassMap = new HashMap<String, Object>();
        for (StudentClass studentsClass : studentsClasses) {
            studentsClassMap.put("_id", studentsClass.get_id());
            studentsClassMap.put("name", studentsClass.getName());
            studentsClassMap.put("class_index", studentsClass.getSrialNo());
            studentsClassMap.put("active_test_id", studentsClass.getActiveTestID());
            mDatabase.child("37230015")
                    .child("studentClasses")
                    .child(Integer.toString(studentsClass.getSrialNo()))
                    .updateChildren(studentsClassMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Write was successful!
                    // ...
                    Log.d("LND", "Data insertion Completed");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Write failed
                    // ...
                    Log.e("LND", "Data insertion Not completed");
                }
            });
        }

    }

    public void uploadTests() {
        ArrayList<Test> tests = new ArrayList<Test>();
        DBHelperSpecific dbHelperSpecific = new DBHelperSpecific(getApplicationContext());
        tests = dbHelperSpecific.getAllTests();
        Map<String, Object> testsMap = new HashMap<String, Object>();
        for (Test test : tests) {
            testsMap.put("_id", test.get_id());
            testsMap.put("class_test", test.getClassTest());
            testsMap.put("subject", test.getSubject());
            testsMap.put("chapter", test.getChapter());
            testsMap.put("sections", test.getSections());
            testsMap.put("data_time", test.getDataTime());
            testsMap.put("total_time", test.getTotalTime());
            testsMap.put("total_questions", test.getTotalQuestions());

            mDatabase.child("37230015")
                    .child("studentClasses")
                    .child(test.getClassTest())
                    .child("subjects")
                    .child(test.getSubject())
                    .child("chapters")
                    .child(test.getChapter())
                    .child("section")
                    .child(test.getSections())
                    .updateChildren(testsMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Write was successful!
                    // ...
                    Log.d("LND", "Data insertion Completed");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Write failed
                    // ...
                    Log.e("LND", "Data insertion Not completed");
                }
            });
        }

//                Map<String, Object> resultMap = new HashMap<String, Object>();
//                resultMap.put("uid", "123");
//                resultMap.put("author", "Aslam");
//
//                mDatabase.child("37230015").updateChildren(resultMap);
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

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

//        mDatabase.child("users").child(userId).setValue(user);
//        mDatabase.child("users").child(userId).setValue("abc");
        mDatabase.child("users").child(name + "/me").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Write was successful!
                // ...
                ToastMessage.ShowToastMessage(getApplicationContext(), "Succfull data inserted");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Write failed
                // ...
                ToastMessage.ShowToastMessage(getApplicationContext(), "Not successfull");
            }
        });
    }

    @IgnoreExtraProperties
    public class User {

        public String username;
        public String email;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }
    }
}
