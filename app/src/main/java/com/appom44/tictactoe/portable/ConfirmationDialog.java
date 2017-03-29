package com.appom44.tictactoe.portable;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.appom44.tictactoe.portable.IConfirmationDialogListener;

import java.util.ArrayList;

/**
 * Created by swar8080 on 12/20/2016.
 */

public class ConfirmationDialog {

    private Context context;
    private ArrayList<IConfirmationDialogListener> dialogListeners;
    private String title, message;

    public ConfirmationDialog(Context context, String message, String title) {
        dialogListeners = new ArrayList<IConfirmationDialogListener>();

        this.context = context;
        this.title = title;
        this.message = message;
    }

    public void registerConfirmationDialogListener(IConfirmationDialogListener listener){
        dialogListeners.add(listener);
    }


    public void getUserSelection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
            .setMessage(message)
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (IConfirmationDialogListener confirmationDialogListener : dialogListeners){
                        confirmationDialogListener.onNoClicked();
                    }
                    //dialog.cancel(); //done automatically by AlertDialogs
                }
            })
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (IConfirmationDialogListener confirmationDialogListener : dialogListeners){
                        confirmationDialogListener.onYesClicked();
                    }
                    //dialog.cancel(); //done automatically by AlertDialogs
                }
            });

        if (title != null){
            builder = builder.setTitle(title);
        }

        builder.create().show();
    }
}
