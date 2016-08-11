//Heather Wolford
//CPMD Week 1
//August 2016

package com.java2.heatherwolford.cpmdweek1;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

public class CustomIntentService extends IntentService{

    public static final String TAG = "CustomIntentService: ";
    public static final String ACTION_UPDATE_LIST = "com.java2.heatherwolford.h_wolford_cannabisconnection.ACTION_UPDATE_LIST";
    public static final String EXTRA_LIST = "com.java2.heatherwolford.h_wolford_cannabisconnection.EXTRA_LIST";
    public static final String RESULT = "RESULT";
    LocalBroadcastManager broadcaster;
    public ArrayList<Grocery> groceryArrayList = new ArrayList<>();

    public CustomIntentService() {
        super("CustomIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    public static void startActionUpdateList(Context context, ArrayList<Grocery> list) {
        System.out.println(TAG + "startActionUpdateList");
        Intent intent = new Intent(context, CustomIntentService.class);
        intent.setAction(ACTION_UPDATE_LIST);
        intent.putExtra(EXTRA_LIST, list);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println(TAG + "onHandleIntent");
        if (intent != null) {
            System.out.println(TAG + "onHandleIntent - intent != null");
            final String action = intent.getAction();
            if (ACTION_UPDATE_LIST.equals(action)) {
                System.out.println(TAG + "onHandleIntent - ACTION_UPDATE_LIST");
                groceryArrayList = (ArrayList<Grocery>) intent.getSerializableExtra(CustomIntentService.EXTRA_LIST);
                handleActionUpdateList(groceryArrayList);
            }
        }
    }

    private void handleActionUpdateList(ArrayList<Grocery> list) {
        System.out.println(TAG + "handleActionUpdateList");
        Intent intent = new Intent(RESULT);
        intent.putExtra(EXTRA_LIST, list);
        broadcaster.sendBroadcast(intent);
    }
}
