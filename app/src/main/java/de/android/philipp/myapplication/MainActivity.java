package de.android.philipp.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Philipp on 18.03.2016.
 */
public class MainActivity extends Activity {


    Button btn1;
    Button btn2;
    Button btn3;
    TextView title;

    DialogBeenden _dialogBeenden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        ImageView view = (ImageView) findViewById(R.id.imageView);
        view.setImageDrawable(getResources().getDrawable(R.drawable.laser));
        Bitmap map = drawableToBitmap(view.getDrawable());
        setBlurImageToImageView(map, view);

        Typeface font = Typeface.createFromAsset(getAssets(), "airstrike.ttf");


        title = (TextView) findViewById(R.id.txtTitle);
        btn1 = (Button) findViewById(R.id.btnStart);
        btn2 = (Button) findViewById(R.id.btnHighscore);
        btn3 = (Button) findViewById(R.id.btnExit);

        btn1.setOnClickListener(clickListener);
        btn2.setOnClickListener(clickListener);
        btn3.setOnClickListener(clickListener);

        title.setTypeface(font);
        btn1.setTypeface(font);
        btn2.setTypeface(font);
        btn3.setTypeface(font);
    }

    private View.OnClickListener clickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnStart:
                    ActivityStarten(InfoActivity.class);
                    break;

                case R.id.btnHighscore:
                    ActivityStarten(HighscoreActivity.class);
                    break;

                case R.id.btnExit:
                    onBackPressed();
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        if(_dialogBeenden == null)
        {
            _dialogBeenden = new DialogBeenden(this);
            _dialogBeenden.setOnDismissListener(onDismissListener);
            Helfer.LadeDialog(_dialogBeenden, R.layout.dialog_beenden, true, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, this);
            _dialogBeenden.init(MainActivity.this);
        }
        _dialogBeenden.show();
    }

    private Dialog.OnDismissListener onDismissListener = new Dialog.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog) {
            if(_dialogBeenden.is_beenden())
                finish();
        }
    };

    private void ActivityStarten(Class klasse)
    {
        Intent i = new Intent(this, klasse);
        startActivity(i);
        //this.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    public static void setBlurImageToImageView(Bitmap bitmap, ImageView imageView)
    {

        //LogWriter.Write(imageView.getContext(), "Helfer - setBlurImageToImageView Start", false);
        Blur gaussian = new Blur(imageView.getContext());
        gaussian.setMaxImageSize(100);
        gaussian.setRadius(3);

        final Bitmap output = gaussian.render(bitmap, true);


        imageView.setImageBitmap(output);
        //LogWriter.Write(imageView.getContext(), "Helfer - setBlurImageToImageView Ende", false);
    }

    public static Bitmap drawableToBitmap(Drawable drawable)
    {
        //LogWriter.Write(Helfer.getMetter(), "Helfer - drawableToBitmap Start", false);
        if (drawable instanceof BitmapDrawable)
        {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        //LogWriter.Write(Helfer.getMetter(), "Helfer - drawableToBitmap Ende", false);
        return bitmap;
    }
}
