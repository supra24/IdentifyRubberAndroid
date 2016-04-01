package com.example.damian.identifyrubber;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

public class EditPhoto {

    Bitmap image=null, imageChange=null;
    int width;
    int height;
    ArrayList<IntegerPoint> p ;


    public EditPhoto(Bitmap image){
        this.image = image;
        imageChange = image;
        width = image.getWidth(); // szerokość
        height = image.getHeight(); // wysokość
    }

    private int getColor(int x, int y){
        int colour = image.getPixel(x, y);

        int red = Color.red(colour);
        int blue = Color.blue(colour);
        int green = Color.green(colour);

        return  (red + blue + green) / 3;
    }

    public void GreyScale() {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

               int gr = getColor(i,j);

                if (gr < 180)
                    gr = 0;
                else if (gr >= 180)
                    gr = 0xFFFFFFFF;


                image.setPixel(i, j, gr);

            }
        }
        imageChange = image;
    }

    public void AnalysisPhoto () {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (getColor(x,y) != 0){

                    int XInception = x;
                    int YInception = y;
                    p = new ArrayList<>();

                    for (int c=0;; c++){ // pętla zewnętrzna, sprawdzanie niżej
                        int right=0;
                        int left=0;

                        for (right=0;; right++){ // pętla do sprawdzania po prawej stonie
                            if ((XInception+right < width) && (YInception+c < height)){
                                if (getColor(XInception+right, YInception+c) !=0){
                                    p.add(new IntegerPoint(XInception+right,YInception+c));
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                        for (left=0;; left++){ // pętla do sprawdzania po lewej stonie
                            if ((XInception-left >0) && (YInception+c < height)) {
                                if (getColor(XInception - left, YInception+c) != 0) {
                                    p.add(new IntegerPoint(XInception + -left, YInception+c));
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }

                        boolean edit= true;

                        if (YInception+1 <height){
                            if (getColor(XInception, YInception+1) != 0){
                                YInception = YInception+1;
                                edit =false;
                            }
                        }

                        if (edit == true) {
                            for (int Xaxis = 0; Xaxis < right; Xaxis++) { // pętla do sprawdzania czy niżej jest jeszcze kawałek gumy po prawej stronie
                                if (YInception + 1 < height) {
                                    if (getColor(XInception + Xaxis, YInception + 1) != 0) {
                                        XInception = XInception+Xaxis;
                                        YInception = YInception+1;
                                        edit = false;
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }

                        if (edit == true) {
                            for (int Xaxis=0; Xaxis < left  ; Xaxis++ ) { // pętla do sprawdzania czy niżej jest jeszcze kawałęk gumy po lewej stronie
                                if (YInception+1 < height){
                                    if (getColor(XInception-Xaxis, YInception+1) !=0 ){
                                        XInception = XInception+Xaxis;
                                        YInception = YInception+1;
                                        edit = false;
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }

                        if (edit == true){
                            break;
                        }

                    }

                   // edycja zdjecia po analizie
                    if (p.size() <=15){
                        for (int i=0; i<p.size(); i++){
                            image.setPixel(p.get(i).getX(), p.get(i).getY(), 0);
                            imageChange.setPixel(p.get(i).getX(), p.get(i).getY(), 0);
                        }
                    } else {
                        for (int i=0; i<p.size(); i++){
                            image.setPixel(p.get(i).getX(), p.get(i).getY(), 0xFFFFFFFF);
                            imageChange.setPixel(p.get(i).getX(), p.get(i).getY(), 0xFFFFFFFF);
                        }
                    }

                }
            }
        }
    }

    public Bitmap getImage(){
        return imageChange;
    }
}
