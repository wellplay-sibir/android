package com.example.gazprombank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText phoneNumberEditText = findViewById(R.id.editTextPhone);
        final Button loginButton = findViewById(R.id.login);
        final TextView textView = findViewById(R.id.textView);





        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final String phoneNumber = phoneNumberEditText.getText().toString();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://fenomen96.000webhostapp.com/index.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equals("Ok")){
                                sendMessage();
                            }
                            else{
                                // return: значение ошибки
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        response,
                                        Toast.LENGTH_SHORT); // сообщение будет висеть минимум времени.
                                toast.setGravity(Gravity.CENTER, 0, 0); // Тут координаты – мессендж возникнет по центру.
                                toast.show(); // строка вызова.
//                                sendMessage();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public  void onErrorResponse(VolleyError error) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    error.toString(),
                                    Toast.LENGTH_SHORT); // сообщение будет висеть минимум времени.
                            toast.setGravity(Gravity.CENTER, 0, 0); // Тут координаты – мессендж возникнет по центру.
                            toast.show(); // строка вызова.
                        }
                    }){
                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            final String phone = map.put("jjj", phoneNumberEditText.getText().toString());
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);

            }
        });
    }







//    public void gggg(final TextView textView){
//        String url = "https://fenomen96.000webhostapp.com/index.php";
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        textView.setText("Response: " + response.toString());
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO: Handle error
//                        textView.setText("Response: " + error.toString());
//                    }
//                });
//        Log.i("ggggggg", jsonObjectRequest.toString());
//
//// Access the RequestQueue through your singleton class.
//        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
//                getRequestQueue();
//        Log.i("uuuuuu", queue.toString());
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
//
//    }


    // Переход на новую активность
    public void sendMessage() {
        Intent intent = new Intent(this, LoginCode.class);
        EditText phoneNumberEditText = (EditText) findViewById(R.id.editTextPhone);
        String phoneUser = phoneNumberEditText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, phoneUser);
        startActivity(intent);
    }
}