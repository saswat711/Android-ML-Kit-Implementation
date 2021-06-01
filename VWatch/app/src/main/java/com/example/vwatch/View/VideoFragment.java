package com.example.vwatch.View;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.vwatch.Controller.MainActivity;
import com.example.vwatch.Model.VideoList;
import com.example.vwatch.R;
import com.example.vwatch.databinding.FragmentVideoBinding;

public class VideoFragment extends Fragment {
    FragmentVideoBinding binding;
    private VideoList vList = new VideoList();
    private  int up =0; // for using in up swipe
    private  int down; // For using in down swipe
    private  int state=0; // For Saving THE instance state
    int tap = 0; // For Single Tap
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create an object of the Android_Gesture_Detector  Class

        // Create a GestureDetector
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVideoBinding.inflate(getLayoutInflater());

        //Using The video List
        vList.addVideos("android.resource://" + "com.example.vwatch"+ "/" + R.raw.v1);
        vList.addVideos("android.resource://" + "com.example.vwatch" + "/" + R.raw.v2);
        vList.addVideos("android.resource://" + "com.example.vwatch" + "/" + R.raw.v3);
        vList.addVideos("android.resource://" + "com.example.vwatch" + "/" + R.raw.v4);
        vList.addVideos("android.resource://" + "com.example.vwatch" + "/" + R.raw.v5);

        //for swipe down
        down=vList.videos.size();
        //Updating The Video View
        binding.videoView.setVideoURI(vList.videos.get(0));
        //start playing
        binding.videoView.start();

        //On Complete Listener
        binding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("Video","Video Completed");
                binding.videoView.start();
                // display a toast when an video is completed
            }
        });
        
        //On Error Listener
        binding.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d("Video","An error occurred when plating video");
                Toast.makeText(getActivity(), "oops!! An error occurred when playing video...", Toast.LENGTH_SHORT).show();
                // display a toast when an error occurs while playing an video
                return false;
            }
        });

        // Settin Up Gesture Detector
        GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                Log.d("Gesture ", "Left to Right swipe: "+ e1.getX() + " - " + e2.getX());
                                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");
                                Toast.makeText(getContext(),"Subscribed To The Creator",Toast.LENGTH_SHORT).show();
                                //For Subscribing
                            } else {
                                Log.d("Gesture ", "Right to Left swipe: " + e1.getX() + " - " + e2.getX());
                                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");
                                // Enable View Pager adapter in main Activity
                            }
                        }
                    }
                    else {
                        if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) {
                                Log.d("Gesture ", "Up to Down swipe: " + e1.getX() + " - " + e2.getX());
                                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
                                down--;
                                if(down<0){
                                    down=vList.videos.size();
                                }
                                //Updating The Video View
                                binding.videoView.setVideoURI(vList.videos.get(down));
                                // For saving the instance start
                                state = down;
                                //start playing
                                binding.videoView.start();
                            } else {
                                Log.d("Gesture ", "Down to Up swipe: " + e1.getX() + " - " + e2.getX());
                                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
                                up++;
                                if(up>=vList.videos.size()){
                                    up=0;
                                }
                                //Updating The Video View
                                binding.videoView.setVideoURI(vList.videos.get(up));
                                // For saving the instance start
                                state = up;
                                //start playing
                                binding.videoView.start();
                            }
                        }
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                return false;

            }
           // Setting up animation for single tap
           Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.blink);
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d("Gesture ", " onSingleTapConfirmed");
                if(tap==0){
                    binding.videoView.pause();
                    binding.PauseView.setImageDrawable(Drawable.createFromPath("@drawable/ic_pause_circle"));
                    binding.PauseView.startAnimation(anim);
                    binding.PauseView.setVisibility(View.VISIBLE);

                }
                if(tap>0){
                    binding.PauseView.setImageDrawable(Drawable.createFromPath("@drawable/ic_play_circle"));
                    binding.PauseView.startAnimation(anim);
                    binding.PauseView.setVisibility(View.INVISIBLE);
                    binding.videoView.start();
                    tap=-1;
                }
                tap++;
                return true;
            }
        };

        GestureDetector gestureDetector=new GestureDetector(simpleOnGestureListener);

        MainActivity.MyOnTouchListener myOnTouchListener = ev -> {
            gestureDetector.onTouchEvent(ev);
            return false;
        };

        ((MainActivity) getActivity()).registerMyOnTouchListener(myOnTouchListener);

        return binding.getRoot();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
       // This bundle will be passed to onCreate if the process is
      // killed and restarted.

        savedInstanceState.putInt("StringKeyForInteger", state);

     // etc.
    }

    @Override
    public void onResume() {
        binding.videoView.setVideoURI(vList.videos.get(state));
        binding.videoView.start();
        super.onResume();
    }

    @Override
    public void onStart(){
        binding.videoView.setVideoURI(vList.videos.get(state));
        binding.videoView.start();
        super.onStart();
    }

}