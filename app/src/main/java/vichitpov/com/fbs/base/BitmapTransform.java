package vichitpov.com.fbs.base;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * Created by Vichit on 3/19/18.
 */


public class BitmapTransform implements Transformation {

    public static final int MAX_WIDTH = 1080;
    public static final int MAX_HEIGHT = 720;

    public static int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

    private final int maxWidth;
    private final int maxHeight;

    public BitmapTransform(int maxWidth, int maxHeight) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int targetWidth, targetHeight;
        double aspectRatio;

        if (source.getWidth() > source.getHeight()) {
            targetWidth = maxWidth;
            aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            targetHeight = (int) (targetWidth * aspectRatio);
        } else {
            targetHeight = maxHeight;
            aspectRatio = (double) source.getWidth() / (double) source.getHeight();
            targetWidth = (int) (targetHeight * aspectRatio);
        }

        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
        if (result != source) {
            source.recycle();
        }
        return result;
    }

    @Override
    public String key() {
        return maxWidth + "x" + maxHeight;
    }

}