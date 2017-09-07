package com.bignerdranch.android.vkonline;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;


public class DemoSyncJob extends Job {
    public static final String TAG = "job_demo_tag";

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        ServiceController serviceController = new ServiceController(getContext());
        serviceController.sendRequest(params.getId());
        Log.i("MyLog", "JobSuccess");
        return Result.SUCCESS;
    }

    public static int scheduleJob(long exactTimeMs) {
        Log.i("MyLog", "JobStart");
        int jobId = new JobRequest.Builder(DemoSyncJob.TAG)
                .setExact(exactTimeMs+1)
                .setPersisted(true)
                .build()
                .schedule();
        return jobId;
    }

    public static void cancelJob(int jobId) {
        JobManager.instance().cancel(jobId);
    }
}
