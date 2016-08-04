//Heather Wolford
//CPMD Week 1
//August 2016

package com.java2.heatherwolford.cpmdweek1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class CredentialsPreferenceActivity extends AppCompatActivity {

    public static final String TAG = "CredentialsPreferenceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_credentials);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setLogo(R.mipmap.ic_launcher);
        }

        CredentialsPreferenceFragment fragment = (CredentialsPreferenceFragment) getFragmentManager()
                .findFragmentByTag(CredentialsPreferenceFragment.TAG);
        if (fragment == null) {
            fragment = CredentialsPreferenceFragment.newInstance(CredentialsPreferenceActivity.this);
        }
        getFragmentManager().beginTransaction().addToBackStack(CredentialsPreferenceFragment.TAG)
                .replace(R.id.container, fragment, CredentialsPreferenceFragment.TAG).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_credentials, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            System.out.println(TAG + "Entered the onOptions Item Selected - action_save ");
            SharedPreferences sharedSearchPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String email = sharedSearchPreferences.getString(CredentialsPreferenceFragment.EMAIL, null);
            String password = sharedSearchPreferences.getString(CredentialsPreferenceFragment.PASSWORD, null);
            Intent loginIntent = new Intent(CredentialsPreferenceActivity.this, LoginActivity.class);
            loginIntent.putExtra(LoginActivity.EMAIL_EXTRA, email);
            loginIntent.putExtra(LoginActivity.PASSWORD_EXTRA, password);
            setResult(RESULT_OK, loginIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Quit if back is pressed
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}