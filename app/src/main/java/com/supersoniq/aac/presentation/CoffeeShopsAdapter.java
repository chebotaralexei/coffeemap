package com.supersoniq.aac.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.supersoniq.aac.R;
import com.supersoniq.aac.model.CoffeeShop;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

class CoffeeShopsAdapter extends ListAdapter<CoffeeShop, CoffeeShopViewHolder> {

    protected CoffeeShopsAdapter() {
        super(CoffeeShop.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public CoffeeShopViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                   final int viewType) {
        return new CoffeeShopViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_coffee_shop, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CoffeeShopViewHolder holder,
                                 final int position) {
        holder.bindTo(getItem(position));
    }
}
