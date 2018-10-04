package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ViewClassesActivity extends AppCompatActivity {

    ListView classesListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classes);
        classesListView=(ListView) findViewById(R.id.classes_view_list_view);
        classesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView  listItemView=(TextView) view;
                String classOfStudents=listItemView.getText().toString();

                Intent intent=new Intent(ViewClassesActivity.this, ViewStudentActivity.class);
                intent.putExtra("CLASS_ID_FOR_STUDENTS", classOfStudents);
                startActivity(intent);
//                AlertMessage.ShowAlertMessage(ViewClassesActivity.this,listItemView.getText().toString());
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
}
