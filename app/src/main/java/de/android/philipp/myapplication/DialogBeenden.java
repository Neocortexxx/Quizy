package de.android.philipp.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogBeenden extends Dialog {



    boolean _beenden;

    public DialogBeenden(Context context)
    {
        super(context);
    }

    public void init(Context con)
    {//
        Typeface font = Typeface.createFromAsset(con.getAssets(), "airstrike.ttf");


        TextView text = (TextView) findViewById(R.id.txtDialog);
        Button abbr = (Button) findViewById(R.id.btnDialogAbbrechen);
        Button beenden = (Button) findViewById(R.id.btnDialogBeenden);

        text.setTypeface(font);
        beenden.setTypeface(font);
        abbr.setTypeface(font);

        findViewById(R.id.btnDialogAbbrechen).setOnClickListener(onClickListener);
        findViewById(R.id.btnDialogBeenden).setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(final View v)
        {
            switch (v.getId())
            {
                case R.id.btnDialogBeenden:
                    set_beenden(true);
                    dismiss();
                    break;

                case R.id.btnDialogAbbrechen:
                    set_beenden(false);
                    dismiss();
                    break;
            }
        }
    };

    public boolean is_beenden() {
        return _beenden;
    }

    public void set_beenden(boolean _beenden) {
        this._beenden = _beenden;
    }
}
