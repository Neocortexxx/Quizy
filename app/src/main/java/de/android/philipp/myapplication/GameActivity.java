package de.android.philipp.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Philipp on 18.03.2016.
 */
public class GameActivity extends Activity {

    TextView frageTextView, scoreText;
    Button  ant1, ant2, ant3, ant4, btnKorrekt;
    ImageView life1, life2, life3, life4, life5;
    CountDownTimer timer;
    Drawable bckTrue, bckFalse, bckNormal;
    ProgressBar bar;
    boolean timerRunning, auswertung;
    int aktuelleKategorie, korrekteAnt, progress, leben, score;
    DialogGameOver _gameOver;

    List<String> alleIDs;
    List<String> checkedIDs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game);

        initUI();

        aktuelleKategorie = 1;
        leben = 5;
        score = 0;
        checkedIDs = new ArrayList<>();
        new GetQuestionTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        StartTimer();
    }

    private void StartTimer()
    {
        if (timer != null && timerRunning)
            timer.cancel();
        progress = 60;
        TextView aktKat = (TextView) findViewById(R.id.txtAktuelleKat);
        aktKat.setText("Aktuelle Kategorie: " + String.valueOf(aktuelleKategorie) + " ");
        timer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerRunning = true;
                progress--;
                //progressText.setText(String.valueOf(progress) + " ");
                bar.setProgress(progress);
            }

            public void onFinish() {
                timerRunning = false;
                if(aktuelleKategorie < 5){
                    NextKategory();
                }
                else{
                    Toast.makeText(GameActivity.this, "Ende", Toast.LENGTH_SHORT).show();
                    aktuelleKategorie = 1;
                }
            }
        }.start();
    }

    private void ResetUI(){
        scoreText.setText("Score: " + String.valueOf(score) + " ");

        life1.setImageResource(R.drawable.herz_full_30);
        life2.setImageResource(R.drawable.herz_full_30);
        life3.setImageResource(R.drawable.herz_full_30);
        life4.setImageResource(R.drawable.herz_full_30);
        life5.setImageResource(R.drawable.herz_full_30);

        ResetButtons();
    }

    private void ResetButtons(){
        ant1.setBackground(bckNormal);
        ant2.setBackground(bckNormal);
        ant3.setBackground(bckNormal);
        ant4.setBackground(bckNormal);
    }

    private void initUI(){
        Helfer.UISetting(getWindow().getDecorView());
        Helfer.SetSchriftarten(findViewById(R.id.rootView));

        bckTrue = getResources().getDrawable(R.drawable.button_menu_true);
        bckFalse = getResources().getDrawable(R.drawable.button_menu_false);
        bckNormal = getResources().getDrawable(R.drawable.button_menu);

        ImageView view = (ImageView) findViewById(R.id.imageViewGame);
        view.setImageDrawable(getResources().getDrawable(R.drawable.laser));
        Bitmap map = drawableToBitmap(view.getDrawable());
        setBlurImageToImageView(map, view);

        //Button pause = (Button) findViewById(R.id.btnPause);
        bar = (ProgressBar) findViewById(R.id.progressBar);
        //progressText = (TextView) findViewById(R.id.txtProgress);
        //pause.setOnClickListener(clickListener);

        scoreText = (TextView) findViewById(R.id.txtScore);
        frageTextView = (TextView) findViewById(R.id.txtFrageGame);

        life1 = (ImageView) findViewById(R.id.life1);
        life2 = (ImageView) findViewById(R.id.life2);
        life3 = (ImageView) findViewById(R.id.life3);
        life4 = (ImageView) findViewById(R.id.life4);
        life5 = (ImageView) findViewById(R.id.life5);

        ant1 = (Button) findViewById(R.id.btnGameButton1);
        ant2 = (Button) findViewById(R.id.btnGameButton2);
        ant3 = (Button) findViewById(R.id.btnGameButton3);
        ant4 = (Button) findViewById(R.id.btnGameButton4);

        ant1.setOnClickListener(checkAntwortListener);
        ant2.setOnClickListener(checkAntwortListener);
        ant3.setOnClickListener(checkAntwortListener);
        ant4.setOnClickListener(checkAntwortListener);

        ResetUI();
    }

    private int calculateLifes(){

        if(leben > 0)
            leben--;

        switch (leben){
            case 4:
                //life1.setBackground(getResources().getDrawable(R.drawable.herz_leer));
                life1.setImageResource(R.drawable.herz_leer_30);
                break;

            case 3:
                life2.setImageResource(R.drawable.herz_leer_30);
                break;

            case 2:
                life3.setImageResource(R.drawable.herz_leer_30);
                break;

            case 1:
                life4.setImageResource(R.drawable.herz_leer_30);
                break;

            case 0:
                if(aktuelleKategorie < 5){
                    life5.setImageResource(R.drawable.herz_leer_30);
                    NextKategory();
                }
                else{
                    life5.setImageResource(R.drawable.herz_leer_30);
                    ShowGameOver();
                }
                break;
        }
        return leben;
    }

    private View.OnClickListener checkAntwortListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            if(!auswertung) {
                switch (v.getId()) {
                    case R.id.btnGameButton1:
                        if (korrekteAnt == 1) {
                            btnKorrekt.setBackground(bckTrue);
                            NextKategory();
                        } else {
                            v.setBackground(bckFalse);
                            btnKorrekt.setBackground(bckTrue);
                            NextQuestion();
                        }
                        break;
                    case R.id.btnGameButton2:
                        if (korrekteAnt == 2) {
                            btnKorrekt.setBackground(bckTrue);
                            NextKategory();
                        } else {
                            v.setBackground(bckFalse);
                            btnKorrekt.setBackground(bckTrue);
                            NextQuestion();
                        }
                        break;
                    case R.id.btnGameButton3:
                        if (korrekteAnt == 3) {
                            btnKorrekt.setBackground(bckTrue);
                            NextKategory();
                        } else {
                            v.setBackground(bckFalse);
                            btnKorrekt.setBackground(bckTrue);
                            NextQuestion();
                        }
                        break;
                    case R.id.btnGameButton4:
                        if (korrekteAnt == 4) {
                            btnKorrekt.setBackground(bckTrue);
                            NextKategory();
                        } else {
                            v.setBackground(bckFalse);
                            btnKorrekt.setBackground(bckTrue);
                            NextQuestion();
                        }
                        break;
                }
            }
        }
    };

    private int CalcScore(){
        int tempScore;

        if(leben > 0)
            tempScore = 1000 - (200 * (5 - leben)) + 13 * progress;
        else{
            tempScore = 0;
        }

        return score + tempScore;
    }

    private void  ShowGameOver(){
        _gameOver = new DialogGameOver(GameActivity.this);
        _gameOver.setOnDismissListener(onDismissListener);
        Helfer.LadeDialog(_gameOver, R.layout.dialog_gameover, true, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, this);
        _gameOver.init(GameActivity.this);
    }

    private Dialog.OnDismissListener onDismissListener = new Dialog.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog) {
            if(_gameOver.get_menu())
                Helfer.ActivityStarten(GameActivity.this, MainActivity.class);
            else if(_gameOver.get_new()){
                Helfer.ActivityStarten(GameActivity.this, GameActivity.class);
            }
            finish();
        }
    };

    private void NextKategory(){
        auswertung = true;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                score = CalcScore();
                leben = 5;

                if(aktuelleKategorie < 5){
                    aktuelleKategorie++;
                    ResetUI();
                    new GetQuestionTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    StartTimer();
                }
                else{
                    //TODO GameOver Dialog + Highscore eintragen
                    timer.cancel();
                    Toast.makeText(GameActivity.this, "Quiz geschafft!", Toast.LENGTH_SHORT).show();
                }

                auswertung = false;
            }
        }, 1000);
    }

    private void NextQuestion(){
        auswertung = true;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ResetButtons();
                if (calculateLifes() != 0)
                    new GetQuestionTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else if (aktuelleKategorie < 5)
                    NextKategory();
                else {
                    //TODO Aufruf GameOver
                    Toast.makeText(GameActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
                }
                auswertung = false;
            }
        }, 1000);
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

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Helfer.UISetting(getWindow().getDecorView());
    }

    @Override
    public void onBackPressed() {
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
                                btnKorrekt = (Button) findViewById(R.id.btnGameButton1);
                                ant1.setText(json_data.getString("Korrekt"));
                                ant2.setText(json_data.getString("Falsch_1"));
                                ant3.setText(json_data.getString("Falsch_2"));
                                ant4.setText(json_data.getString("Falsch_3"));
                                break;

                            case 2:
                                btnKorrekt = (Button) findViewById(R.id.btnGameButton2);
                                ant1.setText(json_data.getString("Falsch_1"));
                                ant2.setText(json_data.getString("Korrekt"));
                                ant3.setText(json_data.getString("Falsch_2"));
                                ant4.setText(json_data.getString("Falsch_3"));
                                break;

                            case 3:
                                btnKorrekt = (Button) findViewById(R.id.btnGameButton3);
                                ant1.setText(json_data.getString("Falsch_2"));
                                ant2.setText(json_data.getString("Falsch_1"));
                                ant3.setText(json_data.getString("Korrekt"));
                                ant4.setText(json_data.getString("Falsch_3"));
                                break;

                            case 4:
                                btnKorrekt = (Button) findViewById(R.id.btnGameButton4);
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
