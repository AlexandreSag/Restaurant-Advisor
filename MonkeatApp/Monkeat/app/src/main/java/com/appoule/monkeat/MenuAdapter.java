package com.appoule.monkeat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    private Context context;
    private List<Menu> list;
    private Boolean TokenIsHere;
    private MenuSelectListener listener;

    public MenuAdapter(Context context, List<Menu> list, Boolean TokenIsHere, MenuSelectListener listener) {
        this.context = context;
        this.list = list;
        this.TokenIsHere = TokenIsHere;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuViewHolder(LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.menuCardName.setText(list.get(position).getName());
        holder.menuCardDesc.setText(list.get(position).getDescription());
        holder.menuCardPrice.setText(list.get(position).getPrice());

        if(TokenIsHere){
            holder.menuEditBtn.setVisibility(View.VISIBLE);
        } else {
            holder.menuEditBtn.setVisibility(View.GONE);
        }

        holder.menuEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemMenuClicked(list.get(holder.getAdapterPosition()));
                //listener.onItemClicked(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Menu> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}
