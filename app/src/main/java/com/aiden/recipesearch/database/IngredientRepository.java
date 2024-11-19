package com.aiden.recipesearch.database;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.aiden.recipesearch.R;

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
    void insert(Ingredient ingredient, Context context){
        IngredientRoomDatabase.databaseWriteExecutor.execute(() -> {
            String itemName = ingredient.ingredient;
            Ingredient item = ingredientDao.getIngredient(itemName);
            if(item == null) { //checks for duplicate
                ingredientDao.insert(ingredient);
            } else {
                if(ingredient.amount == -1 && item.amount == -1){ //both input and target unspecified: notify user, does nothing else
                    Looper.prepare();
                    Toast toast = Toast.makeText(context, R.string.error_amount_unspecified, Toast.LENGTH_LONG);
                    toast.show();
                } else if(item.amount != -1 && ingredient.amount != -1) { //if input and target amount inputted
                    ingredient.amount += item.amount; //adds amounts together
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
        /* Use this code if getting data from database in UI thread
         * currently doesn't have any usages in main thread
        Ingredient output;
        try {
            output = executor.submit(() -> ingredientDao.getIngredient(name)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }*/
        return ingredientDao.getIngredient(name);
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
