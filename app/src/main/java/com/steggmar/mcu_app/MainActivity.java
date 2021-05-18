package com.steggmar.mcu_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.steggmar.mcu_app.api.MovieDataSet;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvReleasesInXDays;
    private TextView tvType;
    private TextView mlOverview;
    private ImageView ivPoster;
    private ImageView ivHome;
    private ImageView ivBack;

    private Button btnNext;

    private MovieDataSet movie;
    private MovieDataSet nextMovie;
    private MovieDataSet previousMovie;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.movie_countdown);

        this.movie = new MovieDataSet();
        this.nextMovie = new MovieDataSet(this.movie.getRelease_date());
        this.previousMovie = null;

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

    @SuppressLint("SetTextI18n")
    private void eventBtnNext(){
        // this.movie = new MovieDataSet(this.movie.getRelease_date());

        this.btnNext.setText("NEXT RELEASE");

        /*
        while(!checkInternetConnection()){
            setContentView(R.layout.no_connection);
        }
        checkInternetConnection();
         */


        // this.previousMovie = this.movie;
        this.movie = this.nextMovie;
        this.nextMovie = new MovieDataSet(this.movie.getRelease_date());
        if(this.movie.getFollowing_production() == null){
            this.btnNext.setText("BACK TO START");
            this.nextMovie = new MovieDataSet();
        }
        updateView();
    }

    private void eventIvHome(){
        this.movie = new MovieDataSet();
        this.nextMovie = new MovieDataSet(this.movie.getRelease_date());
        updateView();
    }

    private void eventIvBack(){
        /*
        if(this.previousMovie == null){
            // TODO: Load home menu
        }
         */
        this.nextMovie = this.movie;
        // this.movie = this.previousMovie;
    }

    @SuppressLint("SetTextI18n")
    private void updateView(){
        this.tvTitle.setText(movie.getTitle());
        this.tvReleasesInXDays.setText("releases in " + movie.getDays_until() + " days!");
        this.tvType.setText(movie.getType());
        this.mlOverview.setText(movie.getOverview());
        Picasso.get().load(movie.getPoster_url()).into(this.ivPoster);
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