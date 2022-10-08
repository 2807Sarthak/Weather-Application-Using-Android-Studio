package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView maintemp, feelsliketemp, prsr, humdty, windspeed, location,mintemp,maxtemp,description;
    EditText editText;
    ImageView search,pressure_img,wind_img,humidity_img,icon;
    String cityname,url;

//    final String appid = "21d4336d96353a08f73105b0ee46626f";
//    final String url = "https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        maintemp = findViewById(R.id.maintemp);
        mintemp = findViewById(R.id.mintemp);
        maxtemp = findViewById(R.id.maxtemp);
        description = findViewById(R.id.description);
        feelsliketemp = findViewById(R.id.feelslike);
        prsr = findViewById(R.id.prsr);
        windspeed = findViewById(R.id.wind);
        humdty = findViewById(R.id.humdty);
        location = findViewById(R.id.location);
        editText = findViewById(R.id.editText);
        search = findViewById(R.id.search);
        pressure_img = findViewById(R.id.prsr_img);
        wind_img = findViewById(R.id.wind_img);
        humidity_img = findViewById(R.id.hmdty_img);
        icon = findViewById(R.id.icon);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityname = editText.getText().toString().trim();
                editText.setText("");
                url = "https://api.openweathermap.org/data/2.5/weather?q="+cityname+"&appid=21d4336d96353a08f73105b0ee46626f";

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject object = response.getJSONObject("main");

                            String temperature = object.getString("temp");
                            Double temp = Double.parseDouble(temperature) - 273.15;
                            maintemp.setText(temp.toString().substring(0,2) + "°C");

                            String feelslike = object.getString("feels_like");
                            Double feelstemp = Double.parseDouble(feelslike) - 273.15;
                            feelsliketemp.setText("Feels like:"+feelstemp.toString().substring(0,2) + "°C");

                            String min_temp = object.getString("temp_min");
                            Double minntemp = Double.parseDouble(min_temp) - 273.15;
                            mintemp.setText("Min. Temp. :"+minntemp.toString().substring(0,2) + "°C");

                            String max_temp = object.getString("temp_max");
                            Double maxxtemp = Double.parseDouble(max_temp) - 273.15;
                            maxtemp.setText("Max. Temp. :"+maxxtemp.toString().substring(0,2) + "°C");

                            String Pressure = object.getString("pressure");
                            Double Pressure_val = Double.parseDouble(Pressure);
                            prsr.setText("Pressure : " + Pressure_val+ " hPa");

                            String Humidity1 = object.getString("humidity");
                            int Humidity_val = Integer.parseInt(Humidity1);
                            humdty.setText("Humidity : " + Humidity_val + " %");

                            JSONObject jsonObjectWind = response.getJSONObject("wind");
                            String wind = jsonObjectWind.getString("speed");
                            windspeed.setText("Wind Speed : " +wind + " m/s");

                            JSONArray jsonArray = response.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                            String Description = jsonObjectWeather.getString("description");
                            description.setText(Description);

                            JSONObject jsonObjectsys = response.getJSONObject("sys");
                            String cn = jsonObjectsys.getString("country");
                            location.setText((cityname+" , "+cn).toUpperCase(Locale.ROOT));

                            int id = jsonObjectWeather.getInt("id");
                            if (id>=0 && id <300){
                               icon.setImageResource(R.drawable.thunderstrom);
                            }
                            else if (id>=300 && id<500){
                                icon.setImageResource(R.drawable.rain);
                            }
                            else if (id>=500 && id<600){
                                icon.setImageResource(R.drawable.rain);
                            }
                            else if (id>=600 && id<701){
                                icon.setImageResource(R.drawable.snow);
                            } else if (id>=701 && id<800){
                                icon.setImageResource(R.drawable.haze);
                            } else if (id == 800) {
                                icon.setImageResource(R.drawable.clearsky);
                            }else if (id == 801) {
                                icon.setImageResource(R.drawable.fewclouds);
                            }else if (id == 802) {
                                icon.setImageResource(R.drawable.scatteredcloud);
                            }else if (id == 803) {
                                icon.setImageResource(R.drawable.brokencloud);
                            }else if (id == 804) {
                                icon.setImageResource(R.drawable.brokencloud);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Enter a valid City Name/Country Name and Try Again !!", Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(request);
            }
        });
    }
}



