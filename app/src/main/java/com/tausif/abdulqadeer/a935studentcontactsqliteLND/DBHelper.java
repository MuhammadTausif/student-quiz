package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Abdul Qadeer on 10/6/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    // region Database, Tables, and Columns Names
    // Database
    public final static String SCHOOL_DB = "school_db";

    // Database Tables
    public final static String STUDENT_TABLE = "students_table";
    public final static String STUDENT_CLASS_TABLE = "student_class_table";
    public final static String TEST_TABLE = "test_table";
    public final static String QUESTIONS_TABLE = "questions_table";
    public final static String EXAM_TABLE = "exam_table";
    public final static String RESULT_TABLE = "result_table";

    // Columns of STUDENT_CLASS_TABLE
    public final static String ID_STUDENT_CLASS_TABLE = "_id";
    public final static String STUDENT_CLASS_NAME = "name";
    public final static String CLASS_INDEX = "class_index";
    public final static String ACTIVE_TEST_ID = "active_test_id";

    // Columns of STUDENT_TABLE
    public final static String ID_STUDENT_TABLE = "_id";
    public final static String NAME = "name";
    public final static String FATHER_NAME = "fathername";
    public final static String ADDRESS = "address";
    public final static String PHONE = "phone";
    public final static String STD_CLASS = "stdclass";
    public final static String ROLL_NO = "roll_no";

    // Columns of TEST_TABLE
    public final static String ID_TEST_TABLE = "_id";
    public final static String CLASS_TEST = "class_test";
    public final static String SUBJECT = "subject";
    public final static String CHAPTER = "chapter";
    public final static String SECTIONS = "sections";
    public final static String DATA_TIME = "data_time";
    public final static String TOTAL_TIME = "total_time";
    public final static String TOTAL_QUESTIONS = "total_questions";

    // Columns of QUESTIONS_TABLE
    public final static String ID_QUESTIONS_TABLE = "_id";
    public final static String QUESTION_TEST_ID_F = "test_id_f";
    public final static String QUESTION = "question";
    public final static String OPTION_A = "option_a";
    public final static String OPTION_B = "option_b";
    public final static String OPTION_C = "option_c";
    public final static String OPTION_D = "option_d";

    // Columns of EXAM_TABLE
    public final static String ID_EXAM_TABLE = "_id";
    public final static String DATE_TIME = "date_time";
    public final static String EXAM_TEST_ID_F = "test_id_f";
    public final static String EXAM_STUDENT_ID_F = "student_id_f";

    // Columns of RESULT_TABLE
    public final static String ID_RESULT_TABLE = "_id";
    public final static String RESULT_EXAM_ID_F = "exam_id";
    public final static String Q1_ANSWER = "q1_answer";
    public final static String Q2_ANSWER = "q2_answer";
    public final static String Q3_ANSWER = "q3_answer";
    public final static String Q4_ANSWER = "q4_answer";
    public final static String Q5_ANSWER = "q5_answer";
    public final static String Q6_ANSWER = "q6_answer";
    public final static String Q7_ANSWER = "q7_answer";
    public final static String Q8_ANSWER = "q8_answer";
    public final static String Q9_ANSWER = "q9_answer";
    public final static String Q10_ANSWER = "q10_answer";

    // endregion

    // region Create Table Quries

    private final String INTEGER_TYPE = " INTEGER, ";
    private final String TEXT_TYPE = " TEXT, ";

    // Create STUDENT_TBALE query
    String tempStudentTableCreate =
            NAME + TEXT_TYPE +
                    FATHER_NAME + TEXT_TYPE +
                    ADDRESS + TEXT_TYPE +
                    PHONE + TEXT_TYPE +
                    STD_CLASS + INTEGER_TYPE +
                    ROLL_NO + " INTEGER ";

    String STUDENT_CREATE_TABLE = createTableStatement(STUDENT_TABLE, ID_STUDENT_TABLE, tempStudentTableCreate);

    // Create STUDENT_CLASS_TBALE query
    String tempStudentClassTableCreate =
            STUDENT_CLASS_NAME + TEXT_TYPE +
                    CLASS_INDEX + INTEGER_TYPE+
                    ACTIVE_TEST_ID + " INTEGER ";

    String STUDENT_CLASS_CREATE_TABLE = createTableStatement(STUDENT_CLASS_TABLE, ID_STUDENT_CLASS_TABLE, tempStudentClassTableCreate);

    // Create TEST_TBALE query
    String tempTestTableCreate =
            CLASS_TEST + INTEGER_TYPE +
                    SUBJECT + TEXT_TYPE +
                    CHAPTER + INTEGER_TYPE +
                    SECTIONS + TEXT_TYPE +
                    DATA_TIME + TEXT_TYPE +
                    TOTAL_TIME + TEXT_TYPE +
                    TOTAL_QUESTIONS + " INTEGER ";

    String TEST_CREATE_TABLE = createTableStatement(TEST_TABLE, ID_TEST_TABLE, tempTestTableCreate);

    // Create QUESTIONS_TBALE query
    String tempQuestionTableCreate =
            QUESTION_TEST_ID_F + INTEGER_TYPE +
                    QUESTION + TEXT_TYPE +
                    OPTION_A + TEXT_TYPE +
                    OPTION_B + TEXT_TYPE +
                    OPTION_C + TEXT_TYPE +
                    OPTION_D + " TEXT";
    String QUESTIONS_CREATE_TABLE = createTableStatement(QUESTIONS_TABLE, ID_QUESTIONS_TABLE, tempQuestionTableCreate);

    // Create EXAM_TBALE query
    String tempExamtTableCreate =
            DATA_TIME + TEXT_TYPE +
                    EXAM_TEST_ID_F + INTEGER_TYPE +
                    EXAM_STUDENT_ID_F + " INTEGER";

    String RESULT_CREATE_TABLE = createTableStatement(EXAM_TABLE, ID_RESULT_TABLE, tempExamtTableCreate);

    // Create RESULT_TBALE query
    String tempResultTableCreate =
            RESULT_EXAM_ID_F + INTEGER_TYPE +
                    Q1_ANSWER + TEXT_TYPE +
                    Q2_ANSWER + TEXT_TYPE +
                    Q3_ANSWER + TEXT_TYPE +
                    Q4_ANSWER + TEXT_TYPE +
                    Q5_ANSWER + TEXT_TYPE +
                    Q6_ANSWER + TEXT_TYPE +
                    Q7_ANSWER + TEXT_TYPE +
                    Q8_ANSWER + TEXT_TYPE +
                    Q9_ANSWER + TEXT_TYPE +
                    Q10_ANSWER + " TEXT";
    String EXAM_CREATE_TABLE = createTableStatement(RESULT_TABLE, ID_EXAM_TABLE, tempResultTableCreate);


    private String createTableStatement(String tableName, String id, String columns) {

        return "CREATE TABLE IF NOT EXISTS " +
                tableName +
                " ( " +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                columns +
                " )";
    }
    // endregion

    // region Constructors
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBHelper(Context context) {
        super(context, SCHOOL_DB, null, 1);
        onCreate(this.getWritableDatabase());
    }
    // endregion

    /**
     * @param sqLiteDatabase SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(STUDENT_CREATE_TABLE);
        sqLiteDatabase.execSQL(STUDENT_CLASS_CREATE_TABLE);
        sqLiteDatabase.execSQL(TEST_CREATE_TABLE);
        sqLiteDatabase.execSQL(QUESTIONS_CREATE_TABLE);
        sqLiteDatabase.execSQL(EXAM_CREATE_TABLE);
        sqLiteDatabase.execSQL(RESULT_CREATE_TABLE);

        /**
         *  Method for initial seed.
         */
