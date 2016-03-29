package de.android.philipp.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * Created by Philipp on 18.03.2016.
 */
public class GameActivity extends Activity {

    DialogCountdown _dialogCountdown;
    TextView timeLeft, timeLeftText, frageTextView;
    Button  ant1, ant2, ant3, ant4;
    int aktuelleKategorie, korrekteAnt;

    List<String> alleIDs;
    List<String> checkedIDs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game);

        Helfer.UISetting(getWindow().getDecorView());
        Helfer.SetSchriftarten(findViewById(R.id.rootView));

        ImageView view = (ImageView) findViewById(R.id.imageViewGame);
        view.setImageDrawable(getResources().getDrawable(R.drawable.laser));
        Bitmap map = drawableToBitmap(view.getDrawable());
        setBlurImageToImageView(map, view);

        TextView pause = (TextView) findViewById(R.id.txtPause);

        pause.setOnClickListener(clickListener);

        if(_dialogCountdown == null)
        {
            _dialogCountdown = new DialogCountdown(this);
            _dialogCountdown.setOnDismissListener(onDismissListener);
            Helfer.LadeDialog(_dialogCountdown, R.layout.dialog_countdown, true, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, this);
            _dialogCountdown.init(GameActivity.this);
        }
        _dialogCountdown.show();

        timeLeft = (TextView) findViewById(R.id.txtTimeLeft);
        timeLeftText = (TextView) findViewById(R.id.txtTimeLeftText);
        frageTextView = (TextView) findViewById(R.id.txtFrageGame);

        ant1 = (Button) findViewById(R.id.btnGameButton1);
        ant2 = (Button) findViewById(R.id.btnGameButton2);
        ant3 = (Button) findViewById(R.id.btnGameButton3);
        ant4 = (Button) findViewById(R.id.btnGameButton4);

    }

    private void StartTimer()
    {
        new CountDownTimer(61000, 1000) {

            public void onTick(long millisUntilFinished) {

                if((millisUntilFinished / 1000) < 10)
                {
                    timeLeft.setTextColor(Color.RED);
                    timeLeft.setTextSize(1, 30);
                    timeLeftText.setTextColor(Color.RED);
                    timeLeftText.setTextSize(1, 30);
                }
                timeLeft.setText(String.valueOf(millisUntilFinished / 1000) + " ");
            }

            public void onFinish() {
                ResetUI();
            }
        }.start();
    }

    private void ResetUI(){
        timeLeft.setTextColor(Color.WHITE);
        timeLeftText.setTextColor(Color.WHITE);
        timeLeft.setTextSize(20);
        timeLeftText.setTextSize(20);
    }

    @Override
    public void onBackPressed() {
    }

    private Dialog.OnDismissListener onDismissListener = new Dialog.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog) {
            aktuelleKategorie = 1;
            checkedIDs = new ArrayList<>();
            new GetQuestionTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            StartTimer();
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txtPause:
                    Toast.makeText(getApplicationContext(), "Pause", Toast.LENGTH_SHORT);
                    break;
            }
        }
    };

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

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Helfer.UISetting(getWindow().getDecorView());
    }

    public class GetQuestionTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        public GetQuestionTask() {
        }

        protected String doInBackground(String... werte) {
            try {
                String result = "";

                result = InteractWithServer.GetFrageFromServer((String.valueOf(aktuelleKategorie)), checkedIDs);

                return result;
            } catch (final Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {

            Random r = new Random();

            try {
                if(!result.equals("null\n")) {

                    JSONArray jArray = new JSONArray(result);
                    JSONObject json_data = null;
                    for (int i = 0; i < jArray.length(); i++) {

                        json_data = jArray.getJSONObject(i);

                        korrekteAnt = r.nextInt(3) + 1;

                        checkedIDs.add(String.valueOf(json_data.getInt("FrageID")));
                        frageTextView.setText(json_data.getString("Fragetext"));

                        switch (korrekteAnt){

                            case 1:
                                ant1.setText(json_data.getString("Korrekt"));
                                ant2.setText(json_data.getString("Falsch_1"));
                                ant3.setText(json_data.getString("Falsch_2"));
                                ant4.setText(json_data.getString("Falsch_3"));
                                break;

                            case 2:
                                ant1.setText(json_data.getString("Falsch_1"));
                                ant2.setText(json_data.getString("Korrekt"));
                                ant3.setText(json_data.getString("Falsch_2"));
                                ant4.setText(json_data.getString("Falsch_3"));
                                break;

                            case 3:
                                ant1.setText(json_data.getString("Falsch_2"));
                                ant2.setText(json_data.getString("Falsch_1"));
                                ant3.setText(json_data.getString("Korrekt"));
                                ant4.setText(json_data.getString("Falsch_3"));
                                break;

                            case 4:
                                ant1.setText(json_data.getString("Falsch_3"));
                                ant2.setText(json_data.getString("Falsch_1"));
                                ant3.setText(json_data.getString("Falsch_2"));
                                ant4.setText(json_data.getString("Korrekt"));
                                break;
                        }
                    }
                }
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GameActivity.this, "Serverantwort fehlerhaft!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

    }
}
