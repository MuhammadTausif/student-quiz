package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Abdul Qadeer on 10/9/2017.
 */

public class AlertMessage {
    public static void ShowAlertMessage(Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
