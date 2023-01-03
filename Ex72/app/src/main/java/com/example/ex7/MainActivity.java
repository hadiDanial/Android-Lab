package com.example.ex7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DetailsFragment fragB = (DetailsFragment) getSupportFragmentManager().findFragmentByTag("FRAGB");
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && fragB != null)
        {
            if(!getSupportFragmentManager().popBackStackImmediate())
            {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainerView, RecyclerViewFragment.class, null,"FRAGA")
                        .addToBackStack("AAA")
                        .commit();
                getSupportFragmentManager().executePendingTransactions();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.settingsMenu:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragmentContainerView, new CountryPreferences()).
                        addToBackStack(null).
                        commit();
                getSupportFragmentManager().executePendingTransactions();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}