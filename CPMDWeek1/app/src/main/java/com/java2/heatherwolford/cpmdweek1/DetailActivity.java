//Heather Wolford
//CPMD Week 1
//August 2016

package com.java2.heatherwolford.cpmdweek1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class DetailActivity extends AppCompatActivity implements DetailFragment.DetailListener{

    private final String TAG = "DetailActivity: ";
    public static final String ITEM_EXTRA = "STRAIN_ITEM_EXTRA";
    public static final String POSITION = "POSITION";
    public static final String LIST_EXTRA = "ARRAY_LIST_EXTRA";
    private Grocery grocery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(TAG + "Entered the onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setLogo(R.mipmap.ic_launcher);

        if (savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new DetailFragment())
                    .commit();
        }

        Intent detailIntent = getIntent();
        if (detailIntent != null){
            grocery = (Grocery) detailIntent.getSerializableExtra(ITEM_EXTRA);}
    }

    //Adds item (add) to action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println(TAG + "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            //Todo: Add delete functionality
            //Update the list
            updateList();
        }else{
            Intent listIntent = new Intent(this, ListActivity.class);
            startActivity(listIntent);
            finish();
        }
        return true;
    }

    public void updateList(){
        //Todo: Update the list
    }

    @Override
    protected void onStart(){
        super .onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
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

    @Override
    public Grocery getObject() {
        return grocery;
    }
}
