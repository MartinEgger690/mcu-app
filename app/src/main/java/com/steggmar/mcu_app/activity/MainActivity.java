package com.steggmar.mcu_app.activity;

import android.content.Intent;
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
        startActivity(new Intent(MainActivity.this, CountdownActivity.class));
    }

    private void eventLaunchSearch(){

    }



}