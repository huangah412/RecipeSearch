package com.aiden.recipesearch;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngredientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientsFragment newInstance(String param1, String param2) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        FloatingActionButton fab = rootView.findViewById(R.id.floatingActionButton);

        //when FAB is pressed open dialog to input ingredient and amount
        fab.setOnClickListener(v -> {
            View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_layout,null);
            TextInputEditText ingredient = view1.findViewById(R.id.edit_text_ingredient);
            TextInputEditText amount = view1.findViewById(R.id.edit_text_amount);

            AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.add_ingredient_dialog_title))
                .setView(view1)
                .setNegativeButton(R.string.add_ingredient_dialog_cancel, (dialog1, which)-> dialog1.cancel())
                .setPositiveButton(R.string.add, null)
                .create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v1 -> {
                //Log.d("success", Objects.requireNonNull(input.getText()).toString());
                boolean error = false;

                if(Objects.requireNonNull(ingredient.getText()).toString().trim().isEmpty()){
                    ingredient.setError("Input ingredient");
                    error = true;
                } else{
                    ingredient.setError(null);
                }

                if(Objects.requireNonNull(amount.getText()).toString().trim().isEmpty()){
                    amount.setError("Input amount");
                    error = true;
                } else{
                    amount.setError(null);
                }

                if(!error){
                    //TODO make this do something
                    dialog.dismiss();
                }
            });
        });

        return rootView;
    }
}