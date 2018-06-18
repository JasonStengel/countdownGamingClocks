package com.example.jason.countdowngamingclocks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity
{
    int iIntentRequestCode = 1;
    private AdView mAdView;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        /*
        mAdViewPS = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdViewPS.loadAd(adRequest);
        */
        mAdView = (AdView) findViewById(R.id.adViewMain);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    //onClick for the playstation tab
    public void btn_playstation4_OnClick(View oView)
    {
        startActivityForResult(new Intent("com.example.jason.countdowngamingclocks.playstation4Games"), iIntentRequestCode);
    }
    //onClick for XBoxOneGames
    public void btn_XBoxOne_OnClick(View oView)
    {
        startActivityForResult(new Intent("com.example.jason.countdowngamingclocks.XBoxOneGames"), iIntentRequestCode);
    }
}
