//Heather Wolford
//CPMD Week 1
//August 2016

package com.java2.heatherwolford.cpmdweek1;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements ListFragment.CustomListener{

    private final String TAG = "ListActivity";
    public int currentPosition;
    public ArrayList<Grocery> groceryArrayList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private String mUser;
    private DatabaseReference mDatabase;
    public BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(TAG + " Entered onCreate.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (toolbar != null) {
            toolbar.setLogo(R.mipmap.ic_launcher);
        }

        if (savedInstanceState == null) {
            //Display the MainActivityFragment
            getFragmentManager().beginTransaction().replace(R.id.container, new ListFragment()).commit();
        }

        ListFragment listFragment = (ListFragment) getFragmentManager().findFragmentById(R.id.list);
        if (listFragment == null) {
            getFragmentManager().beginTransaction().replace(R.id.container, new ListFragment()).commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Transition to FormActivity
                    Intent formIntent = new Intent(ListActivity.this, FormActivity.class);
                    ListActivity.this.startActivity(formIntent);
                }
            });
        }

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference(mUser);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println(TAG + "Entered the Broadcast Receiver.");
                groceryArrayList = (ArrayList<Grocery>) intent.getSerializableExtra(CustomIntentService.EXTRA_LIST);
                Log.d(TAG, "The size of the groceryArrayList is " + groceryArrayList.size());
                ListView listView = (ListView) findViewById(R.id.list);
                CustomAdapter customAdapter = new CustomAdapter(ListActivity.this, groceryArrayList);
                listView.setAdapter(customAdapter);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            //Transition to FormActivity
            Intent formIntent = new Intent(ListActivity.this, FormActivity.class);
            ListActivity.this.startActivity(formIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void viewItem(int position) throws IOException {
        System.out.println(TAG + "viewItem.");
        groceryArrayList = FirebaseHelper.readFromFireBaseDatabase(getApplicationContext());
        currentPosition = position;
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(DetailActivity.ITEM_EXTRA, groceryArrayList.get(position));
        detailIntent.putExtra(DetailActivity.POSITION, position);
        detailIntent.putExtra(DetailActivity.LIST_EXTRA, groceryArrayList);
        startActivity(detailIntent);

    }

    @Override
    protected void onStart(){
        super .onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(FirebaseHelper.RESULT));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        // Quit if back is pressed
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }else if (keyCode == KeyEvent.KEYCODE_HOME){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
