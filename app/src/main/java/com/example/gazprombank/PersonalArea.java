package com.example.gazprombank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalArea extends AppCompatActivity {

    public static final String EXTRA_MESSAGE2 = "";

    EditText etText;
    Button btnSave, btnLoad;

    SharedPreferences sPref;

    final String SAVED_TEXT = "saved_text";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final String phoneUser = intent.getStringExtra(LoginCode.EXTRA_MESSAGE2);

        saveText(phoneUser);

        // Capture the layout's TextView and set the string as its text

//        textView.setText(phoneUser);
        final Button loadingDocuments = findViewById(R.id.loadingDocuments);

//        loadText();

        loadingDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage3(phoneUser);
            }
        });

    }

    // Сохранение номера телефона клиента
    void saveText(String phoneUser) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, phoneUser);
        ed.commit();
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    // Извлечение номера телефона клиента
    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        final TextView textView = findViewById(R.id.textView3);
        textView.setText(savedText);
        Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }

    // Переход на новую активность
    public void sendMessage2() {
        Intent intent1 = new Intent(this, DocumentType.class);
//        EditText phoneNumberEditText = (EditText) findViewById(R.id.codeEditText);
//        String phoneUser = phoneNumberEditText.getText().toString();
//        intent1.putExtra(EXTRA_MESSAGE2, phoneUser);
        startActivity(intent1);
    }

    // Переход на новую активность
    public void sendMessage3(String phone){
        Intent intent = new Intent(this, DocumentType.class);
        intent.putExtra("phone", phone);
//        intent.putExtra("company", company);
//        intent.putExtra("price", price);
        startActivity(intent);
    }
}