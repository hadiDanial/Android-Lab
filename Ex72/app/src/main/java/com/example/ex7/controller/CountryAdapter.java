package com.example.ex7.controller;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex7.DetailsFragment;
import com.example.ex7.R;
import com.example.ex7.model.Country;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>{

    private final CountryViewModel viewModel;
    private final FragmentManager fragmentManager;
    private ArrayList<Country> countries;
    private int selectedCountry;

    private Observer<ArrayList<Country>> countriesObserver = new Observer<ArrayList<Country>>() {
        @Override
        public void onChanged(ArrayList<Country> countriesList) {
            countries = countriesList;
            notifyDataSetChanged();
        }
    };
    private Observer<Integer> selectedCountryObservable = new Observer<Integer>() {
        @Override
        public void onChanged(Integer selected) {
            selectedCountry = selected;
            notifyDataSetChanged();
        }
    };

    public CountryAdapter(Context context, CountryViewModel viewModel, FragmentManager fragmentManager)
    {
        //countries = CountryXMLParser.parseCountries(context);t
        this.fragmentManager = fragmentManager;
        this.viewModel = viewModel;
        this.viewModel.getCountries().observe((LifecycleOwner) context, countriesObserver);
        this.viewModel.getItemSelected().observe((LifecycleOwner) context, selectedCountryObservable);
    }
    public interface CountryInterface{}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View countryView = inflater.inflate(R.layout.country_view, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(countryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setCountry(countries.get(position));
        if(position == selectedCountry)
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#9ec6b8"));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView, populationTextView;
        public ImageView countryImage;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = itemView.findViewById(R.id.countryName);
            populationTextView = itemView.findViewById(R.id.countryPopulation);
            countryImage = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.setSelected(getAdapterPosition());
                        DetailsFragment fragB;
                        Context context =itemView.getContext();
                        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                        {

                            fragmentManager.beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.fragmentContainerView, DetailsFragment.class, null,"FRAGB")
                                    .addToBackStack("BBB")
                                    .commit();
                            fragmentManager.executePendingTransactions();
                        }
                       // fragB = (DetailsFragment) getSupportFragmentManager().findFragmentByTag("FRAGB");
                }
            });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int index = getAdapterPosition();
                    Log.println(Log.INFO, "EX7", "Clicked on " + index);
                    if(index == selectedCountry)
                        viewModel.setSelected(RecyclerView.NO_POSITION);
                    else if(selectedCountry > index)
                        viewModel.setSelected(selectedCountry - 1);
                    viewModel.removeCountry(index);
                    notifyItemRemoved(index);
                    return true;
                }
            });
        }

        public void setCountry(Country country) {
            nameTextView.setText(country.getName());
            populationTextView.setText(country.getShorty());
            Context context = countryImage.getContext();
            int imageId = context.getResources().getIdentifier(country.getFlag(), "drawable",
                    context.getPackageName());
            countryImage.setImageResource(imageId);
        }
    }
}
