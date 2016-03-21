package de.android.philipp.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Philipp on 18.03.2016.
 */
public class HighscoreActivity extends Activity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.highscore);

        ImageView view = (ImageView) findViewById(R.id.imageViewHighscore);
        view.setImageDrawable(getResources().getDrawable(R.drawable.laser));
        Bitmap map = drawableToBitmap(view.getDrawable());
        setBlurImageToImageView(map, view);

        Typeface font = Typeface.createFromAsset(getAssets(), "airstrike.ttf");


        TextView title = (TextView) findViewById(R.id.txtHighscoreTitle);
        title.setTypeface(font);

        new GetHighscoresTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private View.OnClickListener clickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnInfoStart:
                    //ActivityStarten(InfoActivity.class);
                    break;
            }
        }
    };

    private void ActivityStarten(Class klasse)
    {
        Intent i = new Intent(this, klasse);
        startActivity(i);
        this.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
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

    public class GetHighscoresTask extends AsyncTask<String, Void, String> {

        LinearLayout alleEintraege;
        protected void onPreExecute() {
        }

        public GetHighscoresTask() {
            alleEintraege = (LinearLayout)findViewById(R.id.listHighscore);
            alleEintraege.removeAllViews();
        }

        protected String doInBackground(String... werte) {
            try {
                String result = "";

                result = InteractWithServer.GetHighscoresFromServer();

                return result;
            } catch (final Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {

            try {
                if(!result.equals("null\n")) {
                    JSONArray jArray = new JSONArray(result);
                    JSONObject json_data = null;
                    for (int i = 0; i < jArray.length(); i++) {

                        HighscoreItem item = new HighscoreItem(HighscoreActivity.this);

                        json_data = jArray.getJSONObject(i);
                        item.setUserName(json_data.getString("Name"));
                        item.setHighscoreDate(json_data.getString("Datum"));
                        item.setScore(json_data.getInt("Score"));
                        alleEintraege.addView(item);
                    }
                }
            } catch (final Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HighscoreActivity.this, "Serverantwort fehlerhaft!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

    }
}
