package com.example.damian.identifyrubber;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 1;
    private int PICK_IMAGE_REQUEST = 20;
    private ImageView imgViewPhoto;

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
        Intent intent = new Intent();

        intent.setType("image/*"); // wybierz zdjęcie ze wszysktkich rozszezeń
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    public void ActionButtonTakePicture ( View view){
//        Intent cameraIntent = new Intent();
//        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        String pictureDirectoryPath = pictureDirectory.getPath();
//
//        Uri data = Uri.parse(pictureDirectoryPath);
//        cameraIntent.setType("image/*"); // wybierz zdjęcie ze wszysktkich rozszezeń
//        cameraIntent.setAction(Intent.ACTION_GET_CONTENT);
//        if (cameraIntent.resolveActivity(getPackageManager()) != null)
//            startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgViewPhoto.setImageBitmap(imageBitmap);
            imgViewPhoto.setEnabled(true);
        }
    }
}
