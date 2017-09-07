package com.bignerdranch.android.vkonline;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Алексей on 30.08.2017.
 */

public class ServiceController {
    private Context mContext;
    public boolean wifiState = false;
    public ServiceController(Context context){
        mContext = context;
    }

    public void sendRequest(int requestCode){
        Log.i("MyLog1", "Service start");

        showNotification(requestCode);

        //writeFileSD(getCurrentDate() + " Service start");

        WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

        if(wifi.getWifiState() == WifiManager.WIFI_STATE_DISABLED && hasConnection(mContext) == false) {
            wifiState = true;
            wifi.setWifiEnabled(true);
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //writeFileSD(getCurrentDate() + " Request is sending...");
        VKRequest request = new VKRequest("account.setOnline", VKParameters.from());
        request.executeSyncWithListener(a);
    }

    public static void writeFileSD(String str) {
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath());
        File sdFile = new File(sdPath, "VKonlineLog.txt");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile, true));
            bw.append(str);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentDate(){
        Date date = new Date();
        String str = date.toString();
        String time = str.substring(11,19);
        String day = str.substring(4, 10);
        String year = str.substring(30, 34);
        return year + " " + day + " " + time;
    }

    public static String convertMilisecondsToDate(long time){

        return time/3600000 + ":" + time%3600000/60000;
    }

    public void showNotification(int idNotify){
        Notification.Builder builder = new Notification.Builder(mContext);
        builder.setSmallIcon(R.drawable.ic_ab_app)
                .setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentTitle("VKonline").setContentText("ServiceStart");
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager)mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        notificationManager.notify(idNotify, notification);
    }

    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    VKRequest.VKRequestListener a = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            if(wifiState == true) wifi.setWifiEnabled(false);
            writeFileSD(getCurrentDate() + " Request complete");
            Log.i("MyLog1", "on complete");
            showNotification(14636);

        }
        @Override
        public void onError(VKError error) {
            writeFileSD(getCurrentDate() + " Request fail!" + error.errorMessage);
            Log.i("MyLog1", "on error" + error.errorMessage);
        }
        @Override
        public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
            writeFileSD(getCurrentDate() + " attemptFailed!");
        }
    };
}
