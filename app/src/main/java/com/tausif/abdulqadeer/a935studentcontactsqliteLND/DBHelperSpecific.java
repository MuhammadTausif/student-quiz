package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.CHAPTER;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.CLASS_TEST;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.ID_QUESTIONS_TABLE;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.ID_STUDENT_TABLE;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.ID_TEST_TABLE;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.OPTION_A;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.OPTION_B;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.OPTION_C;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.OPTION_D;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.QUESTION;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.QUESTION_TEST_ID_F;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.ROLL_NO;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.SECTIONS;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.STD_CLASS;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.STUDENT_TABLE;
import static com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelper.TEST_TABLE;

/**
 * Created by Abdul Qadeer on 10/9/2017.
 */

public class DBHelperSpecific {
    private DBHelper dbHelper;
    private Context dbHelperSpecificContext;
    private SQLiteDatabase studentDB;

    public DBHelperSpecific(Context context) {
        this.dbHelperSpecificContext = context;
        dbHelper = new DBHelper(context);
    }

    /**
     * Function for Select test for Add Question class
     *
     * @param classTest
     * @return
     */
    public ArrayList<String> getDistinctTestSubjects(int classTest) {

        ArrayList<String> classes = new ArrayList<>();
        classes.add("-1");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + DBHelper.SUBJECT + " FROM " + TEST_TABLE + " WHERE " +
                CLASS_TEST + " =  " + classTest, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            String tempSubject;
            while (cursor.isAfterLast() == false) {
                tempSubject = cursor.getString(cursor.getColumnIndex(DBHelper.SUBJECT));
                classes.add(tempSubject);

                cursor.moveToNext();
            }
        } else {
//            classes.add("-1");
        }
        return classes;
    }

    public ArrayList<String> getDistinctTestClasses() {

        ArrayList<String> classes = new ArrayList<>();
        classes.add("-1");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + CLASS_TEST + " FROM " + TEST_TABLE, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            int tempClass;
            while (!cursor.isAfterLast()) {
                tempClass = cursor.getInt(cursor.getColumnIndex(CLASS_TEST));
                classes.add(String.valueOf(tempClass));

                cursor.moveToNext();
            }
        }
//        else {
//            classes.add("-1");
//        }
        cursor.close();
        return classes;
    }

    public ArrayList<String> getDistinctTestChapters(String classTest, String subject) {

        ArrayList<String> chapters = new ArrayList<>();
        chapters.add("-1");
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT " + CHAPTER + " FROM " + TEST_TABLE + " WHERE " +
                DBHelper.SUBJECT + " ='" + subject + "' AND " + CLASS_TEST + " = " + classTest, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            int tempClass;
            while (cursor.isAfterLast() == false) {
                tempClass = cursor.getInt(cursor.getColumnIndex(CHAPTER));
                chapters.add(String.valueOf(tempClass));

                cursor.moveToNext();
            }
        }
//        else {
////            classes.add("-1");
//        }
        return chapters;
    }

    public ArrayList<String> getDistinctTestSections(String classTest, String subject, String chapter) {

        ArrayList<String> chapters = new ArrayList<>();
        chapters.add("-1");
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT " + SECTIONS + " FROM " + TEST_TABLE + " WHERE " +
                DBHelper.SUBJECT + " ='" + subject + "' AND " + CLASS_TEST + " = " + classTest + " AND " + CHAPTER + " = " + chapter, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            String tempSections;
            while (!cursor.isAfterLast()) {
                tempSections = cursor.getString(cursor.getColumnIndex(SECTIONS));
                chapters.add(String.valueOf(tempSections));

                cursor.moveToNext();
            }
        }
//        else {
////            classes.add("-1");
//        }
        return chapters;
    }

    public int getTestId(String classTest, String subject, String chapter, String sections) {

        int testId = -1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT " + ID_TEST_TABLE + " FROM " + TEST_TABLE + " WHERE " +
                DBHelper.SUBJECT + " ='" + subject + "' AND " + CLASS_TEST + " = " + classTest + " AND " + CHAPTER + " = " + chapter +
                " AND " + SECTIONS + " = '" + sections + "'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            testId = cursor.getInt(cursor.getColumnIndex(ID_TEST_TABLE));
        }
