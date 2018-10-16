package com.tausif.abdulqadeer.a935studentcontactsqliteLND.Models;

import com.tausif.abdulqadeer.a935studentcontactsqliteLND.StudentClass;

import java.util.ArrayList;

/**
 * Created by s.c on 10/10/2018.
 */

public class Subject {
    int _id;
    String name;
    StudentClass studentClass;

    public static ArrayList<Subject> SUBJECTS = new ArrayList<Subject>(){};

    public Subject() {
    }

    public Subject(int _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudentClass getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(StudentClass studentClass) {
        this.studentClass = studentClass;
    }
}
