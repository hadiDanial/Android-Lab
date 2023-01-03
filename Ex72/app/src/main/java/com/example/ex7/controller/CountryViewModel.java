package com.example.ex7.controller;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex7.model.Country;
import com.example.ex7.model.CountryXMLParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class CountryViewModel extends AndroidViewModel {
    private final Application application;
    private final String fileName = "RemovedCountries.rc";
    private MutableLiveData<ArrayList<Country>> countries;
    private MutableLiveData<Integer> itemSelected;
    private ArrayList<Country> originalList, currentList;
    private int originalSize;
    private boolean[] removed;

    public CountryViewModel(@NonNull Application application) {
        super(application);
        countries = new MutableLiveData<>();
        itemSelected = new MutableLiveData<>();
        this.application = application;
        originalList = CountryXMLParser.parseCountries(application.getApplicationContext());
        originalSize = originalList.size();
        currentList = (ArrayList<Country>) originalList.clone();
        removed = new boolean[originalSize];
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(application);
        boolean remember = sharedPref.getBoolean("rememberRemoved", false);

        if (remember) {
            readFile(sharedPref);
        } else {
            resetFile(sharedPref);
        }
        getCountries().setValue(currentList);
        getItemSelected().setValue(RecyclerView.NO_POSITION);
    }

    private void readFile(SharedPreferences sharedPref) {
        // SP
        if (sharedPref.getBoolean("useSP", true)) {
            for (int i = originalList.size() - 1; i >= 0; i--) {
                if (sharedPref.getBoolean(originalList.get(i).getName(), false)) {
                    removed[i] = true;
                    currentList.remove(i);
                }
            }
        }
        // RAW FILE
        else {
            ByteBuffer bf = readSavedRAWFile();
            if (bf != null)
                for (int i = originalList.size() - 1; i >= 0; i--) {
                    if (bf.get(i) == 1) {
                        removed[i] = true;
                        currentList.remove(i);
                    } else
                        removed[i] = false;
                }
        }
    }

    private ByteBuffer readSavedRAWFile() {
        FileInputStream fin = null;
        try {
            fin = application.openFileInput(fileName);
            ByteBuffer bf = ByteBuffer.allocate(originalSize);
            fin.read(bf.array());
            fin.close();
            return bf;
        } catch (FileNotFoundException e) {
            Log.i("EX8", "No file found");
            return null;
        } catch (IOException e) {
            Log.i("EX8", "Couldn't read file");
            return null;
        }
    }

    private void writeToFile(Country removedCountry) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(application);
        for (int i = 0; i < originalSize; i++)
            if (originalList.get(i).equals(removedCountry))
                removed[i] = true;
        // SP
        if (sharedPref.getBoolean("useSP", true)) {

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(removedCountry.getName(), true);
            editor.apply();
        }
        // RAW
        else {

            try {
                FileOutputStream fos = application.openFileOutput(fileName, Context.MODE_PRIVATE);
                byte[] bb = new byte[originalSize];
                //
                for (int i = 0; i < originalSize; i++) {
                    bb[i] = (byte) (removed[i] ? 1 : 0);
                }
                Log.i("EX8", bb.toString());
                fos.write(bb);
                fos.close();
            } catch (IOException e) {
                Log.e("EX8", "Failed to save");
            }
        }
    }

    private void resetFile(SharedPreferences sharedPref) {
        SharedPreferences.Editor editor = sharedPref.edit();
        // SP
        for (int i = originalList.size() - 1; i >= 0; i--) {
            editor.putBoolean(originalList.get(i).getName(), false);
        }
        editor.apply();
        // RAW
        try {
            FileOutputStream fos = application.openFileOutput(fileName, Context.MODE_PRIVATE);
            byte[] bb = new byte[originalSize];
            //
            for (int i = 0; i < originalSize; i++) {
                bb[i] = (byte) 0;
            }
            fos.write(bb);
            fos.close();
        } catch (IOException e) {
            Log.e("EX8", "Failed to reset");
        }
    }

    public void removeCountry(int index) {
        ArrayList<Country> list = getCountries().getValue();
        Country removedCountry = list.remove(index);
        writeToFile(removedCountry);
        getCountries().setValue(list);
    }


    public void setSelected(int adapterPosition) {
        getItemSelected().setValue(adapterPosition);
    }

    public MutableLiveData<ArrayList<Country>> getCountries() {
        return countries;
    }

    public MutableLiveData<Integer> getItemSelected() {
        return itemSelected;
    }
}
