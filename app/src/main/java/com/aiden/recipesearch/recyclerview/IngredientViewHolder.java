package com.aiden.recipesearch.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aiden.recipesearch.R;

public class IngredientViewHolder extends RecyclerView.ViewHolder {
    private final TextView ingredientItemView;
    private final TextView amountItemView;

    private IngredientViewHolder(View itemView){
        super(itemView);
        ingredientItemView = itemView.findViewById(R.id.ingredientName);
        amountItemView = itemView.findViewById(R.id.amount);
    }

    public void bind(String text, int amount){
        ingredientItemView.setText(text);

        String amountText;
        if(amount == -1){
            amountText = "N/A";
        }
        else{
            amountText = Integer.toString(amount);
        }
        amountItemView.setText(amountText);
    }

    static IngredientViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new IngredientViewHolder(view);
    }
}
