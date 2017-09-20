package com.example.raju.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private EditText locationInputForCity, locationInputForCountryCode;
    private Button submitButton;


    //   final String url = "http://api.openweathermap.org/data/2.5/weather?q=Dhaka,bd&appid=6b8e89f2d94be716c906219d5721c6de";
    String maxTemp, minTemp;
    TextView maxTmp, minTmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationInputForCity = (EditText) findViewById(R.id.location_input_for_city);
        locationInputForCountryCode = (EditText) findViewById(R.id.location_input_for_country_code);
        maxTmp = (TextView) findViewById(R.id.maxview);
        minTmp = (TextView) findViewById(R.id.minview);
        submitButton = (Button) findViewById(R.id.submit_btn);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showService();
            }
        });

        //   showService();
    }

    private void showService() {

        String userLocationCity = locationInputForCity.getText().toString().trim();
        String userLocationCountry = locationInputForCountryCode.getText().toString().trim();
       final String url = "http://api.openweathermap.org/data/2.5/weather?q=" + userLocationCity + "," + userLocationCountry + "&appid=6b8e89f2d94be716c906219d5721c6de";

        JSONObject jsonObject = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main = response.getJSONObject("main");
                    maxTemp = main.getString("temp_max");
                    minTemp = main.getString("temp_min");
                    int maxTemperature = ((Integer.parseInt(maxTemp) - 32)*(5/9));
                    int minTemperature = (Integer.parseInt(minTemp) - 32)*(5/9);
                    maxTmp.setText(String.format("%d", maxTemperature));
                    minTmp.setText(String.format("%d", minTemperature ));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Internet Connectivity Error: " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }

        );
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

}

