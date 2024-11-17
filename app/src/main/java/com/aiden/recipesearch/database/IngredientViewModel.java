package com.aiden.recipesearch.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class IngredientViewModel extends AndroidViewModel {
    private IngredientRepository repository;
    private final LiveData<List<Ingredient>> allIngredients;
    private final Executor executor = Executors.newSingleThreadExecutor();
    public IngredientViewModel(Application application){
        super(application);
        repository = new IngredientRepository(application);
        allIngredients = repository.getAllIngredients();
    }

    public LiveData<List<Ingredient>> getAllIngredients() {return  allIngredients;}
    public void insert(Ingredient ingredient) {repository.insert(ingredient);}
    public void delete(String ingredient){
        executor.execute(() -> {
            repository.delete(ingredient);
        });
    }
}
