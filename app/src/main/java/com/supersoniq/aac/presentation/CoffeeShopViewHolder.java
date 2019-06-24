package com.supersoniq.aac.presentation;

import android.view.View;
import android.widget.TextView;

import com.supersoniq.aac.R;
import com.supersoniq.aac.model.CoffeeShop;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CoffeeShopViewHolder extends RecyclerView.ViewHolder {

    private final TextView titleView;
    private final TextView descriptionView;

    public CoffeeShopViewHolder(@NonNull final View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.coffee_shop_title);
        descriptionView = itemView.findViewById(R.id.coffee_shop_description);
    }

    public void bindTo(@NonNull final CoffeeShop item) {
        titleView.setText(item.getName());
        descriptionView.setText(item.getDescription());
    }
}