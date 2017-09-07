package com.bignerdranch.android.vkonline;

/**
 * Created by Алексей on 01-Sep-17.
 */

public interface ITimePickerPresenter {
    void createMessageForToast(int time);
    int differenceBetweenTime(int settingTime);
    void attachView(ITimePickerFragment view);
    void detachView();
    void onTimeSet(int hourOfDay, int minute);
}
