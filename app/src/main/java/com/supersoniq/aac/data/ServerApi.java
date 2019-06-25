package com.supersoniq.aac.data;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ServerApi {
    public static final String endpoint = "https://msk-coffee-bot.herokuapp.com/api/v1";
    public static final String places = "/places";
    public static final String shops = "/shops";
    private RequestQueue requestQueue;

    public ServerApi(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public Request<String> getCoffeeShops(@NonNull final Response.Listener<String> listener,
                                          @Nullable Response.ErrorListener errorListener) {
        return requestQueue.add(new StringRequest(endpoint + shops, listener, errorListener));
    }
}
