//Heather Wolford
//CPMD Week 1
//August 2016

package com.java2.heatherwolford.cpmdweek1;

import android.os.Bundle;

public class CredentialsPreferenceFragment extends android.preference.PreferenceFragment {

    public static final String TAG = "CredentialsPreferenceFragment";
    public static CredentialsPreferenceActivity preferencesActivity = new CredentialsPreferenceActivity();

    //Preferences
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String STAY_LOGGED_IN = "STAY_LOGGED_IN";

    public static CredentialsPreferenceFragment newInstance(CredentialsPreferenceActivity activity){
        preferencesActivity = activity;
        return new CredentialsPreferenceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_credentials_preference);
    }
}