//        seedData();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //        sqLiteDatabase.execSQL("DROP IF TABLE EXIST " + TEST_TABLE);
        //        onCreate(sqLiteDatabase);
    }

    /**
     * Method to seed the data at initial data logging.
     */
    public void seedData(){
        // Inserting students
        insertStudent("Aslam Khan", "Sulman Khan", "Kot", "322", 0, 1 );
        insertStudent("Akram Khan", "Sulman Khan", "Kot", "322", 0, 2 );
        insertStudent("Asghar Khan", "Sulman Khan", "Kot", "322", 0, 3 );

        // Inserting Test
        insertTest(0, "English", 1, "1", "01/01/2018", 10, 10);
        insertTest(0, "English", 1, "2", "01/01/2018", 10, 10);
        insertTest(0, "English", 1, "3", "01/01/2018", 10, 10);

        // Inserting Questions to above test
        insertQuestion(1, "What is Urdu", "Urdu", "English", "Punjabi", "Don't Know" );
        insertQuestion(1, "What is Urdu", "Urdu", "English", "Punjabi", "Don't Know" );
        insertQuestion(1, "What is Urdu", "Urdu", "English", "Punjabi", "Don't Know" );
        insertQuestion(1, "What is Urdu", "Urdu", "English", "Punjabi", "Don't Know" );
        insertQuestion(1, "What is Urdu", "Urdu", "English", "Punjabi", "Don't Know" );
        insertQuestion(1, "What is Urdu", "Urdu", "English", "Punjabi", "Don't Know" );
        insertQuestion(1, "What is Urdu", "Urdu", "English", "Punjabi", "Don't Know" );
        insertQuestion(1, "What is Urdu", "Urdu", "English", "Punjabi", "Don't Know" );
        insertQuestion(1, "What is Urdu", "Urdu", "English", "Punjabi", "Don't Know" );
        insertQuestion(1, "What is Urdu", "Urdu", "English", "Punjabi", "Don't Know" );

        // Inserting Classes
        insertStudentClass("Nursary", 0 , -1);
        insertStudentClass("One", 1 , -1);
        insertStudentClass("Two", 2 , -1);
        insertStudentClass("Three", 3 , -1);
        insertStudentClass("Four", 4 , -1);
        insertStudentClass("Five", 5 , -1);
        insertStudentClass("Six", 6 , -1);
        insertStudentClass("Seven", 7 , -1);
        insertStudentClass("Eight", 8 , -1);
        insertStudentClass("Nine", 9 , -1);
        insertStudentClass("10", 10 , -1);

    }

    /**
     * @param name Name of the student.
     * @param fatherName Father Name of the student
     * @param address Address of the student
     * @param phone Phone number to whome student can be connected
     * @param stdClass Student class
     * @param roll_no roll number of the student.
     * @return true if insert student is succesfull.
     */
    // region Students record manipulation methods
    public boolean insertStudent(String name, String fatherName, String address, String phone, int stdClass, int roll_no) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(FATHER_NAME, fatherName);
        values.put(ADDRESS, address);
        values.put(PHONE, phone);
        values.put(STD_CLASS, stdClass);
        values.put(ROLL_NO, roll_no);

        long insert_result = db.insert(STUDENT_TABLE, null, values);

        if (insert_result == -1) {
            return false;
        }
        {
            return true;
        }
    }

    public ArrayList<String> getAllStudentsRecords() {

        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_TABLE, null);
        cursor.moveToFirst();

        StringBuilder builder = new StringBuilder();

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                builder = new StringBuilder();
                builder.append("ID: " + cursor.getInt(cursor.getColumnIndex(ID_STUDENT_TABLE)) + "\n");
                builder.append("Name: " + cursor.getString(cursor.getColumnIndex(NAME)) + "\n");
                builder.append("Father Name: " + cursor.getString(cursor.getColumnIndex(FATHER_NAME)) + "\n");
                builder.append("Address: " + cursor.getString(cursor.getColumnIndex(ADDRESS)) + "\n");
                builder.append("Phone: " + cursor.getString(cursor.getColumnIndex(PHONE)) + "\n");
                builder.append("Class: " + cursor.getInt(cursor.getColumnIndex(STD_CLASS)) + "\n");
                builder.append("Roll No: " + cursor.getInt(cursor.getColumnIndex(ROLL_NO)) + "\n");

                arrayList.add(builder.toString());
