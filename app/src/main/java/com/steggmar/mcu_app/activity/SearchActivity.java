package com.steggmar.mcu_app.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.steggmar.mcu_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ImageView ivSearch;
    EditText etSearchField;
    ListView lvSearchResults;

    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Permits blocking methods on the main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.ivSearch = findViewById(R.id.ivSearch);
        this.etSearchField = findViewById(R.id.etSearchField);
        this.lvSearchResults = findViewById(R.id.lvSearchResults);

        this.ivSearch.setOnClickListener(event -> eventIvSearch());

        lvSearchResults.setOnItemClickListener((parent, view, position, id) -> System.out.println(parent.getAdapter().getItem(position)));

    }

    /**
     * Gets the Titles and IDs from the API and displays each title in the list view
     */
    private void eventIvSearch() {

        try {
            URL url = new URL("https://mcuapi.herokuapp.com/api/v1/movies?page=1&columns=title%2Cid&order=chronology%2CASC&filter=title%3D" + this.etSearchField.getText());
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

            JSONObject response = new JSONObject(String.valueOf(responseContent));
            JSONArray searchResults = response.getJSONArray("data");

            ArrayList<String> movieTitles = new ArrayList<>();

            for(int i = 0; i < searchResults.length(); i++){
                JSONObject result = searchResults.getJSONObject(i);
                System.out.println(result.getString("title"));
                movieTitles.add(result.getString("title"));
            }

            this.arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, movieTitles);

            lvSearchResults.setAdapter(arrayAdapter);

        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

}