package com.bignerdranch.android.vkonline;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;


import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;


import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements IView{

    private String[] scope = new String[]{VKScope.STATUS, VKScope.OFFLINE};
    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TaskAdapter mTaskAdapter;
    private IPresenter mIPresenter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
                fab.show();
                invalidateOptionsMenu();
            }
            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void login() {
        VKSdk.login(this, scope);
    }

    @Override
    public void isLoggedIn() {
        fab.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
    }

    private void initialization(){
        mIPresenter = new Presenter();
        mIPresenter.attachView(this);

        Realm.init(this);
        App.realm = Realm.getDefaultInstance();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIPresenter.fabOnClick();
            }
        });
        fab.hide();

        mIPresenter.checkAuthorization();
        mIPresenter.viewIsReady();
    }

    @Override
    public void showTimePickerFragment() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void showList(RealmResults<Task> tasks) {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mTaskAdapter = new TaskAdapter(tasks, mIPresenter);
        mRecyclerView.setAdapter(mTaskAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.realm.close();
        mIPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(VKSdk.isLoggedIn() == true)
            menu.add(0, 0, 1, "Выйти");
        else menu.add(0, 1, 1, "Авторизация");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case 1:
                VKSdk.login(this, scope);
                return true;
            case 0:
                VKSdk.logout();
                invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
