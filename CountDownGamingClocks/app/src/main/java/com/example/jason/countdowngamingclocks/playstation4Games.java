package com.example.jason.countdowngamingclocks;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;

public class playstation4Games extends AppCompatActivity {

    private TextView txtCodWWII;
    private Handler handler;
    private Runnable runnable;
    private AdView mAdViewPS;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playstation4_games);
        //countDownStart();

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        //use for testing
        // switch to actual code on release
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        mAdViewPS = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewPS.loadAd(adRequest);

        //pulls names and dates from txt files
        String saGameList[] = new String[100];
            try {
                InputStream isGames = getAssets().open("GameNamesAndDates.txt");
                int iSize = isGames.available();
                byte[] buffer = new byte[iSize];
                isGames.read(buffer);
                isGames.close();

                String sGameNames = new String(buffer);
                saGameList = sGameNames.split(",");
                //Toast.makeText(this, "reading file", Toast.LENGTH_SHORT).show();
                //Toast.makeText(playstation4Games.this, "" + saGameList[2], Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                Toast.makeText(this, "error reading file", Toast.LENGTH_SHORT).show();
              }
        countDownStart(saGameList);
        Toast.makeText(playstation4Games.this, "" + saGameList[0], Toast.LENGTH_SHORT).show();
    }
    public void countDownStart(String saGameList[] )
    {

        //variables for months, days, hours, and seconds calculations
        final int iDays = 86400000,iHours = 3600000, iMinutes = 60000, iSeconds = 60000;
        final long LMonths = 2629746000L;
        final String fsGamesList[] = saGameList;
        //object for the Cod WWII textView
        Toast.makeText(playstation4Games.this, "" + fsGamesList[2], Toast.LENGTH_SHORT).show();
        txtCodWWII = (TextView) findViewById(R.id.txtGame1);
        handler = new Handler();
        //Toast.makeText(playstation4Games.this, "" + fsGamesList[2], Toast.LENGTH_SHORT).show();
        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                handler.postDelayed(this, 1000);
                try
                {
                    //Toast.makeText(playstation4Games.this, "" + fsGamesList[2], Toast.LENGTH_SHORT).show();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    // Please here set your event date//YYYY-MM-DD
                    Date futureDate = dateFormat.parse(fsGamesList[3]);
                    Date currentDate = new Date();
                    //Ths if statement converts the milliseconds till launch date to
                    // months days hours minutes and seconds
                    if (!currentDate.after(futureDate))
                    {
                        long diff = futureDate.getTime()-currentDate.getTime();
                        long months = diff / LMonths;
                        diff -= months * LMonths;
                        long days = diff / iDays;
                        diff -= days * iDays;
                        long hours = diff / iHours;
                        diff -= hours * iHours;
                        long minutes = diff / iMinutes;
                        diff -= minutes * iMinutes;
                        long seconds = diff / 1000;


                        //prints the countdown clock till launch to the cod textView
                        if(months == 0)
                        {
                            if (days < 0)
                            {
                                if (hours == 0)
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
                            txtCodWWII.setText(months + " months " + days + " Days " + hours + " Hours " + minutes + " Min " + seconds + " sec");
                        }
                    }
                    else
                    {
                        //if the reached launch day change the textView to day launch day
                        LaunchDay();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }
    //method to change Cod textView
    public void LaunchDay()
    {
        txtCodWWII.setText("Launch Day!");
    }
}
