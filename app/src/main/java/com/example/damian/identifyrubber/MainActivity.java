package com.example.damian.identifyrubber;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends Activity {

    public static final int CAMERA_REQUEST = 10;
    private int PICK_IMAGE_REQUEST = 1;
    private ImageView imgViewPhoto=null;
    private Button bSelectPicture, bTakePicture, bRun, bRun2;
    private Bitmap bitmap1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // przypisanie id
        bSelectPicture = (Button) findViewById(R.id.buttonSelectPicture);
        bTakePicture = (Button) findViewById(R.id.buttonTakePicture);
        bRun = (Button) findViewById(R.id.buttonRun);
        bRun2 = (Button) findViewById(R.id.toggleButton);

        imgViewPhoto = (ImageView) findViewById(R.id.imgPicture);
    }

    public void ActionButtonSelectPicture (View view){  // akcja dla otworzenia galeri
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*"); // wybierz zdjęcie ze wszysktkich rozszezeń
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void ActionButtonTakePicture ( View view){ // akcja dla zrobienia zdjęcia
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void ActionButtonRun(View view){ // akcja dla przycisku RUN

        EditPhoto editPhoto = new EditPhoto(bitmap1);
        editPhoto.GreyScale();
        bitmap1 = editPhoto.getImage();
        imgViewPhoto.setImageBitmap(bitmap1);
//        editPhoto.AnalysisPhoto();
//        bitmap1 = editPhoto.getImage();
//        imgViewPhoto.setImageBitmap(bitmap1);
    }

    public void run2 (View view){
        EditPhoto editPhoto = new EditPhoto(bitmap1);
        editPhoto.AnalysisPhoto();
        bitmap1 = editPhoto.getImage();
        imgViewPhoto.setImageBitmap(bitmap1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) || (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)){

            Uri imageUri = data.getData(); // zapisanie adresu Uri zdjęcia
            InputStream imputStream;

            try {
                imputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(imputStream);  // konwersja adresu na bitmap
                bitmap1 = Bitmap.createScaledBitmap(bitmap, 1024, 768, false); // zmniejszenie zdjęcia
                imgViewPhoto.setImageBitmap(bitmap1);  // dodanie zdjęcia do imageView

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bRun.setVisibility(View.VISIBLE); // widoczność dla przycisku RUN
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {  // metoda wywoływana po obruceniu telefonem i otworzeniu nowego activity
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getBoolean("wasImage") == true) {
            bitmap1 = savedInstanceState.getParcelable("image");
            imgViewPhoto.setImageBitmap(bitmap1);
            bRun.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {  // metoda wywoływana po obrocie telefonem i przed zamknięciem starego activity
        super.onSaveInstanceState(outState);

        if ( (bitmap1!=null)) {
            outState.putParcelable("image", bitmap1);
        }
        outState.putBoolean("wasImage", (bitmap1!=null));
    }
}
