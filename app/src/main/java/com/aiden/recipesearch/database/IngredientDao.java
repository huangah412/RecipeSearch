package com.aiden.recipesearch.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ingredient ingredient);

    @Query("DELETE FROM ingredient_table")
    void deleteAll();

    @Query("SELECT * FROM ingredient_table WHERE ingredient = :name")
    Ingredient getIngredient(String name);

    /*@Query("SELECT amount FROM ingredient_table WHERE ingredient = :name")
    int getAmount(String name);*/

    @Query("SELECT * FROM ingredient_table ORDER BY ingredient ASC")
    LiveData<List<Ingredient>> getAlphabetizedIngredients();

    @Query("SELECT * FROM ingredient_table ORDER BY ingredient DESC")
    LiveData<List<Ingredient>> getAlphabetizedIngredientsDescending();

    @Query("SELECT * FROM ingredient_table ORDER BY amount ASC")
    LiveData<List<Ingredient>> getAmountAscending();

    @Query("SELECT * FROM ingredient_table ORDER BY amount DESC")
    LiveData<List<Ingredient>> getAmountDescending();

    //TODO: get IDs query, find out what order would get newest first (its last first but probably not needed)
}
