package com.example.gazprombank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class DocumentType extends AppCompatActivity {

    SharedPreferences sPref;

    final String SAVED_TEXT = "saved_text";

    public String phone;

    String[] cities = {"дополнительный документ", "справка 2 НДФЛ", "копия трудовой", "паспорт", "выписка (с работы)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_type);

        final TextView textView4 = findViewById(R.id.textView46);
        final Button further = findViewById(R.id.Further);

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null) {
            phone = arguments.get("phone").toString();
            textView4.setText("phone: " + phone);
        }



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
                final String documentType = spinner.getSelectedItem().toString(); ;
                sendMessage3(phone, documentType);
            }
        });


    }

    // Переход на новую активность
    public void sendMessage3(String phone, String documentType){
        Intent intent = new Intent(this, DocumentPhote.class);
        intent.putExtra("phone", phone);
        intent.putExtra("documentType", documentType);
//        intent.putExtra("price", price);
        startActivity(intent);
    }
}