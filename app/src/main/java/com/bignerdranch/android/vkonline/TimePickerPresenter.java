package com.bignerdranch.android.vkonline;


import com.evernote.android.job.JobManager;

import java.util.Calendar;

/**
 * Created by Алексей on 01-Sep-17.
 */

public class TimePickerPresenter implements ITimePickerPresenter{
    private ITimePickerFragment view;
    private TimePickerModel model = new TimePickerModel();
    @Override
    public void createMessageForToast(int time) {
        int hour = time / 60;
        int minute = time % 60;
        String stringHour = "";
        String stringMinute = "";
        if(hour == 1 || hour == 21)
            stringHour = hour + " час";
        if((hour >= 2 && hour <= 4) || (hour >= 22 && hour <= 24))
            stringHour = hour + " часа";
        if((hour >= 5 && hour <= 20))
            stringHour = hour + " часов";
        view.makeToast("До сигнала осталось " + stringHour + ":" + minute + " минуты");
    }

    @Override
    public int differenceBetweenTime(int settingTime) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int currentTime = hour * 60 + minute;
        int result;
        if(currentTime > settingTime)
            result = 1440 - currentTime + settingTime;
        else result = settingTime - currentTime;
        return result;
    }

    @Override
    public void onTimeSet(int hourOfDay, int minute) {
        int difference = differenceBetweenTime(hourOfDay * 60 + minute);
        int jobId = DemoSyncJob.scheduleJob(difference * 60000);
        model.addTaskToDB(hourOfDay, minute, jobId);
        createMessageForToast(difference);
    }

    @Override
    public void attachView(ITimePickerFragment view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
