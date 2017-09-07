package com.bignerdranch.android.vkonline;

import io.realm.RealmResults;

/**
 * Created by Алексей on 01-Sep-17.
 */

public interface IView {
    void login();
    void isLoggedIn();
    void showList(RealmResults<Task> tasks);
    void showTimePickerFragment();
}
