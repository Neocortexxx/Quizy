package de.android.philipp.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Philipp on 14.12.2015.
 */
public class HighscoreItem extends RelativeLayout {

    public HighscoreItem(Context context)
    {
        super(context);
        init(context);
    }

    public HighscoreItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public HighscoreItem(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context con)
    {
        inflate(getContext(), R.layout.highscore_item, this);
        Typeface font = Typeface.createFromAsset(con.getAssets(), "airstrike.ttf");

        TextView title = (TextView) findViewById(R.id.txtHighscoreUser);
        TextView user = (TextView) findViewById(R.id.txtHighscoreDate);
        TextView score = (TextView) findViewById(R.id.txtScore);

        score.setTypeface(font);
        user.setTypeface(font);
        title.setTypeface(font);
    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDate;

    }

    public void setUserName(String text)
    {
        ((TextView) findViewById(R.id.txtHighscoreUser)).setText(text);
    }

    public void setHighscoreDate(String text)
    {
        ((TextView) findViewById(R.id.txtHighscoreDate)).setText(formateDateFromstring("yyyy-MM-dd", "dd MMM yyyy", text));
    }

    public void setScore(int score)
    {
        ((TextView) findViewById(R.id.txtScore)).setText(String.valueOf(score));
    }
}