//                arrayList.add(cursor.getString(cursor.getColumnIndex(CHAPTER)));
                cursor.moveToNext();
            }
        }
        return arrayList;
    }
    // endregion

    // region Students' class record manipulation methods
    public boolean insertStudentClass(String student_class_name, int class_index, int active_test_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STUDENT_CLASS_NAME, student_class_name);
        values.put(CLASS_INDEX, class_index);
        values.put(ACTIVE_TEST_ID, active_test_id);

        long insert_result = db.insert(STUDENT_CLASS_TABLE, null, values);

        if (insert_result == -1) {
            return false;
        }
        {
            return true;
        }
    }

    public ArrayList<String> getAllStudentsClassRecordsInString() {

        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_CLASS_TABLE, null);
        cursor.moveToFirst();

        StringBuilder builder = new StringBuilder();

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                builder = new StringBuilder();
                builder.append("ID: " + cursor.getInt(cursor.getColumnIndex(ID_STUDENT_CLASS_TABLE)) + "\n");
                builder.append("Class Name: " + cursor.getString(cursor.getColumnIndex(STUDENT_CLASS_NAME)) + "\n");
                builder.append("Class Index: " + cursor.getInt(cursor.getColumnIndex(CLASS_INDEX)) + "\n");
                builder.append("Active Test ID: " + cursor.getInt(cursor.getColumnIndex(ACTIVE_TEST_ID)) + "\n");

                arrayList.add(builder.toString());
                cursor.moveToNext();
            }
        }
        return arrayList;
    }

    public ArrayList<StudentClass> getAllStudentsClassRecords() {

        ArrayList<StudentClass> arrayList = new ArrayList<StudentClass>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_CLASS_TABLE, null);
        cursor.moveToFirst();

