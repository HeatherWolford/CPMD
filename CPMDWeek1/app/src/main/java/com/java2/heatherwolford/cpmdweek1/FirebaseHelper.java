//Heather Wolford
//CPMD Week 1
//August 2016

package com.java2.heatherwolford.cpmdweek1;

import android.app.Application;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseHelper extends Application{

    private static final String TAG = "FirebaseHelper";
    private static FirebaseHelper instance;
    private static FirebaseDatabase database;
    private ArrayList<Grocery> groceryArrayList;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static FirebaseHelper getInstance(){
        return instance;
    }

    //Save items to Firebase Database
    public static void addToFirebaseDatabase(ArrayList<Grocery> list){
        Log.d(TAG, "addToFirebaseDatabase - the size of the list to be saved is:" + list.size());
        database = FirebaseDatabase.getInstance();
        DatabaseReference listRef = database.getReference("list");
        listRef.setValue(list);

        // Read from the database
//        listRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                System.out.println("There are " + snapshot.getChildrenCount() + " grocery items");
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                ArrayList<Grocery> groceryArrayList = new ArrayList<>();
//                for (DataSnapshot listSnapshot: snapshot.getChildren()) {
//                    Grocery grocery = listSnapshot.getValue(Grocery.class);
//                    System.out.println(grocery.getItem());
//                    groceryArrayList.add(grocery);
//                }
//
//                //List<Grocery> value = dataSnapshot.getValue(List.class);
//                //ArrayList<Grocery> groceryArrayList = new ArrayList<Grocery>(value);
//                Log.d(TAG, "The size of the array is: " + groceryArrayList.size());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }

    //Read items from the Firebase Database
    public static ArrayList<Grocery> readFromFirebaseDatabase(){
        ArrayList<Grocery> groceryArrayList = new ArrayList<>();
        //Todo: Need to actually get data from the database
        //Temp hardcoded for testing
        Grocery grocery1 = new Grocery("bread", 1);
        Grocery grocery2 = new Grocery("milk", 1);
        Grocery grocery3 = new Grocery("eggs", 24);
        groceryArrayList.add(grocery1);
        groceryArrayList.add(grocery2);
        groceryArrayList.add(grocery3);
        return groceryArrayList;
    }
}
