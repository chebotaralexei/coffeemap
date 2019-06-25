package com.supersoniq.aac.presentation;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.supersoniq.aac.App;
import com.supersoniq.aac.model.CoffeeShop;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoffeeShopsViewModel extends ViewModel {
    private final MutableLiveData<List<CoffeeShop>> coffeeShopsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> progressLiveData = new MutableLiveData<>();
    private Request<String> request;

    public void init() {
        progressLiveData.postValue(true);

        request = App.INSTANCE.getServerApi().getCoffeeShops(response -> {
                    coffeeShopsLiveData.postValue(new Gson().fromJson(response, new TypeToken<List<CoffeeShop>>() {}.getType()));
                    progressLiveData.postValue(false);
                },
                error -> {
                    progressLiveData.postValue(false);
                });
    }

    public MutableLiveData<List<CoffeeShop>> observeCoffeeShops() {
        return coffeeShopsLiveData;
    }

    public MutableLiveData<Boolean> observeProgress() {
        return progressLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        request.cancel();
    }
}
