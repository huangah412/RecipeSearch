package com.aiden.recipesearch;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiden.recipesearch.database.Ingredient;
import com.aiden.recipesearch.database.IngredientViewModel;
import com.aiden.recipesearch.recyclerview.IngredientListAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

import com.aiden.recipesearch.util.StringUtils;


public class IngredientsFragment extends Fragment {
    private IngredientViewModel ingredientViewModel;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        FloatingActionButton fab = rootView.findViewById(R.id.floatingActionButton);

        ingredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);

        RecyclerView recyclerView = rootView.findViewById(R.id.ingredients);
        final IngredientListAdapter adapter = new IngredientListAdapter(new IngredientListAdapter.IngredientDiff(), ingredientViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MaterialToolbar materialToolbar = rootView.findViewById(R.id.materialToolbar2);
        materialToolbar.setOnMenuItemClickListener(v -> {
            AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.delete_all))
                .setMessage(getString(R.string.are_you_sure))
                .setNegativeButton(getString(R.string.add_ingredient_dialog_cancel), ((dialog1, which) -> dialog1.cancel()))
                .setPositiveButton(getString(R.string.confirm), null)
                .create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v1 -> {
                List<Ingredient> allIngredients = ingredientViewModel.getIngredients();
                ingredientViewModel.deleteAll();

                Snackbar snackbar = Snackbar.make(rootView, getString(R.string.snackbar_deleted_items), Snackbar.LENGTH_LONG).setAction(getString(R.string.snackbar_action_undo), v2 -> {
                    for(Ingredient i : allIngredients){
                        ingredientViewModel.insert(i, getContext());
                    }
                });
                dialog.dismiss();
                snackbar.show();
            });

            return false;
        });

        ingredientViewModel.getAllIngredients().observe(getViewLifecycleOwner(), adapter::submitList);

        //when FAB is pressed open dialog to input ingredient and amount
        fab.setOnClickListener(v -> {
            View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_layout,null); //get dialog layout and text input boxes
            TextInputEditText ingredient = view1.findViewById(R.id.editTextIngredient);
            TextInputEditText amount = view1.findViewById(R.id.editTextAmount);

            //Create and display dialog
            AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.add_ingredient_dialog_title))
                .setView(view1)
                .setNegativeButton(R.string.add_ingredient_dialog_cancel, (dialog1, which)-> dialog1.cancel())
                .setPositiveButton(R.string.add, null)
                .create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v1 -> {
                boolean error = false;
                //get input from dialog
                String ingredientString = Objects.requireNonNull(ingredient.getText()).toString().trim();
                String amountString = Objects.requireNonNull(amount.getText()).toString().trim();

                // ensure ingredient name is inputted
                if(ingredientString.isEmpty()) {
                    ingredient.setError(getString(R.string.error_input_ingredient));
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

                    ingredientViewModel.insert(new Ingredient(ingredientString, Integer.parseInt(amountString)), getContext());
                    dialog.dismiss();
                }
            });
        });

        return rootView;
    }


}