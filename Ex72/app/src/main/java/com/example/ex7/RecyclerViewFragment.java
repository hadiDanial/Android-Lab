package com.example.ex7;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ex7.controller.CountryAdapter;
import com.example.ex7.controller.CountryViewModel;

public class RecyclerViewFragment extends Fragment {

    public RecyclerViewFragment() {}

    public static RecyclerViewFragment newInstance() {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Lookup the recyclerview in activity layout
        RecyclerView rvCountries = (RecyclerView) view.findViewById(R.id.countryRecycler);
        CountryViewModel viewModel = new ViewModelProvider(getActivity()).get(CountryViewModel.class);
        CountryAdapter adapter = new CountryAdapter(view.getContext(), viewModel, getParentFragmentManager());
        // Attach the adapter to the recyclerview to populate items
        rvCountries.setAdapter(adapter);

        // Set layout manager to position the items
        rvCountries.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // That's all!
    }
}