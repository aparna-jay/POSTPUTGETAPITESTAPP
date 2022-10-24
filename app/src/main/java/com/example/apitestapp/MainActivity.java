package com.example.apitestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button button, buttonput, btnRequest, buttongetone;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        buttonput= (Button) findViewById(R.id.buttonput);
        btnRequest= (Button) findViewById(R.id.buttonget);
        buttongetone= (Button) findViewById(R.id.buttongetone);

        //********************************************************** POST

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            OkHttpClient client = new OkHttpClient();
                            String BASE_URL = "http://192.168.43.140:8080/api";
                            String json = "{\"customerId\":\"122222222222222222222222\",\"customerName\":\"John\",\"email\":\"john@gmail.com\",\"password\":\"123\",\"vehicleType\":\"Diesel\",\"arrivalTime\":0,\"departureTime\":1}";

                            RequestBody body = RequestBody.create(
                                    MediaType.parse("application/json"), json);

                            Request request = new Request.Builder()
                                    .url(BASE_URL + "/Station/CreateCustomer")
                                    .post(body)
                                    .build();

                            Call call = client.newCall(request);
                            try {
                                Response response = call.execute();
                                Log.e("Response", response.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();

            }

        });

        //********************************************************************************  PUT

        buttonput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            OkHttpClient client = new OkHttpClient();
                            String BASE_URL = "http://192.168.43.140:8080/api";
                            String json = "{\"customerId\":\"122222222222222222222222\",\"customerName\":\"Amali\",\"email\":\"Amali@gmail.com\",\"password\":\"123\",\"vehicleType\":\"Diesel\",\"arrivalTime\":0,\"departureTime\":1}";

                            RequestBody body = RequestBody.create(
                                    MediaType.parse("application/json"), json);

                            Request request = new Request.Builder()
                                    .url(BASE_URL + "/Station/UpdateCustomer/122222222222222222222222")
                                    .put(body)
                                    .build();

                            Call call = client.newCall(request);
                            try {
                                Response response = call.execute();
                                Log.e("Response", response.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();

            }

        });

        //****************************************************  GET
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                sendAndRequestResponse();
            }
        });


        buttongetone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                getOne();
            }
        });
    }



    //****************************************************  GET
    private void sendAndRequestResponse() {
        String URL = "http://192.168.43.140:8080/api/Station/GetAllCustomers";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest objectRequest = new JsonArrayRequest(
                com.android.volley.Request.Method.GET,
                URL,
                null,

                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Response", response.toString());
                        JSONObject ob = null;

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                ob = response.getJSONObject(i);
                                String useremail = ob.getString("email");
                                Log.e("Email", useremail);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Error", error.toString());
                            }
                        }
                        );

                        requestQueue.add(objectRequest);
                    }



    //****************************************************  GET ONE



    private void getOne() {
        String URL = "http://192.168.43.140:8080/api/Station/GetCustomerById/122222222222222222222222";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            try {
                JSONObject object = new JSONObject();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, URL, null,
                        new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response", response.toString());
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
                requestQueue.add(jsonObjectRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
                }