package com.supersoniq.aac.presentation;

import com.android.volley.Request;
import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.supersoniq.aac.App;
import com.supersoniq.aac.model.CoffeeNetwork;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoffeeShopsViewModel extends ViewModel {
    private final MutableLiveData<List<CoffeeNetwork>> coffeeShopsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> progressLiveData = new MutableLiveData<>();
    private Request<String> request;
    private final MutableLiveData<Integer> mapTypeLiveData = new MutableLiveData<>();

    public void init() {
        progressLiveData.postValue(true);

        request = App.INSTANCE.getServerApi().getCoffeeShops(response -> {
                    coffeeShopsLiveData.postValue(new Gson().fromJson(response, new TypeToken<List<CoffeeNetwork>>() {}.getType()));
//                    coffeeShopsLiveData.postValue(CoffeeNetwork.getCoffeeShopsFromAssets(App.INSTANCE.getAssets()));
                    progressLiveData.postValue(false);
                },
                error -> {
                    progressLiveData.postValue(false);
                });

        mapTypeLiveData.postValue(GoogleMap.MAP_TYPE_NORMAL);
    }

    public MutableLiveData<List<CoffeeNetwork>> observeCoffeeShops() {
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

    public MutableLiveData<Integer> observeMapType() {
        return mapTypeLiveData;
    }
}
