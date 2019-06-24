package com.supersoniq.aac;

import android.app.Application;

import com.android.volley.toolbox.Volley;
import com.supersoniq.aac.data.ServerApi;

public class App extends Application {
    public static App INSTANCE;

    private ServerApi serverApi;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        serverApi = new ServerApi(Volley.newRequestQueue(this));
    }

    public ServerApi getServerApi() {
        return serverApi;
    }
}
