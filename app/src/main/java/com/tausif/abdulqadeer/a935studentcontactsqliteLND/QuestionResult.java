package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

/**
 * Created by s.c on 10/18/2018.
 */

public class QuestionResult {
    int _id;
    int questionID;
    String selectedOption;

    public QuestionResult(int questionID, String selectedOption) {
        this.questionID = questionID;
        this.selectedOption = selectedOption;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
}
