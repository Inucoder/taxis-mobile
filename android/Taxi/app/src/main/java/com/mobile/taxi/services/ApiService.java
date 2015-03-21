package com.mobile.taxi.services;

import android.content.Context;

import com.google.gson.Gson;
import com.mobile.taxi.events.GetZonesEvent;
import com.mobile.taxi.events.GetZonesResultEvent;
import com.mobile.taxi.models.TaxiZone;
import com.mobile.taxi.utils.TaxiJsonReader;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Irving on 20/03/2015.
 */
public class ApiService {

    private static final String TAG = ApiService.class.getName();

    private Bus bus;
    private Context contex;

    public ApiService(Bus bus, Context contex) {
        this.bus = bus;
        this.contex = contex;
    }

    @Subscribe
    public void onGetZones(GetZonesEvent event) {

        //TODO: Regresar zonas consumidas del ws

        String json = TaxiJsonReader.loadJSONFromAsset(contex);

        Gson gson = new Gson();

        TaxiZone[] zones = gson.fromJson(json, TaxiZone[].class);

        /*
        zone.setArea(new PolygonOptions()
                .add(new LatLng(0, 0), new LatLng(0, 5), new LatLng(3, 5), new LatLng(0, 0))
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));
                */

        List<TaxiZone> taxiZoneList = Arrays.asList(zones);
        bus.post(new GetZonesResultEvent(taxiZoneList));
    }

}
