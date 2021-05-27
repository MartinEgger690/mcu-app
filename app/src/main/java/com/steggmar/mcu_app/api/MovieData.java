package com.steggmar.mcu_app.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieData extends Thread {

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

    public MovieData(int id){
        this.id = String.valueOf(id);
        this.start();
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MovieData(String title){
        this.title = title;
        this.start();
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private JSONObject requestById() {
        try {
            URL url = new URL("https://mcuapi.herokuapp.com/api/v1/movies/" + this.id);
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

    private JSONObject requestByTitle(){
        try {
            URL url = new URL("https://mcuapi.herokuapp.com/api/v1/movies?page=1&order=chronology%2CASC&filter=title%3DAvengers" + this.title);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            System.out.println(connection.getResponseCode());

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer responseContent = new StringBuffer();

            while ((line = br.readLine()) != null) {
                responseContent.append(line);
            }
            br.close();
            connection.disconnect();

            JSONObject obj = new JSONObject(String.valueOf(responseContent));
            JSONArray arr = obj.getJSONArray("data");
            return arr.getJSONObject(0);
        }
        catch (IOException | JSONException e) {
            return new JSONObject();
        }
    }

    private void applyValues(){
        try{
            this.id = this.response.getString("id");
            this.title = this.response.getString("title");
            this.release_date = this.response.getString("release_date");
            this.box_office = this.response.getString("box_office");
            this.overview = this.response.getString("overview");
            this.cover_url = this.response.getString("cover_url");
            this.trailer_url = this.response.getString("trailer_url");
            this.directed_by = this.response.getString("directed_by");
            this.phase = this.response.getString("phase");
            this.saga = this.response.getString("saga");
            this.chronology = this.response.getString("chronology");
            this.post_credit_scenes = this.response.getString("post_credit_scenes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void run(){
        if (this.id != null) {
            this.response = requestById();
        } else {
            this.response = requestByTitle();
        }
        this.applyValues();
        System.out.println(this.response.toString());
    }

    public String getMovieId() { return id; }

    public String getTitle() { return title; }

    public String getRelease_date() { return release_date; }

    public String getBox_office() { return box_office; }

    public String getOverview() { return overview; }

    public String getCover_url() { return cover_url; }

    public String getTrailer_url() { return trailer_url; }

    public String getDirected_by() { return directed_by; }

    public String getPhase() { return phase; }

    public String getSaga() { return saga; }

    public String getChronology() { return chronology; }

    public String getPost_credit_scenes() { return post_credit_scenes; }

}
