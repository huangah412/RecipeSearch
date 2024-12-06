package com.aiden.recipesearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aiden.recipesearch.database.IngredientViewModel;
import com.aiden.recipesearch.recyclerview.ChooseItemListAdapter;
import com.aiden.recipesearch.recyclerview.IngredientListAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private IngredientViewModel viewModel;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(IngredientViewModel.class);

        //setup recyclerView
        recyclerView = findViewById(R.id.chooseItems);
        final ChooseItemListAdapter adapter = new ChooseItemListAdapter(new IngredientListAdapter.IngredientDiff(), viewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getAllIngredients().observe(this, ingredients -> {
            adapter.submitList(ingredients);
        });
    }

    public void searchRecipe(View view){
        ArrayList<String> selectedIngredients = new ArrayList<>();

        // select items in recyclerView with checkbox checked, adds to list
        for(int i = 0; i < recyclerView.getHeight(); i++){
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);

            if(holder != null){
                CheckBox checkBox = holder.itemView.findViewById(R.id.selectIngredient);
                if(checkBox.isChecked()){
                    selectedIngredients.add(checkBox.getText().toString());
                }
            }
        }

        //create url to search google
        StringBuilder stringBuilder = new StringBuilder("https://www.google.com/search?q=$recipe$+");
        for(String ingredient : selectedIngredients) {
            stringBuilder.append(String.format("$%s$+", ingredient));
        }

        //display url
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuilder.toString().replaceAll("\\$", "\"")));
        startActivity(intent);

        Log.d("Search", selectedIngredients.toString());
    }
}