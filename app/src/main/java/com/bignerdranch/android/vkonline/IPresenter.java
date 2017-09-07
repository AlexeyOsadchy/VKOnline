package com.bignerdranch.android.vkonline;

/**
 * Created by Алексей on 01-Sep-17.
 */

public interface IPresenter {
    void checkAuthorization();
    void attachView(IView view);
    void detachView();
    void viewIsReady();
    void fabOnClick();
    void longClickList(Task task);
}
