package com.greenshopap.bel.greenshop_eca;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreenActivity extends AppCompatActivity {

    int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                timeLimit();
                startUI();
            }
        });
        thread.start();
    }
    public void timeLimit()
    {
        for(progress=10;progress<=100;progress+=25)
        {
            try{
                Thread.sleep(1000);
            }catch (Exception e)
            {

            }
        }
    }
    public void startUI()
    {
        startActivity(new Intent(SplashScreenActivity.this,LogingActivity.class));
        finish();
    }
}
