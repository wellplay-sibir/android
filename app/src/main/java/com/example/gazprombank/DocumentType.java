package com.example.gazprombank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DocumentType extends AppCompatActivity {

    SharedPreferences sPref;

    final String SAVED_TEXT = "saved_text";

    public String phone;

    public DBHelper dbHelper;

    public TextView phoneUser, tokenUser;

    String[] cities = {"дополнительный документ", "справка 2 НДФЛ", "копия трудовой", "паспорт", "выписка (с работы)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_type);

        final TextView textView4 = findViewById(R.id.textView46);
        final Button further = findViewById(R.id.Further);

        phoneUser = findViewById(R.id.phoneUser);
        tokenUser = findViewById(R.id.tokenUser);

        dbHelper = new DBHelper(this);

        Bundle arguments = getIntent().getExtras();

//        if(arguments!=null) {
//            phone = arguments.get("phone").toString();
//            textView4.setText("phone: " + phone);
//        }


//        dataRead();


        // spiner
        final Spinner spinner = (Spinner) findViewById(R.id.cities);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);



        further.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String documentType = spinner.getSelectedItem().toString();
                // сохраним тип документа в бд
                dataStorage(documentType);

                sendMessage3();
            }
        });


    }

    // Сохранение тип документа
    void dataStorage(String documentType){
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_TYPE, documentType);

        database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
    }





    // считываем данные из бд  (удалить)
    void dataRead(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
            phoneUser.setText(cursor.getString(nameIndex));
            tokenUser.setText(cursor.getString(emailIndex));
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




    // Переход на новую активность
    public void sendMessage3(){
        Intent intent = new Intent(this, DocumentPhote.class);
        startActivity(intent);
    }
}