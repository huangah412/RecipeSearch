package com.aiden.recipesearch.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aiden.recipesearch.R;

public class IngredientViewHolder extends RecyclerView.ViewHolder {
    private final TextView ingredientItemView;

    private IngredientViewHolder(View itemView){
        super(itemView);
        ingredientItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text){
        ingredientItemView.setText(text);
    }

    static IngredientViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new IngredientViewHolder(view);
    }
}
