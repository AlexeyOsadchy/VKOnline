package com.bignerdranch.android.vkonline;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener, ITimePickerFragment {
    private final Calendar c = Calendar.getInstance();
    private int hour = c.get(Calendar.HOUR_OF_DAY);
    private int minute = c.get(Calendar.MINUTE);
    private ITimePickerPresenter mPresenter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mPresenter = new TimePickerPresenter();
        mPresenter.attachView(this);
        return new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,this,hour,minute,true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mPresenter.onTimeSet(hourOfDay, minute);
    }

    @Override
    public void makeToast(String message) {
        Toast toast = Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }
}
