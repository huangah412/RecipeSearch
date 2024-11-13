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
            /*
            String itemName = ingredient.ingredient;
            Ingredient item = ingredientDao.getIngredient(itemName);
            if(item == null) { //checks for duplicate
                ingredientDao.insert(ingredient);
            } else {
                if(item.amount != -1) { //if amount inputted
                    ingredient.amount += item.amount; //adds amount
                }
                ingredientDao.insert(ingredient);
            }*/
            ingredientDao.insert(ingredient);
        });
    }
}
