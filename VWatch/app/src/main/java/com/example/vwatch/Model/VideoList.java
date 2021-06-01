package com.example.vwatch.Model;

import android.net.Uri;

import com.example.vwatch.R;

import java.net.URL;
import java.util.ArrayList;

public class VideoList {
    public ArrayList<Uri>videos = new ArrayList<Uri>();
    public ArrayList<String>title = new ArrayList<String>();
    public ArrayList<String>name = new ArrayList<String>();
    public void addVideos(String uriPath){

        videos.add(Uri.parse(uriPath));
    }

    public void addTitle(String Title){
        title.add(Title);
    }

    public void addName(String Name){
        name.add(Name);
    }

}

