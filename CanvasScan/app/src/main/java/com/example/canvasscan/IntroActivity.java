package com.example.canvasscan;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvasscan.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {
   ActivityIntroBinding binding;
    private final String log_tag = "Saswat";
    java.util.Timer Timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityIntroBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());

        binding.startLayout.animate().alpha(1).setDuration(500);
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.blink);
        binding.sloganView.startAnimation(anim1);
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.blink);
        binding.slogan2View.startAnimation(anim2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(log_tag,"will send to main-activity after 3500ms");

                Intent intent = (new Intent(IntroActivity.this, MainActivity.class));
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(IntroActivity.this).toBundle());
                finishAffinity();

            }
        }, 3500);


    }


}