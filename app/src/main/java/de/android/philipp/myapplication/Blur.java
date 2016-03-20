package de.android.philipp.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LogWriter;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

/**
 * Created by Philipp on 18.03.2016.
 */
public class Blur {
    private final int DEFAULT_RADIUS = 25;
    private final float DEFAULT_MAX_IMAGE_SIZE = 400;

    private Context context;
    private int radius;
    private float maxImageSize;

    public Blur(Context context) {
        //LogWriter.Write(Helfer.getMetter(), "Blur - Blur Start", false);
        this.context = context;
        setRadius(DEFAULT_RADIUS);
        setMaxImageSize(DEFAULT_MAX_IMAGE_SIZE);
        //LogWriter.Write(Helfer.getMetter(), "Blur - Blur Ende", false);
    }

    public Bitmap render(Bitmap bitmap, boolean scaleDown) {
        //LogWriter.Write(Helfer.getMetter(), "Blur - render Start", false);
        RenderScript rs = RenderScript.create(context);

        if(bitmap == null)
            return null;

        if (scaleDown) {
            bitmap = scaleDown(bitmap);
        }

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Allocation inAlloc = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_GRAPHICS_TEXTURE);
        Allocation outAlloc = Allocation.createFromBitmap(rs, output);

        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, inAlloc.getElement()); // Element.U8_4(rs));
        script.setRadius(getRadius());
        script.setInput(inAlloc);
        script.forEach(outAlloc);
        outAlloc.copyTo(output);

        rs.destroy();

        //LogWriter.Write(Helfer.getMetter(), "Blur - render Ende", false);
        return output;
    }

    public Bitmap scaleDown(Bitmap input) {
        //LogWriter.Write(Helfer.getMetter(), "Blur - scaleDown Start", false);
        if(input == null)
        {
           // LogWriter.Write(Helfer.getMetter(), "Blur - scaleDown Ende", false);
            return null;
        }

        float ratio = Math.min( getMaxImageSize() / input.getWidth(), getMaxImageSize() / input.getHeight());
        int width = Math.round( ratio * input.getWidth());
        int height = Math.round( ratio * input.getHeight());

        //LogWriter.Write(Helfer.getMetter(), "Blur - scaleDown Ende", false);
        return Bitmap.createScaledBitmap(input, width, height, true);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public float getMaxImageSize() {
        return maxImageSize;
    }

    public void setMaxImageSize(float maxImageSize) {
        this.maxImageSize = maxImageSize;
    }
}