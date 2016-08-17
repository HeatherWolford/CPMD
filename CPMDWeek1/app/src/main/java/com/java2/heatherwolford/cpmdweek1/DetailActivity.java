//Heather Wolford
//CPMD Week 1
//August 2016

package com.java2.heatherwolford.cpmdweek1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

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
            deleteItem();
        }else if (id == R.id.action_save) {
            saveChange();
        }else{
            Intent listIntent = new Intent(this, ListActivity.class);
            startActivity(listIntent);
            finish();
        }
        return true;
    }

    public void deleteItem() {
        Log.d(TAG, "Entered deleteItem");
        ArrayList<Grocery> groceryArrayList = FirebaseHelper.readFromFireBaseDatabase(getApplicationContext());
        Grocery currentObject = getObject();
        for (int i=0; i< groceryArrayList.size(); i++){
            if (groceryArrayList.get(i).getItem().equals(currentObject.getItem())){
                groceryArrayList.remove(i);
            }
        }
        FirebaseHelper.addToFirebaseDatabase(getApplicationContext(), groceryArrayList);
        finish();
    }

    public void saveChange() {
        Log.d(TAG, "Entered saveChanges");
        //Remove original item
        deleteItem();
        //Add updated item
        EditText itemTxtInput = (EditText) findViewById(R.id.item_name);
        EditText qtyTxtInput = (EditText) findViewById(R.id.item_qty);
        if (itemTxtInput.getText().toString().equals("")) {
            Toast.makeText(this, "You must enter an item.", Toast.LENGTH_SHORT).show();
        }else if (!isValidInput(itemTxtInput.getText())){
            Toast.makeText(this, "Input limited to 30 characters.", Toast.LENGTH_SHORT).show();
        }else if (qtyTxtInput.getText().toString().equals("")) {
            //Note validation of number input also in XML
            Toast.makeText(this, "You must enter a quantity.", Toast.LENGTH_SHORT).show();
        }else{
            String groceryItem = itemTxtInput.getText().toString();
            Log.d(TAG, groceryItem);
            int amount = Integer.parseInt(qtyTxtInput.getText().toString());
            Log.d(TAG, String.valueOf(amount));
            Grocery grocery = new Grocery(groceryItem, amount);
            addItem(grocery);
        }
    }

    public boolean isValidInput(CharSequence target) {
        if (target.length() >= 31){
            return false;
        } else {
            return true;
        }
    }

    public void addItem(Grocery item) {
        Log.d(TAG, "Entered addItem");
        ArrayList<Grocery> groceryArrayList = FirebaseHelper.readFromFireBaseDatabase(getApplicationContext());
        groceryArrayList.add(item);
        Log.d(TAG, "The size of groceryArrayList after adding the item is now: " + groceryArrayList.size());
        FirebaseHelper.addToFirebaseDatabase(getApplicationContext(), groceryArrayList);
        finish();
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
