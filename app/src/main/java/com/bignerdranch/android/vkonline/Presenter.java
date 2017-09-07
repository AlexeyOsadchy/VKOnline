package com.bignerdranch.android.vkonline;


import com.vk.sdk.VKSdk;

import io.realm.RealmResults;

public class Presenter implements IPresenter{

    private IView mIView;
    private MainActivityModel mModel = new MainActivityModel();

    @Override
    public void checkAuthorization() {
        if(!VKSdk.isLoggedIn())
            mIView.login();
        else mIView.isLoggedIn();
    }

    @Override
    public void attachView(IView view) {
        mIView = view;
    }

    @Override
    public void detachView() {
        mIView = null;
    }

    @Override
    public void viewIsReady() {
        mIView.showList(mModel.loadTask());
    }

    @Override
    public void fabOnClick() {
        mIView.showTimePickerFragment();
    }

    @Override
    public void longClickList(Task task) {
        DemoSyncJob.cancelJob(task.getRequestCode());
        mModel.deleteFromDB(task.getRequestCode());
    }
}
