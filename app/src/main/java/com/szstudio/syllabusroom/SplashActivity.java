package com.szstudio.syllabusroom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {
    TextView tvTitle, tvSlogan;
    ImageView ivLogo;
    Animation animGrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivLogo=findViewById(R.id.ivSplashLogo);
        tvTitle=findViewById(R.id.tvSplashTitle);
        tvSlogan=findViewById(R.id.tvSplashSlogan);
        animGrow= AnimationUtils.loadAnimation(SplashActivity.this, R.anim.grow_anim);

        ivLogo.startAnimation(animGrow);
        tvTitle.startAnimation(animGrow);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent i=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
            }
        },1500);

    }
}