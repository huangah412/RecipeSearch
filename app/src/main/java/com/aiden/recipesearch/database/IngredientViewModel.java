package com.aiden.recipesearch.database;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {
    private IngredientRepository repository;
    private final LiveData<List<Ingredient>> allIngredients;
    public IngredientViewModel(Application application){
        super(application);
        repository = new IngredientRepository(application);
        allIngredients = repository.getAllIngredients();
    }

    public LiveData<List<Ingredient>> getAllIngredients() {return  allIngredients;}
    public void insert(Ingredient ingredient, Context context) {repository.insert(ingredient, context);}
    public void delete(String ingredient){
        repository.delete(ingredient);
    }

    public void update(int id, String ingredient, int amount){
        repository.update(id, ingredient, amount);
    }
    public Ingredient getIngredient(String name) {
        return repository.getIngredient(name);
    }
}
