package com.example.damian.identifyrubber;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
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
    private Button bSelectPicture, bTakePicture, bRun;
    private Bitmap bitmap1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bSelectPicture = (Button) findViewById(R.id.buttonSelectPicture);
        bTakePicture = (Button) findViewById(R.id.buttonTakePicture);
        bRun = (Button) findViewById(R.id.buttonRun);

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

    public void ActionButtonRun(View view){

        GreyScale greyScale = new GreyScale(bitmap1);
        greyScale.Edit();
        bitmap1 = greyScale.getImage();
        imgViewPhoto.setImageBitmap(bitmap1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) || (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)){

            Uri imageUri = data.getData();
            InputStream imputStream;

            try {
                imputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(imputStream);
                bitmap1 = Bitmap.createScaledBitmap(bitmap, 816, 612, false);
                imgViewPhoto.setImageBitmap(bitmap1);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            bRun.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getBoolean("wasImage") == true) {
            bitmap1 = savedInstanceState.getParcelable("image");
            imgViewPhoto.setImageBitmap(bitmap1);
            bRun.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if ( (bitmap1!=null)) {
            outState.putParcelable("image", bitmap1);
        }
        outState.putBoolean("wasImage", (bitmap1!=null));
    }
}
