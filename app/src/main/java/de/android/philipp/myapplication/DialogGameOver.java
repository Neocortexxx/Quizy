package de.android.philipp.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogGameOver extends Dialog {

    Button btnMenu, btnNewGame;
    boolean _new, _menu;

    public DialogGameOver(Context context)
    {
        super(context);
    }

    public void init(Context con)
    {
        Helfer.SetSchriftarten(findViewById(R.id.rootViewGameOver));
        Helfer.UISetting(getWindow().getDecorView());

        btnMenu = (Button) findViewById(R.id.btnDialogGameOverMenu);
        btnNewGame = (Button) findViewById(R.id.btnDialogGameOverNeu);

        btnNewGame.setOnClickListener(clickListener);
        btnMenu.setOnClickListener(clickListener);

        _menu = false;
        _new = false;

    }

    private View.OnClickListener clickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btnDialogGameOverMenu:
                        set_menu(true);
                        dismiss();
                        break;
                    case R.id.btnDialogGameOverNeu:
                        set_new(true);
                        dismiss();
                        break;
                }
            }

    };

    public boolean get_new() {
        return _new;
    }

    public boolean get_menu() {
        return _menu;
    }

    public void set_menu(boolean _menu) {
        this._menu = _menu;
    }

    public void set_new(boolean _new) {
        this._new = _new;
    }

    @Override
    public void onBackPressed() {
    }
}