//        StringBuilder builder = new StringBuilder();

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                StudentClass studentClass = new StudentClass();
                studentClass.set_id(cursor.getInt(cursor.getColumnIndex(ID_STUDENT_CLASS_TABLE)));
                studentClass.setName(cursor.getString(cursor.getColumnIndex(STUDENT_CLASS_NAME)));
                studentClass.setSrialNo(cursor.getInt(cursor.getColumnIndex(CLASS_INDEX)));
                studentClass.setActiveTestID(cursor.getInt(cursor.getColumnIndex(ACTIVE_TEST_ID)));

                arrayList.add(studentClass);
                cursor.moveToNext();
            }
        }
        return arrayList;
    }

    public ArrayList<StudentClass> getAllStudentsClassRecordsWhere(String where) {

        ArrayList<StudentClass> arrayList = new ArrayList<StudentClass>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_CLASS_TABLE + " WHERE " + where, null);
        cursor.moveToFirst();

//        StringBuilder builder = new StringBuilder();
        StudentClass studentClass = new StudentClass();

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                studentClass.setName(cursor.getString(cursor.getColumnIndex(STUDENT_CLASS_NAME)));
                studentClass.set_id(cursor.getInt(cursor.getColumnIndex(ID_STUDENT_CLASS_TABLE)));
                studentClass.setSrialNo(cursor.getInt(cursor.getColumnIndex(CLASS_INDEX)));
                studentClass.setActiveTestID(cursor.getInt(cursor.getColumnIndex(ACTIVE_TEST_ID)));

                arrayList.add(studentClass);
                cursor.moveToNext();
            }
        }
        return arrayList;
    }

    // endregion

    // region Test record manipulation methods
    public boolean insertTest(int class_test, String subject, int chapter, String sections, String dateTime, int totalTime, int totalQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CLASS_TEST, class_test);
        values.put(SUBJECT, subject);
        values.put(CHAPTER, chapter);
        values.put(SECTIONS, sections);
        values.put(DATA_TIME, dateTime);
        values.put(TOTAL_TIME, totalTime);
        values.put(TOTAL_QUESTIONS, totalQuestions);

        long insert_result = db.insert(TEST_TABLE, null, values);

        if (insert_result == -1) {
            return false;
        }
        {
            return true;
        }
    }

    public ArrayList<String> getAllTestsRecords() {

        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TEST_TABLE, null);
        cursor.moveToFirst();

        StringBuilder builder = new StringBuilder();

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                builder = new StringBuilder();
                builder.append("ID: " + cursor.getInt(cursor.getColumnIndex(ID_TEST_TABLE)) + "\n");
                builder.append("Subject: " + cursor.getString(cursor.getColumnIndex(SUBJECT)) + "\n");
                builder.append("Class: " + cursor.getInt(cursor.getColumnIndex(CLASS_TEST)) + "\n");
                builder.append("Chapter: " + cursor.getInt(cursor.getColumnIndex(CHAPTER)) + "\n");
                builder.append("Sections: " + cursor.getString(cursor.getColumnIndex(SECTIONS)) + "\n");
                builder.append("Date and Time: " + cursor.getString(cursor.getColumnIndex(DATA_TIME)) + "\n");
                builder.append("Total Time: " + cursor.getInt(cursor.getColumnIndex(TOTAL_TIME)) + "\n");
                builder.append("Total Questions: " + cursor.getInt(cursor.getColumnIndex(TOTAL_QUESTIONS)) + "\n");

                arrayList.add(builder.toString());
