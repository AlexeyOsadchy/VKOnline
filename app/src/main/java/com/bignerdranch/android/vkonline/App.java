package com.bignerdranch.android.vkonline;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.vk.sdk.VKSdk;

import io.realm.Realm;

public class App extends Application {
    public static Realm realm;
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
        JobManager.create(this).addJobCreator(new DemoJobCreator());
    }
}
