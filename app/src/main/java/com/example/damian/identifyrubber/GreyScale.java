package com.example.damian.identifyrubber;

import android.graphics.Bitmap;
import android.graphics.Color;


/**
 * Created by Damian on 2016-03-25.
 */
public class GreyScale {

    Bitmap image=null;
    int width;
    int height;
    Bitmap imageChange ;

    public GreyScale(Bitmap image){
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }

    public void Edit() {
        imageChange = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        int width = 100;
//        int height = 1000;
        int[] pixel = new int[width*height];
        image.getPixels(pixel,0,width,0,0,width,height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                int colour = image.getPixel(i, j);

                int red = Color.red(colour);
                int blue = Color.blue(colour);
                int green = Color.green(colour);
                int alpha = Color.alpha(colour);

                int gr = (red + blue + green) / 3;

                if (gr < 180)
                    gr = 0;
                else if (gr < 255)
                    gr = 255;

                pixel[i*j] = Color.argb(alpha,gr,gr,gr);
            }
        }
        imageChange.setPixels(pixel, 0, width, 0,0,width,height);
    }

    public Bitmap getImage(){
        return imageChange;
    }
}
