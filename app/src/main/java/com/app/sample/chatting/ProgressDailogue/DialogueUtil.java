package com.app.sample.chatting.ProgressDailogue;

import android.app.Activity;
import android.app.ProgressDialog;

public class DialogueUtil {
    public static ProgressDialog showProgressDialog(Activity activity, String message) {
        ProgressDialog m_Dialog = new ProgressDialog(activity);
        try{

            m_Dialog.setMessage(message);
            m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            m_Dialog.setCancelable(false);
            m_Dialog.show();

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return m_Dialog;

    }
}
