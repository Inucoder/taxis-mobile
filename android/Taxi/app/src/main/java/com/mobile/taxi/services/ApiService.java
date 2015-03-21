package com.mobile.taxi.services;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.mobile.taxi.events.GetZonesEvent;
import com.mobile.taxi.events.GetZonesResultEvent;
import com.mobile.taxi.models.TaxiZone;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

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
    public void onGetZones(GetZonesEvent event) {

        //TODO: Regresar zonas consumidas del ws

        List<TaxiZone> zones = new ArrayList<>();

        TaxiZone zone = new TaxiZone();
        zone.setArea(new PolygonOptions()
                .add(new LatLng(0, 0), new LatLng(0, 5), new LatLng(3, 5), new LatLng(0, 0))
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));

        zones.add(zone);

        bus.post(new GetZonesResultEvent(zones));
    }

}
