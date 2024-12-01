package com.aiden.recipesearch.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.aiden.recipesearch.R;

public class ChooseItemViewHolder extends RecyclerView.ViewHolder {
    private final CheckBox itemCheckBox;
    private ChooseItemViewHolder(View itemView){
        super(itemView);
        itemCheckBox = itemView.findViewById(R.id.selectIngredient);
    }

    public void bind(String text){
        itemCheckBox.setText(text);
    }

    static ChooseItemViewHolder create (ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_choose_item, parent, false);
        return new ChooseItemViewHolder(view);
    }
}
