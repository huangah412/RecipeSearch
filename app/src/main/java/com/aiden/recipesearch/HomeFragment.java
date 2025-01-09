package com.aiden.recipesearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.aiden.recipesearch.database.IngredientViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private IngredientViewModel viewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button searchButton = view.findViewById(R.id.searchButton);
        Button searchButtonRandom = view.findViewById(R.id.searchButtonRandom);

        searchButton.setOnClickListener(this::searchRecipe);
        searchButtonRandom.setOnClickListener(this::searchRandom);

        viewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        return view;
    }

    /**
     * Starts the search activity
     * @param view button clicked on
     */
    public void searchRecipe(View view){
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivity(intent);
    }

    /**
     * Opens a Google web page with three random ingredients from the database
     * @param view button clicked on
     */
    public void searchRandom(View view){

        List<String> ingredients = viewModel.getIngredientNames();

        final int MIN_INGREDIENTS = 3;
        if (ingredients.size() <= MIN_INGREDIENTS) {
            Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.random_search_input_more), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        StringBuilder stringBuilder = new StringBuilder("https://www.google.com/search?q=recipe+with+");
        for(int i = 0; i < MIN_INGREDIENTS; i++){
            int selected = (int)(Math.random()*ingredients.size());
            String ingredient = ingredients.remove(selected);
            stringBuilder.append(String.format("$%s$+", ingredient));
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuilder.toString().replaceAll("\\$","\"")));
        startActivity(intent);
    }
}