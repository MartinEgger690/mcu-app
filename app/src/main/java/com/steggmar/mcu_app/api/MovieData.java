package com.steggmar.mcu_app.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieData {

    private String id;
    private String title;
    private String release_date;
    private String box_office;
    private String overview;
    private String cover_url;
    private String trailer_url;
    private String directed_by;
    private String phase;
    private String saga;
    private String chronology;
    private String post_credit_scenes;

    private JSONObject response;

    public static final int ID = 0;

    private JSONObject requestById(String id) {
        try {
            URL url = new URL("https://mcuapi.herokuapp.com/api/v1/movies/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer responseContent = new StringBuffer();

            while ((line = br.readLine()) != null) {
                responseContent.append(line);
            }
            br.close();
            connection.disconnect();

            return new JSONObject(String.valueOf(responseContent));
        }
        catch (IOException | JSONException e) {
            return new JSONObject();
        }
    }
    
}
