package com.aiden.recipesearch.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient_table")
public class Ingredient {
    //might want to be private
    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull
    public String ingredient;
    public int amount;

    public Ingredient(@NonNull String ingredient) {this.ingredient = ingredient;}
}
