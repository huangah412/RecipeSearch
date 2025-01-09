package com.aiden.recipesearch.database;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {
    private final IngredientRepository repository;
    private final LiveData<List<Ingredient>> allIngredients;
    public IngredientViewModel(Application application){
        super(application);
        repository = new IngredientRepository(application);
        allIngredients = repository.getAllIngredients();
    }

    /**
     *  Gets all the ingredients that are in the database
     * @return LiveData of a list that contains all of the Ingredients in the database
     */
    public LiveData<List<Ingredient>> getAllIngredients() {return  allIngredients;}

    public  List<Ingredient> getIngredients(){return repository.getIngredients();}

    /**
     * Inserts an ingredient: if there is a duplicate, it combines the amount, if there is no amount it replaces with the amount specified, if the amount is already unspecified it notifies the user.
     * @param ingredient Ingredient to insert
     * @param context passed to allow for messages to be shown
     */
    public void insert(Ingredient ingredient, Context context) {repository.insert(ingredient, context);}

    /**
     * Deletes specific ingredient with name <code>ingredient</code>
     * @param ingredient the ingredient to delete
     */
    public void delete(String ingredient){
        repository.delete(ingredient);
    }

    /**
     * Deletes all of the items in the database
     */
    public void deleteAll(){
        repository.deleteAll();
    }

    /**
     * Updates an ingredient in the database with a new name and amount
     * @param id id of ingredient to update
     * @param ingredient new name
     * @param amount new amount
     */
    public void update(int id, String ingredient, int amount){
        repository.update(id, ingredient, amount);
    }

    /**
     * Gets all the ingredient names in the database
     * @return List of all the names of ingredients
     */
    public List<String> getIngredientNames(){
        return repository.getIngredientNames();
    }

    /**
     * Gets an ingredient with specific name from database
     * @param name name of ingredient to get
     * @return ingredient in database with name <code>name</code>
     */
    public Ingredient getIngredient(String name) {
        return repository.getIngredient(name);
    }
}
