package com.mobile.taxi;

import android.app.Application;

import com.mobile.taxi.services.ApiService;
import com.mobile.taxi.services.BusInstance;
import com.squareup.otto.Bus;

/**
 * Created by Irving on 20/03/2015.
 */
public class TaxiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Bus bus = BusInstance.getInstance();
        ApiService apiService = new ApiService(bus);
        bus.register(apiService);

    }

}
