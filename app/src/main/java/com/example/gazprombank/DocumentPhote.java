package com.example.gazprombank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DocumentPhote extends AppCompatActivity {

    ImageView imageView;

    public String phone, documentType;
    public File photoFile1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_phote);

        final Button buttonKamera = findViewById(R.id.foto);
        final Button further = findViewById(R.id.Further);
        final TextView textView6 = findViewById(R.id.textView6);
        imageView = (ImageView) findViewById(R.id.imageView2);

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null) {
            phone = arguments.get("phone").toString(); // сделать хранение всех этих данных внутри устройства (бд)
            documentType = arguments.get("documentType").toString();
        }

        buttonKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        further.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendMessage3(phone, documentType);
                textView6.setText(documentType);
                sendMessage3(phone, documentType, photoFile1);
            }
        });
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                photoFile1 = photoFile;
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");


            Bitmap bm = imageBitmap;
            String path = Environment.getExternalStorageDirectory().toString();

            imageView.setImageBitmap(imageBitmap);

            OutputStream out = null;
            File file = new File(path, "imagename.png");
            try {
                out = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Сохранение фото


    // Переход на новую активность
    public void sendMessage3(String phone, String documentType, File photoFile){
        Intent intent = new Intent(this, DocumentComment.class);
        intent.putExtra("phone", phone);
        intent.putExtra("documentType", documentType);
        intent.putExtra("photoFile", photoFile);
        startActivity(intent);
    }
}