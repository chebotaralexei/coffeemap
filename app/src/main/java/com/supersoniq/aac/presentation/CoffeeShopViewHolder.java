package com.supersoniq.aac.presentation;

import android.view.View;
import android.widget.TextView;

import com.supersoniq.aac.R;
import com.supersoniq.aac.model.CoffeeNetwork;
import com.supersoniq.aac.utils.Action1;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CoffeeShopViewHolder extends RecyclerView.ViewHolder {

    private final TextView titleView;
    private final TextView descriptionView;
    private Action1<CoffeeNetwork> listener;

    public CoffeeShopViewHolder(@NonNull final View itemView, Action1<CoffeeNetwork> listener) {
        super(itemView);
        titleView = itemView.findViewById(R.id.coffee_shop_title);
        descriptionView = itemView.findViewById(R.id.coffee_shop_description);
        this.listener = listener;
    }

    public void bindTo(@NonNull final CoffeeNetwork item) {
        titleView.setText(item.getName());
        descriptionView.setText(String.valueOf(new DecimalFormat("##.## км")
                .format(item.getCoffeeShops().get(0).getDistance())));
        itemView.setOnClickListener(view -> listener.call(item));
    }
}