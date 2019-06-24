package com.supersoniq.aac.data;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ServerApi {
    public static final String endpoint = "https://www.google.com";
    private RequestQueue requestQueue;

    public ServerApi(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public Request<String> getCoffeeShops(@NonNull final Response.Listener<String> listener,
                                          @Nullable Response.ErrorListener errorListener) {
        return requestQueue.add(new StringRequest(endpoint, listener, errorListener));
    }
}
