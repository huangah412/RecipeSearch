package com.aiden.recipesearch.recyclerview;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.aiden.recipesearch.database.Ingredient;

public class ChooseItemListAdapter extends ListAdapter<Ingredient, ChooseItemViewHolder> {
    public ChooseItemListAdapter(@NonNull DiffUtil.ItemCallback<Ingredient> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public ChooseItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return ChooseItemViewHolder.create(parent);
    }

    public void onBindViewHolder(ChooseItemViewHolder holder, int position){
        Ingredient current = getItem(position);
        holder.bind(current.ingredient);
    }
}
