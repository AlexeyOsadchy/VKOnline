package com.bignerdranch.android.vkonline;

/**
 * Created by Алексей on 01-Sep-17.
 */

public class TimePickerModel {
    public void addTaskToDB (int hour, int minute, int requestCode){
        App.realm.beginTransaction();
        Task task = App.realm.createObject(Task.class);
        if(hour < 10)
            task.setHour(0 + String.valueOf(hour));
        else task.setHour(String.valueOf(hour));
        if(minute < 10)
            task.setMinute(0 + String.valueOf(minute));
        else task.setMinute(String.valueOf(minute));
        task.setRequestCode(requestCode);
        App.realm.commitTransaction();
    }
}
