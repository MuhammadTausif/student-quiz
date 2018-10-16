package com.tausif.abdulqadeer.a935studentcontactsqliteLND;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by s.c on 10/16/2018.
 */

public class ToastMessage {
    public static void ShowToastMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
