package com.supersoniq.aac.presentation;

import com.android.volley.Request;
import com.supersoniq.aac.App;
import com.supersoniq.aac.model.CoffeeShop;

import java.util.Collections;
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
                    final List<CoffeeShop> data = CoffeeShop.getCoffeeShopsFromAssets(App.INSTANCE.getAssets());
                    coffeeShopsLiveData.postValue(data == null ? Collections.EMPTY_LIST : data);
                    progressLiveData.postValue(false);
                },
                error -> {
                    coffeeShopsLiveData.postValue(Collections.singletonList(new CoffeeShop(10000, error.getLocalizedMessage())));
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
