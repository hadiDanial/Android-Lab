package com.example.ex7.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex7.R;
import com.example.ex7.model.Country;
import com.example.ex7.model.CountryXMLParser;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>{

    private ArrayList<Country> countries;
    public CountryAdapter(Context context)
    {
        countries = CountryXMLParser.parseCountries(context);
    }

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

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int index = getAdapterPosition();
                    Log.println(Log.INFO, "EX7", "Clicked on " + index);
                    countries.remove(index);

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
