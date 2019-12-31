package com.example.kennedy.walkwithmeapp;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView mMainNav;
    private android.support.v4.app.Fragment fragment;

    // keys for reading data from SharedPreferences
    public static final String WEIGHT = "pref_weight";

    private boolean phoneDevice = true; // used to force portrait mode
    private boolean preferencesChanged = true; // did preferences change?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // set default values in the app's SharedPreferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        // register listener for SharedPreferences changes
        PreferenceManager.getDefaultSharedPreferences(this).
                registerOnSharedPreferenceChangeListener(
                        preferencesChangeListener);
        // determine screen size
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        if (phoneDevice) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        // navigation bar code
        mMainNav =findViewById(R.id.navigationBar);
        mMainNav.setOnNavigationItemSelectedListener(this);
        loadFragment(new MainActivityFragment());





    }

    private boolean loadFragment(android.support.v4.app.Fragment fragment) {
        if(fragment  != null){
        android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mMainframe,fragment).commit();
        return true;
        }
        return false;

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent preferencesIntent = new Intent(this,SettingsActivity.class);
        startActivity(preferencesIntent);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new MainActivityFragment();
                loadFragment(fragment);
                return true;
            case R.id.nav_goal:
                fragment = new GoalFragment();
                loadFragment(fragment);
                return true;
            case R.id.nav_account:
                fragment = new AccountFragment();
                loadFragment(fragment);
                return true;
            case R.id.nav_cam:
                fragment = new CameraFragment();
                loadFragment(fragment);
                return true;
            default:
                return loadFragment(fragment);
        }

    }
        @Override
    protected void onStart(){
        super.onStart();
            if (preferencesChanged) {
                // now that the default preferences have been set,
                // initialize MainActivityFragment and start the quiz
                MainActivityFragment walkAppFragment = (MainActivityFragment)
                        getSupportFragmentManager().findFragmentById(
                                R.id.mMainframe);
//                walkAppFragment.updateGuessRows(
//                        PreferenceManager.getDefaultSharedPreferences(this));
//                walkAppFragment.updateRegions(
//                        PreferenceManager.getDefaultSharedPreferences(this));
//
                preferencesChanged = false;
            }


    }

    @Override
    protected void onStop(){
        super.onStop();

    }



    private OnSharedPreferenceChangeListener preferencesChangeListener =
            new OnSharedPreferenceChangeListener(){
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    preferencesChanged = true;
                    MainActivityFragment mfragment = (MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.mFragment);

                    if (key.equals(WEIGHT)){}
                }
            };

}
