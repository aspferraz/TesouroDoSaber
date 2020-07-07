package com.seventech.tesourodosaber.utils.view;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import com.seventech.tesourodosaber.R;
import androidx.appcompat.app.AlertDialog;


/**
 * Created by aspferraz on 20/09/2018.
 */

public final class Messages {

    private static final String TAG = Messages.class.getSimpleName();

    private static Messages sInstance = null;
    private Context mContext;

    private Messages(Context ctx) {
        mContext = ctx;
    }

    public static Messages with(Context ctx) {
        if (sInstance == null) {
            sInstance = new Messages(ctx);
        }
        else {
            sInstance.setContext(ctx);
        }

        return sInstance;
    }

    private void setContext(Context ctx) {
        mContext = ctx;
    }

    public void showConfirmationMessage(String title, String message, DialogInterface.OnClickListener positiveButtonClickListener) {
        showConfirmationMessage(title, message, mContext.getString(R.string.dlg_ok_btn_text), mContext.getString(R.string.dlg_cancel_btn_text), positiveButtonClickListener);
    }

    public void showConfirmationMessage(String title, String message, String positiveButtonLabel, String negativeButtonLabel, DialogInterface.OnClickListener positiveButtonClickListener) {
        AlertDialog dlg = new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveButtonLabel, positiveButtonClickListener)
                .setNegativeButton(negativeButtonLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        dlg.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                int btnTextColor = mContext.getResources().getColor(R.color.secondaryLightColor);
                int btnHeight = mContext.getResources().getDimensionPixelSize(R.dimen.alertdialog_button_height);
                int btnWidth  = mContext.getResources().getDimensionPixelSize(R.dimen.alertdialog_button_width);
                Button btnNegative = dlg.getButton(AlertDialog.BUTTON_NEGATIVE);
                Button btnPositive = dlg.getButton(AlertDialog.BUTTON_POSITIVE);
                btnNegative.setTextColor(btnTextColor);
                btnPositive.setTextColor(btnTextColor);
                btnNegative.setWidth(btnWidth);
                btnNegative.setHeight(btnHeight);
                btnPositive.setWidth(btnWidth);
                btnPositive.setHeight(btnHeight);
            }
        });
        dlg.show();
    }

    public void showInfoMessage(String title, String message) {
        showInfoMessage(title, message, mContext.getString(R.string.dlg_close_btn_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    public void showInfoMessage(String title, String message, String buttonLabel, DialogInterface.OnClickListener buttonClickListener) {
        AlertDialog dlg = new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(buttonLabel, buttonClickListener)
                .create();
        dlg.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                int btnTextColor = mContext.getResources().getColor(R.color.secondaryLightColor);
                int btnHeight = mContext.getResources().getDimensionPixelSize(R.dimen.alertdialog_button_height);
                int btnWidth  = mContext.getResources().getDimensionPixelSize(R.dimen.alertdialog_button_width);
                Button btnPositive = dlg.getButton(AlertDialog.BUTTON_POSITIVE);
                btnPositive.setTextColor(btnTextColor);
                btnPositive.setWidth(btnWidth);
                btnPositive.setHeight(btnHeight);
            }
        });
        dlg.show();
    }
}
