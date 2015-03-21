package com.mobile.taxi;

import android.app.Application;

import com.mobile.taxi.services.ApiService;
import com.mobile.taxi.services.BusInstance;
import com.mobile.taxi.services.GoogleApi;
import com.squareup.otto.Bus;

import retrofit.RestAdapter;

/**
 * Created by Irving on 20/03/2015.
 */
public class TaxiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GoogleApi googleApi = new RestAdapter.Builder().setEndpoint(GoogleApi.ENDPOINT).build().create(GoogleApi.class);

        Bus bus = BusInstance.getInstance();
        ApiService apiService = new ApiService(bus, googleApi, this);
        bus.register(apiService);

    }

}
