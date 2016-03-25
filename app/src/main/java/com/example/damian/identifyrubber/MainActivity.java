package com.example.damian.identifyrubber;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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
    private Uri imageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bSelectPicture, bTakePicture;

        bSelectPicture = (Button) findViewById(R.id.buttonSelectPicture);
        bTakePicture = (Button) findViewById(R.id.buttonTakePicture);

        imgViewPhoto = (ImageView) findViewById(R.id.imgPicture);
    }

    public void ActionButtonSelectPicture (View view){
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*"); // wybierz zdjęcie ze wszysktkich rozszezeń
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }
    public void ActionButtonTakePicture ( View view){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) || (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)){

            imageUri = data.getData();
            InputStream imputStream;

            try {
                imputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(imputStream);
                imgViewPhoto.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean("wasImage") == true) {
            imgViewPhoto.setImageBitmap((Bitmap) savedInstanceState.getParcelable("image"));
            imageUri = savedInstanceState.getParcelable("imageUri");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelable("image", outState.getParcelable("image"));
        InputStream imputStream;
        if ( (imageUri!=null)) {
            // (imgViewPhoto != null) &&
            outState.putParcelable("imageUri", imageUri);
            try {
                imputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(imputStream);
                outState.putParcelable("image", bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        outState.putBoolean("wasImage", (imageUri!=null));
    }
}
