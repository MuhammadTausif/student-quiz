package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

/**
 * Created by Muhammad Tausif on 10/18/2018.
 */

public class ManipulateResult {
    public static ArrayList<QuestionResult> manipulateResult(Result result){
        ArrayList<QuestionResult> questionResults  = new ArrayList<QuestionResult>();

        String[] answer;
        // Manipulating Answers
        answer= result.getAnsQ1().split(",");
        questionResults.add(new QuestionResult(Integer.parseInt(answer[0]), answer[1]));
        answer= result.getAnsQ2().split(",");
        questionResults.add(new QuestionResult(Integer.parseInt(answer[0]), answer[1]));
        answer= result.getAnsQ3().split(",");
        questionResults.add(new QuestionResult(Integer.parseInt(answer[0]), answer[1]));
        answer= result.getAnsQ4().split(",");
        questionResults.add(new QuestionResult(Integer.parseInt(answer[0]), answer[1]));
        answer= result.getAnsQ5().split(",");
        questionResults.add(new QuestionResult(Integer.parseInt(answer[0]), answer[1]));
        answer= result.getAnsQ6().split(",");
        questionResults.add(new QuestionResult(Integer.parseInt(answer[0]), answer[1]));
        answer= result.getAnsQ7().split(",");
        questionResults.add(new QuestionResult(Integer.parseInt(answer[0]), answer[1]));
        answer= result.getAnsQ8().split(",");
        questionResults.add(new QuestionResult(Integer.parseInt(answer[0]), answer[1]));
        answer= result.getAnsQ9().split(",");
        questionResults.add(new QuestionResult(Integer.parseInt(answer[0]), answer[1]));
        answer= result.getAnsQ10().split(",");
        questionResults.add(new QuestionResult(Integer.parseInt(answer[0]), answer[1]));

        return questionResults;
    }

    @Contract(pure = true)
    public static int[] getResultSummry(Result result){
        int[] abc = {10,10,100};
        return abc;
    }
}
