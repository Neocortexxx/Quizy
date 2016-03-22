package de.android.philipp.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class Helfer {

    public static final String REG_USERNAME = "username";
    public static final String REG_ID = "regId";

    public static Typeface airstrike;

    public static void LadeSchriftarten(Context context)
    {
        airstrike = Typeface.createFromAsset(context.getAssets(), "airstrike.ttf");
    }

    public static void SetSchriftarten(View view)
    {

        //LogWriter.Write(view.getContext(), "Helfer - SetSchriftarten Start", false);
        if (view instanceof ViewGroup)
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                SetSchriftarten(((ViewGroup) view).getChildAt(i));
            }

        if (view instanceof ScrollView)
        {
            for (int i = 0; i < ((ScrollView) view).getChildCount(); i++)
            {
                SetSchriftarten(((ScrollView) view).getChildAt(i));
            }
        }
        else if (view instanceof EditText)
        {
            if (view.getTag() != null && view.getTag().equals("RobotoRegular"))
                ((EditText) view).setTypeface(airstrike);

            if (view.getTag() == null)
                ((EditText) view).setTypeface(airstrike);

        }
        else if (view instanceof TextView)
        {
            if (view.getTag() != null && view.getTag().equals("RobotoRegular"))
                ((TextView) view).setTypeface(airstrike);

            if (view.getTag() == null)
                ((TextView) view).setTypeface(airstrike);
        }
        else if (view instanceof Button)
        {
            if (view.getTag() != null && view.getTag().equals("RobotoRegular"))
                ((Button) view).setTypeface(airstrike);

            if (view.getTag() == null)
                ((Button) view).setTypeface(airstrike);
        }
        //LogWriter.Write(view.getContext(), "Helfer - SetSchriftarten Ende", false);

    }

    public static void UISetting(View decorView){
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public static void LadeDialog(Dialog dialog, int layout, boolean cancelable, int width, int height, Context context)
    {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = width;
        lp.height = height;
        dialog.getWindow().setAttributes(lp);

        dialog.setCancelable(cancelable);
    }

    public static String getUsername(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String username = prefs.getString(REG_USERNAME, "");
        if (username.isEmpty()) {
            Log.i("Helfer", "User not found.");
            return "";
        }
        return username;
    }

    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i("Helfer", "Registration not found.");
            return "";
        }
        return registrationId;
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("RegisterActivity",
                    "I never expected this! Going down, going down!" + e);
            throw new RuntimeException(e);
        }
    }

}
