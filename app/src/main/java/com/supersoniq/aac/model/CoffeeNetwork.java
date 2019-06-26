package com.supersoniq.aac.model;

import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.supersoniq.aac.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class CoffeeNetwork {

    public static final DiffUtil.ItemCallback<CoffeeNetwork> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CoffeeNetwork>() {
                @Override
                public boolean areItemsTheSame(@NonNull final CoffeeNetwork oldShop,
                                               @NonNull final CoffeeNetwork newShop) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldShop.getId().equals(newShop.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull final CoffeeNetwork oldShop,
                                                  @NonNull final CoffeeNetwork newShop) {
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
    private List<CoffeeShop> coffeeShops = null;
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

    public List<CoffeeShop> getCoffeeShops() {
        return coffeeShops;
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

    public CoffeeNetwork() {
    }

    @Nullable
    public static List<CoffeeNetwork> getCoffeeShopsFromAssets(@NonNull final AssetManager asset) {
        String json = Utils.loadAssetTextAsString("shops_full.json", asset);
        return new Gson().fromJson(json, new TypeToken<List<CoffeeNetwork>>() {}.getType());
    }

}
