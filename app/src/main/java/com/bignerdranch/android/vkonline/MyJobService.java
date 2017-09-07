package com.bignerdranch.android.vkonline;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.util.Log;

/**
 * Created by Алексей on 17.08.2017.
 */

public class MyJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("MyLog", "JobStarting");
        jobFinished(params, true);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


}
