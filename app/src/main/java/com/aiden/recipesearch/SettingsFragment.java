package com.aiden.recipesearch;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.aiden.recipesearch.database.IngredientViewModel;

public class SettingsFragment extends PreferenceFragmentCompat {
    private IngredientViewModel ingredientViewModel;
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        ingredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);

        Preference deleteIngredients = findPreference("deleteIngredients");
        if(deleteIngredients != null){
            deleteIngredients.setOnPreferenceClickListener((preference -> {
                Toast toast = Toast.makeText(getContext(), R.string.deleted_all_ingredients, Toast.LENGTH_SHORT);
                ingredientViewModel.deleteAll();
                toast.show();
                return true;
            }));
        }
    }
}
