package de.android.philipp.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogCountdown extends Dialog {

    TextView timer;

    public DialogCountdown(Context context)
    {
        super(context);
    }

    public void init(Context con)
    {//
        Helfer.SetSchriftarten(findViewById(R.id.rootViewDialogCount));

        timer = (TextView) findViewById(R.id.txtTimer);
        StartTimer();
    }

    private void StartTimer()
    {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                dismiss();
            }
        }.start();
    }

}