//                arrayList.add(cursor.getString(cursor.getColumnIndex(CHAPTER)));
                cursor.moveToNext();
            }
        }
        return arrayList;
    }
    // endregion

    // region Questions record manipulation methods
    public boolean insertQuestion(int test_id_f, String question, String optionA, String optionB, String optionC, String optionD) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QUESTION_TEST_ID_F, test_id_f);
        values.put(QUESTION, question);
        values.put(OPTION_A, optionA);
        values.put(OPTION_B, optionB);
        values.put(OPTION_C, optionC);
        values.put(OPTION_D, optionD);

        long insert_result = db.insert(QUESTIONS_TABLE, null, values);

        if (insert_result == -1) {
            return false;
        }
        {
            return true;
        }
    }

    public ArrayList<String> getAllQuestionsRecords() {

        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + QUESTIONS_TABLE, null);
        cursor.moveToFirst();

        StringBuilder builder = new StringBuilder();

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                builder = new StringBuilder();
                builder.append("ID: " + cursor.getInt(cursor.getColumnIndex(ID_QUESTIONS_TABLE)) + "\n");
                builder.append("Test ID: " + cursor.getInt(cursor.getColumnIndex(QUESTION_TEST_ID_F)) + "\n");
                builder.append("Question: " + cursor.getString(cursor.getColumnIndex(QUESTION)) + "\n");
                builder.append("Option B: " + cursor.getString(cursor.getColumnIndex(OPTION_A)) + "\n");
                builder.append("Option C: " + cursor.getString(cursor.getColumnIndex(OPTION_B)) + "\n");
                builder.append("Option D: " + cursor.getString(cursor.getColumnIndex(OPTION_C)) + "\n");
                builder.append("Option E: " + cursor.getString(cursor.getColumnIndex(OPTION_D)) + "\n");

                arrayList.add(builder.toString());
//                arrayList.add(cursor.getString(cursor.getColumnIndex(CHAPTER)));
                cursor.moveToNext();
            }
        }
        return arrayList;
    }
    // endregion

    // region Exam record manipulation methods
    public long insertExam(String data_time, int exam_test_id, int exam_student_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DATA_TIME, data_time);
        values.put(EXAM_TEST_ID_F, exam_test_id);
        values.put(EXAM_STUDENT_ID_F, exam_student_id);

        long insert_result = db.insert(EXAM_TABLE, null, values);

        return insert_result;
//        if (insert_result == -1) {
//            return false;
//        }
//        {
//            return true;
//        }
    }

    public ArrayList<String> getAllExamsRecords() {

        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + EXAM_TABLE, null);
        cursor.moveToFirst();

        StringBuilder builder = new StringBuilder();

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                builder = new StringBuilder();
                builder.append("ID: " + cursor.getInt(cursor.getColumnIndex(ID_EXAM_TABLE)) + "\n");
                builder.append("Date of Exam: " + cursor.getString(cursor.getColumnIndex(DATA_TIME)) + "\n");
                builder.append("Test ID: " + cursor.getInt(cursor.getColumnIndex(EXAM_TEST_ID_F)) + "\n");
                builder.append("Student ID: " + cursor.getInt(cursor.getColumnIndex(EXAM_STUDENT_ID_F)) + "\n");

                arrayList.add(builder.toString());
