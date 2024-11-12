package com.aiden.recipesearch.database;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class IngredientRepository {
    private IngredientDao ingredientDao;
    private LiveData<List<Ingredient>> allIngredients;

    IngredientRepository(Application application){
        IngredientRoomDatabase database = IngredientRoomDatabase.getDatabase(application);
        ingredientDao = database.ingredientDao();
        allIngredients = ingredientDao.getAlphabetizedIngredients();
    }

    LiveData<List<Ingredient>> getAllIngredients(){
        return allIngredients;
    }

    //Combines amount if there is duplicate ingredient, if no amount replaces with amount specified
    void insert(Ingredient ingredient){
        IngredientRoomDatabase.databaseWriteExecutor.execute(() -> {
            String item = ingredient.ingredient;
            if(ingredientDao.getIngredient(item) == null) { //checks for duplicate
                ingredientDao.insert(ingredient);
            } else {
                int amount = ingredientDao.getAmount(item);
                if(amount != -1) { //if amount inputted
                    ingredient.amount += amount; //adds amount
                }
                ingredientDao.insert(ingredient);
            }
        });
    }
}
