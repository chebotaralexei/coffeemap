package com.supersoniq.aac.presentation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.supersoniq.aac.R;
import com.supersoniq.aac.model.CoffeeNetwork;
import com.supersoniq.aac.utils.Action1;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

class CoffeeShopsAdapter extends ListAdapter<CoffeeNetwork, CoffeeShopViewHolder> {

    private Action1<CoffeeNetwork> listener;

    public CoffeeShopsAdapter(@NonNull final Action1<CoffeeNetwork> listener) {
        super(CoffeeNetwork.DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public CoffeeShopViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                   final int viewType) {
        return new CoffeeShopViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_coffee_shop, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull final CoffeeShopViewHolder holder,
                                 final int position) {
        holder.bindTo(getItem(position));
    }
}
