//Heather Wolford
//CPMD Week 1
//August 2016

package com.java2.heatherwolford.cpmdweek1;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityHelper extends Application {

    private static final String TAG = "ConnectivityHelper";
    private static ConnectivityHelper instance;
    private static Context context;
    private static ConnectivityManager cm;
    private static String type;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static ConnectivityHelper getInstance(){
        return instance;
    }

    public static boolean isConnected(){
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static String getType(){
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
            type = "WiFi";
            return type;
        }else if (activeNetwork.getType() == ConnectivityManager.TYPE_WIMAX){
            type = "WiMax";
            return type;
        }else{
            type = "Mobile Data";
            return type;
        }
    }
}
