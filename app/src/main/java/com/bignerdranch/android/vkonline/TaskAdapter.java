package com.bignerdranch.android.vkonline;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class TaskAdapter extends android.support.v7.widget.RecyclerView.Adapter<TaskAdapter.TaskHolder> implements RealmChangeListener{
    private RealmResults <Task> mTaskList;
    private IPresenter mPresenter;
    public TaskAdapter(RealmResults <Task> taskList, IPresenter presenter){
        mTaskList = taskList;
        mTaskList.addChangeListener(this);
        mPresenter = presenter;
    }

    @Override
    public void onChange(Object o) {
        notifyDataSetChanged();
    }

    public class TaskHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements View.OnLongClickListener{
        private TextView mTextView;
        private CheckBox mCheckBox;
        public TaskHolder(View itemView){
            super(itemView);
            itemView.setOnLongClickListener(this);
            mTextView = (TextView)itemView.findViewById(R.id.cv_textView);
            mCheckBox = (CheckBox)itemView.findViewById(R.id.checkBox);
            mCheckBox.setClickable(false);
        }
        
        @Override
        public boolean onLongClick(View v) {
            mPresenter.longClickList(mTaskList.get(getAdapterPosition()));
            return false;
        }
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        holder.mTextView.setText(mTaskList.get(position).getHour() +":" + mTaskList.get(position).getMinute());
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }
}
