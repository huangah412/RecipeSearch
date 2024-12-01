package com.aiden.recipesearch.recyclerview;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.aiden.recipesearch.database.Ingredient;
import com.aiden.recipesearch.database.IngredientViewModel;

public class ChooseItemListAdapter extends ListAdapter<Ingredient, ChooseItemViewHolder> {
    private final IngredientViewModel viewModel;
    public ChooseItemListAdapter(@NonNull DiffUtil.ItemCallback<Ingredient> diffCallback, IngredientViewModel viewModel){
        super(diffCallback);
        this.viewModel = viewModel;
    }

    @Override
    public ChooseItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return ChooseItemViewHolder.create(parent);
    }

    public void onBindViewHolder(ChooseItemViewHolder holder, int position){
        Ingredient current = getItem(position);
        holder.bind(current.ingredient);
    }
}
