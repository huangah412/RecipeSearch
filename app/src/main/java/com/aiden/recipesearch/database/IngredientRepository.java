package com.aiden.recipesearch.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IngredientRepository {
    private IngredientDao ingredientDao;
    private LiveData<List<Ingredient>> allIngredients;
    private final ExecutorService executor;

    IngredientRepository(Application application){
        IngredientRoomDatabase database = IngredientRoomDatabase.getDatabase(application);
        ingredientDao = database.ingredientDao();
        allIngredients = ingredientDao.getAlphabetizedIngredients();
        executor = Executors.newSingleThreadExecutor();
    }

    LiveData<List<Ingredient>> getAllIngredients(){
        return allIngredients;
    }

    //Combines amount if there is duplicate ingredient, if no amount replaces with amount specified
    void insert(Ingredient ingredient){
        IngredientRoomDatabase.databaseWriteExecutor.execute(() -> {
            String itemName = ingredient.ingredient;
            Ingredient item = ingredientDao.getIngredient(itemName);
            if(item == null) { //checks for duplicate
                ingredientDao.insert(ingredient);
            } else {
                if(item.amount != -1 && ingredient.amount != -1) { //if amount inputted
                    ingredient.amount += item.amount; //adds amount
                }
                ingredient.id = item.id; // change input id to id in database so it replaces properly
                ingredientDao.insert(ingredient);
            }
        });
    }

    void delete(String ingredient){
        ingredientDao.delete(ingredient);
    }

    public Ingredient getIngredient(String name) {
        Ingredient output;
        try {
            output = executor.submit(() -> ingredientDao.getIngredient(name)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
