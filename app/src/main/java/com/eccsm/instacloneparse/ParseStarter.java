package com.eccsm.instacloneparse;

import android.app.Application;

import com.parse.Parse;

public class ParseStarter extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("APP_ID")
                .clientKey("CLIENT_KEY")
                .server("SERVER(AWS etc.)")
                .build()
        );
    }
}
