package com.steggmar.mcu_app.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.steggmar.mcu_app.R;

public class MainActivity extends AppCompatActivity {

    private Button btnLaunchCountdown;
    private Button btnLaunchSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_activity);

        this.btnLaunchCountdown = findViewById(R.id.btnLaunchCountdown);
        this.btnLaunchSearch = findViewById(R.id.btnLaunchSearch);

        this.btnLaunchCountdown.setOnClickListener(event -> eventLaunchCountdown());
        this.btnLaunchSearch.setOnClickListener(event -> eventLaunchSearch());

    }


    private void eventLaunchCountdown(){
        Intent intent = new Intent(MainActivity.this, CountdownActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    private void eventLaunchSearch(){
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Checks if the device is connected to the internet via Wi-Fi or Mobile Data
     * @return true if device is connected.
     */
    private boolean checkInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

}