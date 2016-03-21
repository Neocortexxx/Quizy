package de.android.philipp.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Philipp on 18.03.2016.
 */
public class GameActivity extends Activity {

    DialogCountdown _dialogCountdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game);

        ImageView view = (ImageView) findViewById(R.id.imageViewGame);
        view.setImageDrawable(getResources().getDrawable(R.drawable.laser));
        Bitmap map = drawableToBitmap(view.getDrawable());
        setBlurImageToImageView(map, view);

        Helfer.SetSchriftarten(findViewById(R.id.rootView));

        if(_dialogCountdown == null)
        {
            _dialogCountdown = new DialogCountdown(this);
            _dialogCountdown.setOnDismissListener(onDismissListener);
            Helfer.LadeDialog(_dialogCountdown, R.layout.dialog_countdown, true, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, this);
            _dialogCountdown.init(GameActivity.this);
        }
        _dialogCountdown.show();

    }

    private Dialog.OnDismissListener onDismissListener = new Dialog.OnDismissListener()
    {
        @Override
        public void onDismiss(DialogInterface dialog) {

        }
    };

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
