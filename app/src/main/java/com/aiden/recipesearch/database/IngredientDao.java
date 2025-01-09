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
    @Query("DELETE FROM ingredient_table WHERE ingredient = :name")
    void delete(String name);

    @Query("UPDATE ingredient_table SET ingredient = :name, amount = :amount WHERE id = :id")
    void update(int id, String name, int amount);

    @Query("SELECT * FROM ingredient_table WHERE ingredient = :name")
    Ingredient getIngredient(String name);

    @Query("SELECT * FROM ingredient_table ORDER BY ingredient ASC")
    LiveData<List<Ingredient>> getAlphabetizedIngredients();

    @Query("SELECT * FROM ingredient_table ORDER BY ingredient ASC")
    List<Ingredient> getIngredients();

    @Query("SELECT ingredient FROM ingredient_table ORDER BY ingredient ASC")
    List<String> getIngredientNames();
}
