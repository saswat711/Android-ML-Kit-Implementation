package com.ocr.canvasscan;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvasscan.R;
import com.example.canvasscan.databinding.ActivityIntroBinding;

import java.util.Timer;
import java.util.TimerTask;

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
        Application application = getApplication();
        Timer = new Timer();
        Timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               Log.d(log_tag,"sent to main activity after 3500ms");
                               // Show the app open ad.
                               ((MyApplication) application)
                                       .showAdIfAvailable(
                                               IntroActivity.this,
                                               new MyApplication.OnShowAdCompleteListener() {
                                                   @Override
                                                   public void onShowAdComplete() {
                                                       Intent intent = (new Intent(IntroActivity.this, MainActivity.class));
                                                       startActivity(intent);
                                                       overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                       finishAffinity();
                                                   }
                                               });
                           }
                       },
                3500);





    }


}