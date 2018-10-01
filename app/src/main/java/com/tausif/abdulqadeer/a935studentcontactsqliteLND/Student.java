package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import java.io.Serializable;

/**
 * Created by Abdul Qadeer on 10/18/2017.
 */

public class Student implements Serializable {
    private int _id;

    private String name;

    private String fatherName;
    private String address;
    private String phone;
    private int classStd;
    private int rollNo;
    public Student() {
    }

    public Student(String name, String fatherName, String address, String phone, int classStd, int rollNo) {
        this.name = name;
        this.fatherName = fatherName;
        this.address = address;
        this.phone = phone;
        this.classStd = classStd;
        this.rollNo = rollNo;
    }

    @Override
    public String toString() {
        return "Student Detail\n ID: " + _id +"\n" +
                "Class: "+classStd+"\n"+
                "Roll No: "+rollNo+"\n"+
                "Name: "+name+"\n"+
                "Father Name: "+fatherName+"\n"+
                "Address: "+address+"\n"+
                "Phone: "+phone+"\n";
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getClassStd() {
        return classStd;
    }

    public void setClassStd(int classStd) {
        this.classStd = classStd;
    }

    public int getRollNo() {
        return rollNo;
    }

}
