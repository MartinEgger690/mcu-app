package com.steggmar.mcu_app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.steggmar.mcu_app.R;
import com.steggmar.mcu_app.api.ReleaseData;

public class CountdownActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvReleasesInXDays;
    private TextView tvType;
    private TextView mlOverview;
    private ImageView ivPoster;
    private ImageView ivHome;
    private ImageView ivBack;

    private Button btnNext;

    private ReleaseData movie;
    private ReleaseData nextMovie;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.movie_countdown);

        this.movie = new ReleaseData();
        this.nextMovie = new ReleaseData(this.movie.getRelease_date());

        this.tvTitle = findViewById(R.id.tvTitle);
        this.tvTitle.setSelected(true);

        this.tvReleasesInXDays = findViewById(R.id.tvReleasesInXDays);
        this.tvType = findViewById(R.id.tvType);
        this.mlOverview = findViewById(R.id.tvOverview);
        this.ivPoster = findViewById(R.id.ivPoster);
        this.btnNext = findViewById(R.id.btnNext);
        this.ivHome = findViewById(R.id.ivHome);
        this.ivBack = findViewById(R.id.ivBack);

        updateView();

        btnNext.setOnClickListener(event -> eventBtnNext());
        ivHome.setOnClickListener(event -> eventIvHome());
        ivBack.setOnClickListener(event -> eventIvBack());
    }

    /**
     * Downloads the data for the next movie and writes it into this.movie
     */
    @SuppressLint("SetTextI18n")
    private void eventBtnNext(){
        this.btnNext.setText("NEXT RELEASE");
        this.movie = this.nextMovie;
        this.nextMovie = new ReleaseData(this.movie.getRelease_date());
        if(this.movie.getFollowing_production() == null){
            this.btnNext.setText("BACK TO START");
            this.nextMovie = new ReleaseData();
        }
        updateView();
    }

    /**
     * Closes this activity and opens the main window
     */
    private void eventIvHome(){
        Intent intent = new Intent(CountdownActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void eventIvBack(){
        this.nextMovie = this.movie;
    }

    /**
     * Updates all View-Elements
     */
    @SuppressLint("SetTextI18n")
    private void updateView(){
        this.tvTitle.setText(movie.getTitle());
        this.tvReleasesInXDays.setText("releases in " + movie.getDays_until() + " days!");
        this.tvType.setText(movie.getType());
        this.mlOverview.setText(movie.getOverview());
        Picasso.get().load(movie.getPoster_url()).into(this.ivPoster);
    }

}