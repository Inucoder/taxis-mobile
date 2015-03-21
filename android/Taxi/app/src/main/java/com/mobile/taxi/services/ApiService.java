package com.mobile.taxi.services;

import com.mobile.taxi.events.GetZonesEvent;
import com.mobile.taxi.events.GetZonesResultEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by Irving on 20/03/2015.
 */
public class ApiService {

    private static final String TAG = ApiService.class.getName();

    private Bus bus;

    public ApiService(Bus bus) {
        this.bus = bus;
    }

    @Subscribe
    public void onGetZones(GetZonesEvent event){
        bus.post(new GetZonesResultEvent());
    }

}
