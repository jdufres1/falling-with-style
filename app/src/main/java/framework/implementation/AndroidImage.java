package framework.implementation;

import android.graphics.Bitmap;

import framework.Image;
import framework.Graphics.ImageFormat;

public class AndroidImage implements Image {
    public Bitmap bitmap;
    ImageFormat format;
   
    public AndroidImage(Bitmap bitmap, ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }
    
    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public ImageFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }      
    
    public void setBitmap(Bitmap bitmap)
    {
    	this.bitmap = bitmap;
    }
}
