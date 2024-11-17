package com.aiden.recipesearch.recyclerview;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.aiden.recipesearch.database.Ingredient;
import com.aiden.recipesearch.database.IngredientViewModel;

public class IngredientListAdapter extends ListAdapter<Ingredient, IngredientViewHolder> {
    private final IngredientViewModel viewModel;
    public IngredientListAdapter(@NonNull DiffUtil.ItemCallback<Ingredient> diffCallback, IngredientViewModel viewModel){
        super(diffCallback);
        this.viewModel = viewModel;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return IngredientViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position){
        Ingredient current = getItem(position);
        holder.bind(current.ingredient, current.amount, new IngredientViewHolder.IngredientActionListener() {
            @Override
            public void onEditIngredient(String ingredient) {

            }

            @Override
            public void onDeleteIngredient(String ingredient) {
                viewModel.delete(ingredient);
            }
        });

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        if(position == getCurrentList().size()-1){
            params.bottomMargin = 150;
        } else{
            params.bottomMargin = 0;
        }
        holder.itemView.setLayoutParams(params);
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
