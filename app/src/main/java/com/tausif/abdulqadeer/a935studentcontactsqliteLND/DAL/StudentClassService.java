package com.tausif.abdulqadeer.a935studentcontactsqliteLND.DAL;

import com.tausif.abdulqadeer.a935studentcontactsqliteLND.DBHelperSpecific;
import com.tausif.abdulqadeer.a935studentcontactsqliteLND.StudentClass;

import java.util.ArrayList;

/**
 * Created by Muhammad Tausif on 10/13/2018.
 *
 */

public class StudentClassService {

    DBHelperSpecific dbHelperSpecific;

    public StudentClassService(DBHelperSpecific dbHelperSpecific) {
        this.dbHelperSpecific = dbHelperSpecific;
    }

    // Method for Student class
    public boolean insertStudentClass( StudentClass studentClass){
        return true;
    }

    public ArrayList<StudentClass> getAllStudentClasses(){
        return new ArrayList<StudentClass>();
    }

}
