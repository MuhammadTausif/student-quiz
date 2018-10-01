package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

/**
 * Created by Abdul Qadeer on 10/23/2017.
 */

public class Test {

    // Columns of TEST_TABLE
    public int _id;
    public String classTest;
    public String subject;
    public String chapter;
    public String sections;
    public String dataTime;
    public String totalTime;
    public String totalQuestions;

    public Test() {

    }

    public Test(int _id, String classTest, String subject, String chapter, String sections, String dataTime, String totalTime, String totalQuestions) {
        this._id = _id;
        this.classTest = classTest;
        this.subject = subject;
        this.chapter = chapter;
        this.sections = sections;
        this.dataTime = dataTime;
        this.totalTime = totalTime;
        this.totalQuestions = totalQuestions;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getClassTest() {
        return classTest;
    }

    public void setClassTest(String classTest) {
        this.classTest = classTest;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(String totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
