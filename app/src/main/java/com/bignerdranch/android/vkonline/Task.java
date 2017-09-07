package com.bignerdranch.android.vkonline;

import android.app.PendingIntent;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Алексей on 01.08.2017.
 */

public class Task  extends RealmObject {
    @Required

    private String title;
    private String mHour;
    private String mMinute;
    private int requestCode;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMinute() {
        return mMinute;
    }

    public void setMinute(String minute) {
        mMinute = minute;
    }

    public String getHour() {
        return mHour;
    }

    public void setHour(String hour) {
        mHour = hour;
    }


    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
