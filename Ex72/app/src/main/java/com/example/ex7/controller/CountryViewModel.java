package com.example.ex7.controller;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex7.model.Country;
import com.example.ex7.model.CountryXMLParser;

import java.util.ArrayList;

public class CountryViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Country>> countries;
    private MutableLiveData<Integer> itemSelected;

    public CountryViewModel(@NonNull Application application) {
        super(application);
        countries = new MutableLiveData<>();
        itemSelected = new MutableLiveData<>();
        ArrayList<Country> list = CountryXMLParser.parseCountries(application.getApplicationContext());
        getCountries().setValue(list);
        getItemSelected().setValue(RecyclerView.NO_POSITION);
    }

    public void removeCountry(int index) {
        ArrayList<Country> list = getCountries().getValue();
        list.remove(index);
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
