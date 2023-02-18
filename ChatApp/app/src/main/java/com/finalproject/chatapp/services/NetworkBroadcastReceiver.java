package com.finalproject.chatapp.services;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.finalproject.chatapp.fragments.NetworkStatus;

public class NetworkBroadcastReceiver extends BroadcastReceiver {
    private final FragmentManager fragmentManager;
    private final int containerId;
    private final Context context;
    private final View[] viewsToHide;

    private boolean networkOff, hasRegistered = false;

    public NetworkBroadcastReceiver(FragmentManager fragmentManager, int containerId, Context context, View... viewsToHide)
    {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
        this.context = context;
        handleNetworkChange(context);
        this.viewsToHide = viewsToHide;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        handleNetworkChange(context);
    }

    private void handleNetworkChange(Context context) {
        setNetworkStatus(context);
        if(networkOff)
        {
            openNetworkOffFragment();
            Toast.makeText(context, "Network off!", Toast.LENGTH_LONG).show();
        }
        else
        {
            closeNetworkFragment();
            //Toast.makeText(context, "Network on!", Toast.LENGTH_LONG).show();
        }
    }

    private void setNetworkStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if(info != null && info.isConnected()) // TODO: Load a fragment
        {
            networkOff = false;
        }
        else
        {
            networkOff = true;
        }
    }

    public void register()
    {
        if(hasRegistered)
            context.unregisterReceiver(this);
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(this, filter, Manifest.permission.ACCESS_NETWORK_STATE,null);
        hasRegistered = true;
    }

    public void unregister()
    {
        context.unregisterReceiver(this);
        hasRegistered = false;
    }

    private void openNetworkOffFragment()
    {
        if(!networkOff) return;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment f = NetworkStatus.newInstance();
        ft.add(containerId, f).addToBackStack("network").commit();

        if(viewsToHide != null)
            for(View v : viewsToHide) v.setVisibility(View.GONE);
    }

    private void closeNetworkFragment()
    {
        if(networkOff) return;
        fragmentManager.popBackStack();
        networkOff = false;

        if(viewsToHide != null)
            for(View v : viewsToHide) v.setVisibility(View.VISIBLE);
    }

    public boolean isNetworkOff() {
        return networkOff;
    }
}
