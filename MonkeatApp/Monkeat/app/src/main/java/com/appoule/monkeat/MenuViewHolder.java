package com.appoule.monkeat;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MenuViewHolder extends RecyclerView.ViewHolder {

    public TextView menuCardName, menuCardDesc, menuCardPrice;
    public CardView cardView;
    public ImageButton menuEditBtn;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.menu_container);
        menuEditBtn = itemView.findViewById(R.id.MenuEditButton);
        menuCardName = itemView.findViewById(R.id.MenuCardName);
        menuCardDesc = itemView.findViewById(R.id.MenuCardDesc);
        menuCardPrice = itemView.findViewById(R.id.MenuCardPrice);
    }
}
