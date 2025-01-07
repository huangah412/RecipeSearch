package com.aiden.recipesearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.aiden.recipesearch.database.IngredientViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private IngredientViewModel viewModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button searchButton = view.findViewById(R.id.searchButton);
        Button searchButtonRandom = view.findViewById(R.id.searchButtonRandom);

        searchButton.setOnClickListener(this::searchRecipe);
        searchButtonRandom.setOnClickListener(this::searchRandom);

        viewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        return view;
    }

    public void searchRecipe(View view){
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivity(intent);
    }

    public void searchRandom(View view){

        List<String> ingredients = viewModel.getIngredientNames();

        final int MIN_INGREDIENTS = 3;
        if (ingredients.size() <= MIN_INGREDIENTS) {
            Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.random_search_input_more), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        StringBuilder stringBuilder = new StringBuilder("https://www.google.com/search?q=$recipe$+");
        for(int i = 0; i < MIN_INGREDIENTS; i++){
            int selected = (int)(Math.random()*ingredients.size());
            String ingredient = ingredients.remove(selected);
            stringBuilder.append(String.format("$%s$+", ingredient));
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuilder.toString().replaceAll("\\$","\"")));
        startActivity(intent);
    }
}