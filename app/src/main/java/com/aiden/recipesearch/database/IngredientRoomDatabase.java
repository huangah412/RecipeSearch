package com.aiden.recipesearch.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ingredient.class}, version = 1, exportSchema = false)
public abstract class IngredientRoomDatabase extends RoomDatabase {
    public abstract IngredientDao ingredientDao();

    private static volatile IngredientRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //singleton
    static IngredientRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (IngredientRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), IngredientRoomDatabase.class, "ingredient_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase database){
            super.onCreate(database);

            //Keep data through app restarts: comment out block (currently temporary)
            databaseWriteExecutor.execute(() -> {
                IngredientDao dao = INSTANCE.ingredientDao();
                dao.deleteAll();

                //testing
                Ingredient ingredient = new Ingredient("Apple", 5);
                dao.insert(ingredient);
                ingredient = new Ingredient("Flour", -1);
                dao.insert(ingredient);
            });
        }
    };
}
