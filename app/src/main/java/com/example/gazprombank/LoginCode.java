package com.example.gazprombank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.example.gazprombank.LoginActivity.EXTRA_MESSAGE;

public class LoginCode extends AppCompatActivity {

    public static final String EXTRA_MESSAGE2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_code);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final String phoneUser = intent.getStringExtra(EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        final TextView textView = findViewById(R.id.textView2);
        textView.setText(phoneUser);
        final EditText codeEditText = findViewById(R.id.codeEditText);
        final Button confirmButton = findViewById(R.id.confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNumber = "{\r\n    \"number_phone\": \"" + phoneUser +"\",\r\n    \"password\": \"" + codeEditText.getText().toString() + "\"\r\n}";
                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://fenomen96.000webhostapp.com/index.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String serverResponse) {

                        if (serverResponse.contains("UserToken")){
                            final String token = serverResponse;
                            sendMessage2(phoneUser, token);
                        }
                        else{
                            // return: значение ошибки
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    serverResponse,
                                    Toast.LENGTH_SHORT); // сообщение будет висеть минимум времени.
                            toast.setGravity(Gravity.CENTER, 0, 0); // Тут координаты – мессендж возникнет по центру.
                            toast.show(); // строка вызова.
//                            sendMessage2(phoneUser);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public  void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        final String phone = map.put("proverka", phoneNumber);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    // Переход на новую активность
    public void sendMessage2(String phone, String token){
        Intent intent = new Intent(this, PersonalArea.class);
        intent.putExtra("phone", phone);
        intent.putExtra("token", token);
//        intent.putExtra("price", price);
        startActivity(intent);
        finish();
    }

}