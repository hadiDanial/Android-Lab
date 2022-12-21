package com.example.ex7;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ex7.controller.CountryViewModel;
import com.example.ex7.model.Country;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    private TextView countryDetailsText;
    private ArrayList<Country> countries;
    private int selectedCountry;
    private CountryViewModel viewModel;

    private Observer<ArrayList<Country>> countriesObserver = new Observer<ArrayList<Country>>() {
        @Override
        public void onChanged(ArrayList<Country> countriesList) {
            countries = countriesList;
            updateSelectedCountry();
        }
    };

    private Observer<Integer> selectedCountryObservable = new Observer<Integer>() {
        @Override
        public void onChanged(Integer selected) {
            selectedCountry = selected;
            updateSelectedCountry();
        }
    };

    private void updateSelectedCountry() {
        if(selectedCountry == RecyclerView.NO_POSITION || countries.size() == 0)
        {
            countryDetailsText.setText("No country selected.");
            return;
        }
        countryDetailsText.setText(countries.get(selectedCountry).getDetails());
    }

    public DetailsFragment() {
    }

    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countryDetailsText = view.findViewById(R.id.countryDetailsText);
        viewModel = new ViewModelProvider(getActivity()).get(CountryViewModel.class);
        this.viewModel.getCountries().observe(getActivity(), countriesObserver);
        this.viewModel.getItemSelected().observe(getActivity(), selectedCountryObservable);
    }
}