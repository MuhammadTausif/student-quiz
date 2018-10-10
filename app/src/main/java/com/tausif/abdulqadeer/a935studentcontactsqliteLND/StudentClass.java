package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

/**
 * Created by MuhammadTausif on 10/9/2018.
 */

public class StudentClass {

    // Columns of Student Class
    public int _id;
    public int srialNo;
    public String name;


    public StudentClass() {
    }

    public StudentClass(int srialNo, String name) {
        this.srialNo = srialNo;
        this.name = name;
    }

    public StudentClass(int _id, int srialNo, String name) {
        this._id = _id;
        this.srialNo = srialNo;
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getSrialNo() {
        return srialNo;
    }

    public void setSrialNo(int srialNo) {
        this.srialNo = srialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StudentClass{" +
                "_id=" + _id +
                ", srialNo=" + srialNo +
                ", name='" + name + '\'' +
                '}';
    }
}
