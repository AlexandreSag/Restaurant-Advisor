package com.appoule.monkeat;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {

    public TextView textName;
    public RatingBar ratingBar;
    public CardView cardView;

    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.restaurant_container);
        textName = itemView.findViewById(R.id.textName);
        ratingBar = itemView.findViewById(R.id.RestoratingBar);
    }
}
