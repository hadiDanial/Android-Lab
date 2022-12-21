package com.example.ex7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainer;

import android.content.res.Configuration;
import android.os.Bundle;

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
}