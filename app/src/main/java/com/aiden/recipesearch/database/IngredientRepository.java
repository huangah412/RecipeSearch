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
    private final IngredientDao ingredientDao;
    private final LiveData<List<Ingredient>> allIngredients;
    private final ExecutorService executor;

    IngredientRepository(Application application){
        IngredientRoomDatabase database = IngredientRoomDatabase.getDatabase(application);
        ingredientDao = database.ingredientDao();
        allIngredients = ingredientDao.getAlphabetizedIngredients();
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     *  Gets all the ingredients that are in the database
     * @return LiveData of a list that contains all of the Ingredients in the database
     */
    LiveData<List<Ingredient>> getAllIngredients(){
        return allIngredients;
    }

    public List<Ingredient> getIngredients(){
        try {
            return executor.submit(ingredientDao::getIngredients).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserts an ingredient: if there is a duplicate, it combines the amount, if there is no amount it replaces with the amount specified, if the amount is already unspecified it notifies the user.
     * @param ingredient Ingredient to insert
     * @param context passed to allow for messages to be shown
     */
    //Combines amount if there is duplicate ingredient, if no amount replaces with amount specified
    void insert(Ingredient ingredient, Context context){
        IngredientRoomDatabase.databaseWriteExecutor.execute(() -> {
            String itemName = ingredient.ingredient;
            Ingredient item = ingredientDao.getIngredient(itemName);
            if(item == null) { //checks for duplicate
                ingredientDao.insert(ingredient);
            } else {
                if(ingredient.amount == -1 && item.amount == -1){ //both input and target unspecified: notify user, does nothing else
                    Looper.prepare(); // allows messages to be shown in the thread
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

    /**
     * Deletes all of the items in the database
     */
    void deleteAll(){
        executor.execute(ingredientDao::deleteAll);
    }

    /**
     * Deletes specific ingredient with name <code>ingredient</code>
     * @param ingredient the ingredient to delete
     */
    void delete(String ingredient){
        executor.execute(() -> ingredientDao.delete(ingredient));
    }

    /**
     * Gets an ingredient with specific name from database
     * @param name name of ingredient to get
     * @return ingredient in database with name <code>name</code>
     */
    public Ingredient getIngredient(String name) {
        Ingredient output;
        try {
            output = executor.submit(() -> ingredientDao.getIngredient(name)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    /**
     * Gets all the ingredient names in the database
     * @return List of all the names of ingredients
     */
    public List<String> getIngredientNames(){
        List<String> output;
        try{
            output = executor.submit(ingredientDao::getIngredientNames).get(); //method reference
        } catch (InterruptedException | ExecutionException e){
            throw new RuntimeException(e);
        }
        return output;
    }

    /**
     * Updates an ingredient in the database with a new name and amount
     * @param id id of ingredient to update
     * @param ingredient new name
     * @param amount new amount
     */
    void update(int id, String ingredient, int amount){
        executor.execute(() -> ingredientDao.update(id, ingredient, amount));
    }
}
