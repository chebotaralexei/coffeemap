package com.supersoniq.aac.model;

import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.supersoniq.aac.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class CoffeeShop {

    public static final DiffUtil.ItemCallback<CoffeeShop> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CoffeeShop>() {
                @Override
                public boolean areItemsTheSame(@NonNull final CoffeeShop oldShop,
                                               @NonNull final CoffeeShop newShop) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldShop.getId().equals(newShop.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull final CoffeeShop oldShop,
                                                  @NonNull final CoffeeShop newShop) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldShop.equals(newShop);
                }
            };


    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("index")
    @Expose
    private String index;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("places")
    @Expose
    private List<Place> places = null;
    @SerializedName("stuff")
    @Expose
    private String stuff;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("url")
    @Expose
    private String url;

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public String getStuff() {
        return stuff;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getUrl() {
        return url;
    }

    public CoffeeShop(final long id, @NonNull final String title) {

    }

    @NonNull
    public static List<CoffeeShop> testShops(String response) {
        final List<CoffeeShop> shops = new ArrayList<>();
        if (response == null) {
            shops.add(new CoffeeShop(1, "SKURATOV"));
            shops.add(new CoffeeShop(2, "STARBUCKS"));
            shops.add(new CoffeeShop(3, "COFIX"));
        } else {
            for (int i = 0; i < response.length() - 20; i += 20) {
                shops.add(new CoffeeShop(i, response.substring(i, i + 20)));
            }
        }
        return shops;
    }


    @Nullable
    public static List<CoffeeShop> getCoffeeShopsFromAssets(@NonNull final AssetManager asset) {
        String json = Utils.loadAssetTextAsString("shops_full.json", asset);
        return new Gson().fromJson(json, new TypeToken<List<CoffeeShop>>() {}.getType());
    }

}
