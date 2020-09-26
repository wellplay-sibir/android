package com.example.gazprombank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

    public String phone, token;
    public String tokenUser;
    public DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);

        Bundle arguments = getIntent().getExtras();
//        final TextView textView = findViewById(R.id.textView3);
//        final TextView test = findViewById(R.id.textView7);

        if(arguments!=null) {
            phone = arguments.get("phone").toString();
            token = arguments.get("token").toString();
            tokenUser = token.substring(14, 356);
//            textView.setText(tokenUser);
//            test.setText(token);
        }

        dataStorage(phone, tokenUser);

        final Button loadingDocuments = findViewById(R.id.loadingDocuments);


//        loadText();

        loadingDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage3();
            }
        });

    }
    // Сохранение номера и токен
    void dataStorage(String phone, String tokenUser){
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_NAME, phone);
        contentValues.put(DBHelper.KEY_MAIL, tokenUser);

        database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
    }

    // Сохранение номера телефона клиента


    // Переход на новую активность
    public void sendMessage3(){
        Intent intent = new Intent(this, DocumentType.class);
//        intent.putExtra("phone", phone);
//        intent.putExtra("company", company);
//        intent.putExtra("price", price);
        startActivity(intent);
    }
}