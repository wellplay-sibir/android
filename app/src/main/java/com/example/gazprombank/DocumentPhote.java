package com.example.gazprombank;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;


public class DocumentPhote extends AppCompatActivity {

    ImageView imageView;

    public String phone, documentType;
    public File photoFile1;

    public DBHelper dbHelper;

    public TextView typehhh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_phote);

        final Button buttonKamera = findViewById(R.id.foto);
        final Button further = findViewById(R.id.Further);
        final TextView textView6 = findViewById(R.id.textView6);
//        imageView = (ImageView) findViewById(R.id.imageView2);



        typehhh = findViewById(R.id.textView5);

        dbHelper = new DBHelper(this);

        Bundle arguments = getIntent().getExtras();





//        if(arguments!=null) {
//            phone = arguments.get("phone").toString(); // сделать хранение всех этих данных внутри устройства (бд)
//            documentType = arguments.get("documentType").toString();
//        }

        dataRead();

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
//                textView6.setText(documentType);
                sendMessage3();
            }
        });
    }

    // считываем данные из бд  (удалить)
    void dataRead(){

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
            int documentType = cursor.getColumnIndex(DBHelper.KEY_TYPE);
//            phoneUser.setText(cursor.getString(nameIndex));
//            tokenUser.setText(cursor.getString(emailIndex));
            typehhh.setText(cursor.getString(nameIndex));
        } else {
//            Log.d("mLog","0 rows");
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Error: bd = Null",
                    Toast.LENGTH_SHORT); // сообщение будет висеть минимум времени.
            toast.setGravity(Gravity.CENTER, 0, 0); // Тут координаты – мессендж возникнет по центру.
            toast.show(); // строка вызова.
        }
        cursor.close();
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
                dataStorage(photoFile);

//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                photoFile1 = photoFile;
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    void dataStorage(File photoFile){
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_FILE, photoFile.toString());


        database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
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
    public void sendMessage3(){
        Intent intent = new Intent(this, DocumentComment.class);
        startActivity(intent);
    }
}