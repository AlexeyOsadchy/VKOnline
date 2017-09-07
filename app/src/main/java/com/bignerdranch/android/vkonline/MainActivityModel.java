package com.bignerdranch.android.vkonline;


import java.util.List;

import io.realm.RealmResults;

import static com.bignerdranch.android.vkonline.App.realm;

public class MainActivityModel {
    public RealmResults<Task> loadTask(){
        return realm.where(Task.class).findAll();
    }

    public void deleteFromDB(int requestCode){
        realm.beginTransaction();
        App.realm.where(Task.class).equalTo("requestCode", requestCode).findAll().deleteAllFromRealm();
        App.realm.commitTransaction();
    }
}
