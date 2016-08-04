//Heather Wolford
//CPMD Week 1
//August 2016

package com.java2.heatherwolford.cpmdweek1;

import android.app.Application;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper extends Application{

    private static final String TAG = "FirebaseHelper";
    private static FirebaseHelper instance;
    private static FirebaseDatabase mDatabase;
    private static DatabaseReference listRef;
    private static FirebaseAuth mAuth;
    private static String mUser;
    private static ArrayList<Grocery> groceryArrayList = new ArrayList<>();
    public static LocalBroadcastManager broadcaster;
    public static final String RESULT = "RESULT";
    public static final String EXTRA_LIST = "EXTRA_LIST";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    public static FirebaseHelper getInstance(){
        return instance;
    }

    //Save items to Firebase Database
    public static void addToFirebaseDatabase(ArrayList<Grocery> list){
        Log.d(TAG, "addToFirebaseDatabase - the size of the list to be saved is:" + list.size());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance();
        listRef = mDatabase.getReference(mUser);
        listRef.setValue(list);
        readFromFireBaseDatabase();
    }

    public static ArrayList<Grocery> readFromFireBaseDatabase(){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance();
        listRef = mDatabase.getReference(mUser);
        listRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                groceryArrayList.clear();
                ArrayList<Grocery> groceryList = new ArrayList<>();
                groceryList = (ArrayList<Grocery>) snapshot.getValue();
                if (groceryList != null){
                    for (int i=0; i<groceryList.size(); i++){
                        Map<String, Object> tempHash = new HashMap<>();
                        tempHash = (Map<String, Object>) groceryList.get(i);
                        String item = (String) tempHash.get("item");
                        Log.d(TAG, "The value of item is: " + item);
                        Long longQty = (Long) tempHash.get("amount");
                        int quantity = (int) (long) longQty;
                        Log.d(TAG, "The value of quantity is: " + quantity);
                        Grocery grocery = new Grocery(item, quantity);
                        groceryArrayList.add(grocery);
                    }
                    Intent intent = new Intent(RESULT);
                    intent.putExtra(EXTRA_LIST, groceryArrayList);
                    broadcaster = LocalBroadcastManager.getInstance(getInstance());
                    broadcaster.sendBroadcast(intent);
                    Log.d(TAG, "Should have gone to broadcast receiver");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return groceryArrayList;
    }
}
