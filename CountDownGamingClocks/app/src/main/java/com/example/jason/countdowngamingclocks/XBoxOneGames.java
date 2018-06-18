package com.example.jason.countdowngamingclocks;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XBoxOneGames extends AppCompatActivity {
    //final String sDate = "2018-10-11";
    private TextView txtCodWWII;
    private Handler handler;
    private Runnable runnable;
    private AdView mAdViewPS;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xbox_one_games);


        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");

        mAdViewPS = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewPS.loadAd(adRequest);



        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.headerlayout);
        countDownStart();
    }
    public void countDownStart()
    {
        //variables for months, days, hours, and seconds calculations
        final int IDAYS = 86400000, IHOURS = 3600000, IMINUTES = 60000;
        final long LMonths = 2629746000L;
        //object for the Cod WWII textView
        txtCodWWII = (TextView) findViewById(R.id.txtGame1);
        final String fsGameLaunch = "2017-12-20";
        handler = new Handler();
        runnable = new Runnable()
        {
            @Override
            public void run() {
                //final String fsGameLaunch = "2017-12-24";
                handler.postDelayed(this, 1000);
                try
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    // Sets the date for games here for game release
                    // format is YYYY-MM-DD
                    Date futureDate = dateFormat.parse(fsGameLaunch);
                    Date currentDate = new Date();
                    //Ths if statement converts the milliseconds till launch date to
                    // months days hours minutes and seconds
                    if (!currentDate.after(futureDate))
                    {
                        long diff = futureDate.getTime() - currentDate.getTime();
                        long months = diff / LMonths;
                        diff -= diff / LMonths;
                        long days = diff / IDAYS;
                        diff -= days * IDAYS;
                        long hours = diff / IHOURS;
                        diff -= hours * IHOURS;
                        long minutes = diff / IMINUTES;
                        diff -= minutes * IMINUTES;
                        long seconds = diff / 1000;

                        //prints the countdown clock till launch to the cod textView
                        if(months == 0)
                        {
                            if(days == 0)
                            {
                                if(hours == 0)
                                {
                                    txtCodWWII.setText(minutes + " Min " + seconds + " sec");
                                }
                                else
                                {
                                    txtCodWWII.setText(hours + " Hours " + minutes + " Min " + seconds + " sec");
                                }
                            }
                            else
                            {
                                txtCodWWII.setText(days + " Days " + hours + " Hours " + minutes + " Min " + seconds + " sec");
                            }
                        }
                        else
                        {
                            txtCodWWII.setText(months + " Months "  + days + " Days " + hours + " Hours " + minutes + " Min ");
                        }
                    }
                    else
                    {
                        //if it has reached launch day change the textView to say launch day
                        LaunchDay();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }
    //method to change Cod textView
    public void LaunchDay(){txtCodWWII.setText("Launch Day!");}
}