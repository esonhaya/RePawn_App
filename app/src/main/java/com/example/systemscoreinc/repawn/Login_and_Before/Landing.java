package com.example.systemscoreinc.repawn.Login_and_Before;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.example.systemscoreinc.repawn.R;

public class Landing extends AppCompatActivity {
    private TextView sp_tv;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        sp_tv = findViewById(R.id.splash_tv);
        //sp_tv=findViewById(R.id.splash_tv);

        Animation animSplash = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        //sp_tv.startAnimation(animSplash);
        sp_tv.startAnimation(animSplash);

        final Intent iSplash = new Intent(Landing.this, LoginActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(iSplash);
                    finish();
                }
            }
        };

        timer.start();

    }
}

