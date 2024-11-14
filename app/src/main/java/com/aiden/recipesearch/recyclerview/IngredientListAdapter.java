package com.aiden.recipesearch.recyclerview;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.aiden.recipesearch.database.Ingredient;

public class IngredientListAdapter extends ListAdapter<Ingredient, IngredientViewHolder> {
    public IngredientListAdapter(@NonNull DiffUtil.ItemCallback<Ingredient> diffCallback){
        super(diffCallback);
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return IngredientViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position){
        Ingredient current = getItem(position);
        holder.bind(current.ingredient);
    }

    public static class IngredientDiff extends DiffUtil.ItemCallback<Ingredient>{
        @Override
        public boolean areItemsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem){
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem) {
            return oldItem.ingredient.equals(newItem.ingredient);
        }

    }
}
