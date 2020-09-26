package com.example.gazprombank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class DocumentComment extends AppCompatActivity {

    ImageView imageView;

    public String phone, documentType;
    public File photoFile1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_comment);

//        final Button further = findViewById(R.id.Further);
        final TextView textView = findViewById(R.id.textView4);
        imageView = (ImageView) findViewById(R.id.imageView);

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null) {
            phone = arguments.get("phone").toString(); // сделать хранение всех этих данных внутри устройства (бд)
            documentType = arguments.get("documentType").toString();
//            photoFile1 = arguments.get("photoFile");
        }
    }
}