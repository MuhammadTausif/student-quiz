package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import java.util.Date;

/**
 * Created by s.c on 10/18/2018.
 */

public class Exam {

    // region Fields
    int id;
    String examDate;
    int testID, studentID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
    // endregion
}