//        else {
//
//        }
        cursor.close();
        return testId;
    }

    /**
     * Functiono for add exam class
     *
     * @return
     */
    public ArrayList<String> getDistinctStudentClasses() {

        ArrayList<String> classes = new ArrayList<>();
        classes.add("-1");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + STD_CLASS + " FROM " + STUDENT_TABLE, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            int tempClass;
            while (!cursor.isAfterLast()) {
                tempClass = cursor.getInt(cursor.getColumnIndex(STD_CLASS));
                classes.add(String.valueOf(tempClass));

                cursor.moveToNext();
            }
        }
        cursor.close();
        return classes;
    }

    public ArrayList<String> getDistinctStudentRollNoInAClass(int studentsClass) {

        ArrayList<String> studentsRollNo = new ArrayList<>();
        studentsRollNo.add("-1");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + ROLL_NO + " FROM " +
                STUDENT_TABLE + " WHERE " + STD_CLASS + " = " + studentsClass, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            int tempClass;
            while (!cursor.isAfterLast()) {
                tempClass = cursor.getInt(cursor.getColumnIndex(ROLL_NO));
                studentsRollNo.add(String.valueOf(tempClass));

                cursor.moveToNext();
            }
        }
        cursor.close();
        return studentsRollNo;
    }

    public ArrayList<String> getDistinctStudentDetailFromRollNoInAClass(int studentsClass, int roLLNo) {

        ArrayList<String> studentsIdAndName = new ArrayList<>();
        studentsIdAndName.add("-1");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT *  FROM " +
                STUDENT_TABLE + " WHERE " + STD_CLASS + " = " + studentsClass +
                " AND " + ROLL_NO + " = " + roLLNo, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            int tempClass;
            String tempName;
            tempClass = cursor.getInt(cursor.getColumnIndex(ID_STUDENT_TABLE));
            tempName = cursor.getString(cursor.getColumnIndex(DBHelper.NAME));
            studentsIdAndName.add(String.valueOf(tempClass));
            studentsIdAndName.add(tempName);
        }
        cursor.close();
        return studentsIdAndName;
    }

    public ArrayList<String> getDistinctTestIdFromClassSubjectChapterSections(String testClass, String testSubject, String testChapterss, String testSections) {

        ArrayList<String> testsForId = new ArrayList<>();
        testsForId.add("-1");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM " +
                TEST_TABLE + " WHERE " +
                CLASS_TEST + " = " + testClass + " AND " +
                DBHelper.SUBJECT + " = '" + testSubject + "' AND " +
                CHAPTER + " = " + testChapterss + " AND " +
                SECTIONS + " = '" + testSections + "'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            String tempIdForTest;
            tempIdForTest = cursor.getString(cursor.getColumnIndex(ID_TEST_TABLE));
            testsForId.add(tempIdForTest);
        }
        cursor.close();
        return testsForId;
    }

    /**
     * Functiono for quiz
     *
     * @return
     */

    public ArrayList<Question> getAllQuestionFromTestId(int testId) {

        ArrayList<Question> questionsListForTestId = new ArrayList<>();
//        testsForId.add("-1");

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tempQuery = "SELECT  * FROM " +
                DBHelper.QUESTIONS_TABLE + " WHERE " +
                QUESTION_TEST_ID_F + " = " + testId;

        Cursor cursor = db.rawQuery(tempQuery, null);
        cursor.moveToFirst();


        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                Question tempQuestion = new Question();

                int tempQuestionId = cursor.getInt(cursor.getColumnIndex(ID_QUESTIONS_TABLE));
                String tempQuestionStr = cursor.getString(cursor.getColumnIndex(QUESTION));
                String tempOptionAStr = cursor.getString(cursor.getColumnIndex(OPTION_A));
                String tempOptionBStr = cursor.getString(cursor.getColumnIndex(OPTION_B));
                String tempOptionCStr = cursor.getString(cursor.getColumnIndex(OPTION_C));
                String tempOptionDStr = cursor.getString(cursor.getColumnIndex(OPTION_D));

                tempQuestion.setQuestionId(tempQuestionId);
                tempQuestion.setQuestion(tempQuestionStr);
                tempQuestion.setOptionA(tempOptionAStr);
                tempQuestion.setOptionB(tempOptionBStr);
                tempQuestion.setOptionC(tempOptionCStr);
                tempQuestion.setOptionD(tempOptionDStr);

                questionsListForTestId.add(tempQuestion);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return questionsListForTestId;
    }

    /**
     * Functiono for quiz
     *
     * @return
     */

    // This method can change only the Question text, and Options. Test ID, class, subject, section, or chapter can not be changed
    public Question getQuestionFromQuestionId(int questionId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String tempQuery = "SELECT  * FROM " +
                DBHelper.QUESTIONS_TABLE + " WHERE " +
                DBHelper.ID_QUESTIONS_TABLE + " = " + questionId;

        Cursor cursor = db.rawQuery(tempQuery, null);
        cursor.moveToFirst();

        Question tempQuestion = new Question();

        if (cursor.getCount() > 0) {

            int tempQuestionId = cursor.getInt(cursor.getColumnIndex(ID_QUESTIONS_TABLE));
            String tempQuestionStr = cursor.getString(cursor.getColumnIndex(QUESTION));
            String tempOptionAStr = cursor.getString(cursor.getColumnIndex(OPTION_A));
            String tempOptionBStr = cursor.getString(cursor.getColumnIndex(OPTION_B));
            String tempOptionCStr = cursor.getString(cursor.getColumnIndex(OPTION_C));
            String tempOptionDStr = cursor.getString(cursor.getColumnIndex(OPTION_D));

            tempQuestion.setQuestionId(tempQuestionId);
            tempQuestion.setQuestion(tempQuestionStr);
            tempQuestion.setOptionA(tempOptionAStr);
            tempQuestion.setOptionB(tempOptionBStr);
            tempQuestion.setOptionC(tempOptionCStr);
            tempQuestion.setOptionD(tempOptionDStr);

        }
        cursor.close();
        return tempQuestion;
    }

    public boolean updateQuestion(Question question) {

        int id = question.getQuestionId();
        String questionText = question.getQuestion();
        String optionA = question.getOptionA();
        String optionB = question.getOptionB();
        String optionC = question.getOptionC();
        String optionD = question.getOptionD();

        ContentValues values = new ContentValues();
        values.put(DBHelper.QUESTION, questionText);
        values.put(DBHelper.OPTION_A, optionA);
        values.put(DBHelper.OPTION_B, optionB);
        values.put(DBHelper.OPTION_C, optionC);
        values.put(DBHelper.OPTION_D, optionD);

        String where = DBHelper.ID_QUESTIONS_TABLE + " = " + id;

        studentDB = dbHelper.getWritableDatabase();

        int numberOfUpdatedRows = studentDB.update(DBHelper.QUESTIONS_TABLE, values, where, null);

        if (numberOfUpdatedRows == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteQuestion(Question question) {

        int id = question.getQuestionId();

        String where = DBHelper.ID_QUESTIONS_TABLE + " = " + id;

        studentDB = dbHelper.getWritableDatabase();

        int numberOfDeletedRows = studentDB.delete(DBHelper.QUESTIONS_TABLE, where, null);

        if (numberOfDeletedRows == 1) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Methods for student view class
     */
    // This method would get all students belonging to pereterized class
    public ArrayList<Student> getAllStudentsOfSpecificClass(int classStd) {

        ArrayList<Student> studentsArrayList = new ArrayList<Student>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String tempQuery = "SELECT * FROM " + DBHelper.STUDENT_TABLE + " WHERE  " + STD_CLASS + " = " + classStd;
        Cursor cursor = db.rawQuery(tempQuery, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            Student student;
            while (cursor.isAfterLast() == false) {
                student = new Student();
                student.set_id(cursor.getInt(cursor.getColumnIndex(ID_STUDENT_TABLE)));
                student.setName(cursor.getString(cursor.getColumnIndex(DBHelper.NAME)));
                student.setFatherName(cursor.getString(cursor.getColumnIndex(DBHelper.FATHER_NAME)));
                student.setAddress(cursor.getString(cursor.getColumnIndex(DBHelper.ADDRESS)));
                student.setPhone(cursor.getString(cursor.getColumnIndex(DBHelper.PHONE)));
                student.setClassStd(cursor.getInt(cursor.getColumnIndex(DBHelper.STD_CLASS)));
                student.setRollNo(cursor.getInt(cursor.getColumnIndex(DBHelper.ROLL_NO)));

                studentsArrayList.add(student);
                cursor.moveToNext();
            }
        }
        return studentsArrayList;
    }

    public ArrayList<String> getAllRollNoOfSpecificClass(int classStd) {

        ArrayList<String> studentsRollNoArrayList = new ArrayList<String>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String tempQuery = "SELECT DISTINCT " + DBHelper.ROLL_NO + " FROM " + DBHelper.STUDENT_TABLE + " WHERE  " + STD_CLASS + " = " + classStd;
        Cursor cursor = db.rawQuery(tempQuery, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                int tempRollNoInt = cursor.getInt(cursor.getColumnIndex(DBHelper.ROLL_NO));
                String tempRollNo = String.valueOf(tempRollNoInt);

                studentsRollNoArrayList.add(tempRollNo);
                cursor.moveToNext();
            }
        }
        return studentsRollNoArrayList;
    }

    public boolean updateStudent(Student student) {

        int id = student.get_id();
        int classStd = student.getClassStd();
        int rollNo = student.getRollNo();
        String name = student.getName();
        String fatherName = student.getFatherName();
        String address = student.getAddress();
        String phone = student.getPhone();

        ContentValues values = new ContentValues();
        values.put(DBHelper.NAME, name);
        values.put(DBHelper.FATHER_NAME, fatherName);
        values.put(DBHelper.ADDRESS, address);
        values.put(DBHelper.PHONE, phone);
        values.put(DBHelper.STD_CLASS, classStd);
        values.put(DBHelper.ROLL_NO, rollNo);

        String where = DBHelper.ID_STUDENT_TABLE + " = " + id;

        studentDB = dbHelper.getWritableDatabase();

        int numberOfUpdatedRows = studentDB.update(DBHelper.STUDENT_TABLE, values, where, null);

        if (numberOfUpdatedRows == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteStudent(Student student) {

        int id = student.get_id();
        int classStd = student.getClassStd();
        int rollNo = student.getRollNo();
        String name = student.getName();
        String fatherName = student.getFatherName();
        String address = student.getAddress();
        String phone = student.getPhone();

        String where = DBHelper.ID_STUDENT_TABLE + " = " + id;

        studentDB = dbHelper.getWritableDatabase();

        int numberOfDeletedRows = studentDB.delete(DBHelper.STUDENT_TABLE, where, null);

        if (numberOfDeletedRows == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for Test class
     */
    public ArrayList<Test> getAllTestsRecords(String where) {

        ArrayList<Test> testArrayList = new ArrayList<Test>();
        Test tempTest;

        studentDB = dbHelper.getReadableDatabase();

        Cursor cursor = studentDB.rawQuery("SELECT * FROM " + DBHelper.TEST_TABLE + where, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                tempTest = new Test();
                tempTest.set_id(cursor.getInt(cursor.getColumnIndex(DBHelper.ID_TEST_TABLE)));
                tempTest.setSubject(cursor.getString(cursor.getColumnIndex(DBHelper.SUBJECT)));
                tempTest.setClassTest(String.valueOf(cursor.getInt(cursor.getColumnIndex(DBHelper.CLASS_TEST))));
                tempTest.setChapter(String.valueOf(cursor.getInt(cursor.getColumnIndex(DBHelper.CHAPTER))));
                tempTest.setSections(cursor.getString(cursor.getColumnIndex(DBHelper.SECTIONS)));
                tempTest.setDataTime(cursor.getString(cursor.getColumnIndex(DBHelper.DATA_TIME)));
                tempTest.setTotalTime(String.valueOf(cursor.getInt(cursor.getColumnIndex(DBHelper.TOTAL_TIME))));
                tempTest.setTotalQuestions(String.valueOf(cursor.getInt(cursor.getColumnIndex(DBHelper.TOTAL_QUESTIONS))));

                testArrayList.add(tempTest);
                cursor.moveToNext();
            }
        }
        return testArrayList;
    }

    public ArrayList<Test> getAllTestsRecordsOfClass(String classTest) {
        String whereClause = " WHERE " + DBHelper.CLASS_TEST + " = " + classTest;
        return getAllTestsRecords(whereClause);
    }

    public ArrayList<Test> getAllTestsRecordsOfClassAndSubject(String classTest, String subjectTest) {
        String whereClause = " WHERE = " + DBHelper.CLASS_TEST + " " + classTest + " AND " + DBHelper.SUBJECT + " = " + subjectTest;
        return getAllTestsRecords(whereClause);
    }

    public ArrayList<Test> getAllTestsRecordsOfClassAndSubjectAndChapter(String classTest, String subjectTest, String chapterTest) {
        String whereClause = " WHERE = " + DBHelper.CLASS_TEST + " " + classTest + " AND "
                + DBHelper.SUBJECT + " = " + subjectTest + " AND " + DBHelper.CHAPTER + " = " + chapterTest;
        return getAllTestsRecords(whereClause);
    }

    public boolean updateTest(Test test) {

        int id = test.get_id();
        String classTest = test.getClassTest();
        String subject = test.getSubject();
        String chapter = test.getChapter();
        String sections = test.getSections();
        String dateTime = test.getDataTime();
        String totalTime = test.getTotalTime();
        String totalQuestions = test.getTotalQuestions();

        ContentValues values = new ContentValues();
        values.put(DBHelper.CLASS_TEST, classTest);
        values.put(DBHelper.SUBJECT, subject);
        values.put(DBHelper.CHAPTER, chapter);
        values.put(DBHelper.SECTIONS, sections);
        values.put(DBHelper.DATA_TIME, dateTime);
        values.put(DBHelper.TOTAL_TIME, totalTime);
        values.put(DBHelper.TOTAL_QUESTIONS, totalQuestions);

        String where = DBHelper.ID_TEST_TABLE + " = " + id;

        studentDB = dbHelper.getWritableDatabase();

        int numberOfUpdatedRows = studentDB.update(DBHelper.TEST_TABLE, values, where, null);

        if (numberOfUpdatedRows == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteTest(Test test) {

        int id = test.get_id();

        String where = DBHelper.ID_TEST_TABLE + " = " + id;

        studentDB = dbHelper.getWritableDatabase();

        int numberOfDeletedRows = studentDB.delete(DBHelper.TEST_TABLE, where, null);

        if (numberOfDeletedRows == 1) {
            return true;
        } else {
            return false;
        }
    }

}