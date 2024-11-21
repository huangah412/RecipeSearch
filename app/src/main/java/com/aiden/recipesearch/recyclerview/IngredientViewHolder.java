package com.aiden.recipesearch.recyclerview;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aiden.recipesearch.R;

public class IngredientViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener{
    private final TextView ingredientItemView;
    private final TextView amountItemView;
    private IngredientActionListener actionListener;

    private IngredientViewHolder(View itemView){
        super(itemView);
        ingredientItemView = itemView.findViewById(R.id.ingredientName);
        amountItemView = itemView.findViewById(R.id.amount);
        Button moreOptionsButton = itemView.findViewById(R.id.moreOptions);
        moreOptionsButton.setOnClickListener(v -> {
            showMenu(v);
        });
    }

    public void bind(String text, int amount, IngredientActionListener actionListener){
        ingredientItemView.setText(text);
        this.actionListener = actionListener;

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

    public void showMenu(View view){
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.ingredient_more_options_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (actionListener == null) return false;

        int itemId = item.getItemId();

        if (itemId == R.id.menuEditIngredient) {
            actionListener.onEditIngredient(ingredientItemView.getText().toString(), itemView);
            return true;
        } else if (itemId == R.id.menuDeleteIngredient) {
            actionListener.onDeleteIngredient(ingredientItemView.getText().toString(), itemView);
            return true;
        } else {
            return false;
        }
    }
    public interface IngredientActionListener {
        void onEditIngredient(String ingredient, View view);
        void onDeleteIngredient(String ingredient, View view);
    }
}
