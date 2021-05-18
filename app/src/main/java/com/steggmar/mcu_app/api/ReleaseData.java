package com.steggmar.mcu_app.api;

import android.annotation.SuppressLint;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ReleaseData extends Thread {

    private String days_until;
    private String overview;
    private String poster_url;
    private String release_date;
    private String title;
    private String type;

    private ReleaseData following_production;

    private String date;
    private JSONObject response;

    // Uses current date
    public ReleaseData(){
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.date = df.format(new Date());
        this.start();
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Uses given date
    public ReleaseData(String date){
        this.date = date;
        this.start();
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // for following productions
    private ReleaseData(String days_until, String overview, String poster_url, String release_date, String title, String type) {
        this.days_until = days_until;
        this.overview = overview;
        this.poster_url = poster_url;
        this.release_date = release_date;
        this.title = title;
        this.type = type;
    }

    private JSONObject request() {
        try {
            URL url = new URL("https://www.whenisthenextmcufilm.com/api?date=" + this.date);
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

    private void applyValues() {
        try {
            this.days_until = String.valueOf(this.response.get("days_until"));
            this.overview = this.response.getString("overview");
            if(this.overview.equals("")) this.overview = "No description available...";
            this.poster_url = this.response.getString("poster_url");
            this.release_date = this.response.getString("release_date");
            this.title = this.response.getString("title");
            this.type = this.response.getString("type");

            JSONObject following = (JSONObject) response.get("following_production");

            if(following.length() != 0){
                String days_until = String.valueOf(following.get("days_until"));
                String overview = following.getString("overview");
                String poster_url = following.getString("poster_url");
                String release_date = following.getString("release_date");
                String title = following.getString("title");
                String type = following.getString("type");
                this.following_production = new ReleaseData(days_until, overview, poster_url, release_date, title, type);
            } else {
                this.following_production = null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run(){
        this.response = request();
        this.applyValues();
    }

    // Getter
    public String getDays_until() { return days_until; }
    public String getOverview() { return overview; }
    public String getPoster_url() { return poster_url; }
    public String getRelease_date() { return release_date; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public ReleaseData getFollowing_production() { return following_production; }

}