//                arrayList.add(cursor.getString(cursor.getColumnIndex(CHAPTER)));
                cursor.moveToNext();
            }
        }
        return arrayList;
    }

    // endregion

    // region Result record manipulation methods
    public boolean insertResult(int result_exam_id, String q1_answer, String q2_answer, String q3_answer, String q4_answer, String q5_answer, String q6_answer, String q7_answer, String q8_answer, String q9_answer, String q10_answer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RESULT_EXAM_ID_F, result_exam_id);
        values.put(Q1_ANSWER, q1_answer);
        values.put(Q2_ANSWER, q2_answer);
        values.put(Q3_ANSWER, q3_answer);
        values.put(Q4_ANSWER, q4_answer);
        values.put(Q5_ANSWER, q5_answer);
        values.put(Q6_ANSWER, q6_answer);
        values.put(Q7_ANSWER, q7_answer);
        values.put(Q8_ANSWER, q8_answer);
        values.put(Q9_ANSWER, q9_answer);
        values.put(Q10_ANSWER, q10_answer);

        long insert_result = db.insert(RESULT_TABLE, null, values);

        if (insert_result == -1) {
            return false;
        }
        {
            return true;
        }
    }

    public ArrayList<String> getAllResultsRecords() {

        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + RESULT_TABLE, null);
        cursor.moveToFirst();

        StringBuilder builder = new StringBuilder();

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                builder = new StringBuilder();
                builder.append("Result ID: " + cursor.getInt(cursor.getColumnIndex(ID_QUESTIONS_TABLE)) + "\n");
                builder.append("Exam ID: " + cursor.getInt(cursor.getColumnIndex(RESULT_EXAM_ID_F)) + "\n");
                builder.append("Question 1 Answer: " + cursor.getString(cursor.getColumnIndex(Q1_ANSWER)) + "\n");
                builder.append("Question 2 Answer: " + cursor.getString(cursor.getColumnIndex(Q2_ANSWER)) + "\n");
                builder.append("Question 3 Answer: " + cursor.getString(cursor.getColumnIndex(Q3_ANSWER)) + "\n");
                builder.append("Question 4 Answer: " + cursor.getString(cursor.getColumnIndex(Q4_ANSWER)) + "\n");
                builder.append("Question 5 Answer: " + cursor.getString(cursor.getColumnIndex(Q5_ANSWER)) + "\n");
                builder.append("Question 6 Answer: " + cursor.getString(cursor.getColumnIndex(Q6_ANSWER)) + "\n");
                builder.append("Question 7 Answer: " + cursor.getString(cursor.getColumnIndex(Q7_ANSWER)) + "\n");
                builder.append("Question 8 Answer: " + cursor.getString(cursor.getColumnIndex(Q8_ANSWER)) + "\n");
                builder.append("Question 9 Answer: " + cursor.getString(cursor.getColumnIndex(Q9_ANSWER)) + "\n");
                builder.append("Question 10 Answer: " + cursor.getString(cursor.getColumnIndex(Q10_ANSWER)) + "\n");

                arrayList.add(builder.toString());
//                arrayList.add(cursor.getString(cursor.getColumnIndex(CHAPTER)));
                cursor.moveToNext();
            }
        }
        return arrayList;
    }

    public ArrayList<Result> getResultOfWhere(String where){

        ArrayList<Result> resultsArrayList = new ArrayList<Result>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + RESULT_TABLE + " WHERE " + where, null);
        cursor.moveToFirst();

        Result result = new Result();

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                result.set_id(cursor.getInt(cursor.getColumnIndex(ID_RESULT_TABLE)));
                result.setExam_id(cursor.getInt(cursor.getColumnIndex(RESULT_EXAM_ID_F)));
                result.setAnsQ1(cursor.getString(cursor.getColumnIndex(Q1_ANSWER)));
                result.setAnsQ2(cursor.getString(cursor.getColumnIndex(Q2_ANSWER)));
                result.setAnsQ3(cursor.getString(cursor.getColumnIndex(Q3_ANSWER)));
                result.setAnsQ4(cursor.getString(cursor.getColumnIndex(Q4_ANSWER)));
                result.setAnsQ5(cursor.getString(cursor.getColumnIndex(Q5_ANSWER)));
                result.setAnsQ6(cursor.getString(cursor.getColumnIndex(Q6_ANSWER)));
                result.setAnsQ7(cursor.getString(cursor.getColumnIndex(Q7_ANSWER)));
                result.setAnsQ8(cursor.getString(cursor.getColumnIndex(Q8_ANSWER)));
                result.setAnsQ9(cursor.getString(cursor.getColumnIndex(Q9_ANSWER)));
                result.setAnsQ10(cursor.getString(cursor.getColumnIndex(Q10_ANSWER)));

                resultsArrayList.add(result);
                cursor.moveToNext();
            }
        }
        return resultsArrayList;
    }

    // endregion
}
