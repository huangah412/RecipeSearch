package com.aiden.recipesearch.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.aiden.recipesearch.R;
import com.aiden.recipesearch.database.Ingredient;
import com.aiden.recipesearch.database.IngredientViewModel;
import com.aiden.recipesearch.util.StringUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;
import java.util.Objects;

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
            public void onEditIngredient(String ingredientName, View view) {
                //get ingredient and values, setup dialog layout
                Ingredient editIngredient = viewModel.getIngredient(ingredientName);
                int id = editIngredient.id;
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_layout, null);
                TextInputEditText ingredient = dialogView.findViewById(R.id.editTextIngredient);
                TextInputEditText amount = dialogView.findViewById(R.id.editTextAmount);


                AlertDialog dialog = new MaterialAlertDialogBuilder(view.getContext())
                    .setTitle(view.getResources().getString(R.string.edit_ingredient_dialog_title))
                    .setView(dialogView)
                    .setNegativeButton(R.string.add_ingredient_dialog_cancel, (dialog1, which)-> dialog1.cancel())
                    .setPositiveButton(R.string.menu_edit_ingredient, null)
                    .create();
                ingredient.setText(editIngredient.ingredient);
                if(editIngredient.amount != -1){ // ensures input field is blank if amount = -1 so user cannot input negative numbers
                    amount.setText(String.format(Locale.getDefault(),"%d", editIngredient.amount));
                }
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    boolean error = false;
                    //get input from dialog
                    String ingredientString = Objects.requireNonNull(ingredient.getText()).toString().trim();
                    String amountString = Objects.requireNonNull(amount.getText()).toString().trim();

                    // ensure ingredient name is inputted
                    if(ingredientString.isEmpty()) {
                        ingredient.setError("Input ingredient");
                        error = true;
                    } else{
                        ingredient.setError(null);
                    }

                    if(amountString.isEmpty()){
                        amountString = "-1";
                    }

                    if(!error){
                        ingredientString = ingredientString.trim();
                        ingredientString = ingredientString.replaceAll("  +", " ");
                        ingredientString = StringUtils.toTitleCase(ingredientString);

                        viewModel.update(id, ingredientString, Integer.parseInt(amountString)); //update database with new name and amount
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onDeleteIngredient(String ingredient) {
                viewModel.delete(ingredient);
            }
        });

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        if(position == getCurrentList().size()-1){
            params.bottomMargin = 150; //large bottom margin for last item so menu isn't blocked by FAB
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
