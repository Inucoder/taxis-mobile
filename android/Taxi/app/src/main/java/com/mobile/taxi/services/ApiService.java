package com.mobile.taxi.services;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.mobile.taxi.events.GeocodeLatLngEvent;
import com.mobile.taxi.events.GeocodeLatLngResultEvent;
import com.mobile.taxi.events.GetZonesEvent;
import com.mobile.taxi.events.GetZonesResultEvent;
import com.mobile.taxi.models.GeocodingResponse;
import com.mobile.taxi.models.TaxiZone;
import com.mobile.taxi.utils.TaxiJsonReader;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Irving on 20/03/2015.
 */
public class ApiService {

    private static final String TAG = ApiService.class.getName();

    private static final String DEFAULT_COUNTRY_COMPONENT = "country:MX";
    private static final String DEFAULT_COMPONENTS = DEFAULT_COUNTRY_COMPONENT + "|locality:" + Uri.encode("Cancún");

    private Bus bus;
    private Context context;

    private GoogleApi googleApi;

    public ApiService(Bus bus, GoogleApi googleApi, Context contex) {
        this.bus = bus;
        this.context = contex;
        this.googleApi = googleApi;
    }

    @Subscribe
    public void onGetZones(GetZonesEvent event) {

        //TODO: Regresar zonas consumidas del ws
        String json = TaxiJsonReader.loadJSONFromAsset(context);

        Gson gson = new Gson();

        TaxiZone[] zones = gson.fromJson(json, TaxiZone[].class);

        List<TaxiZone> taxiZoneList = Arrays.asList(zones);
        bus.post(new GetZonesResultEvent(taxiZoneList));
    }

    @Subscribe
    public void onGeocodePoint(GeocodeLatLngEvent event) {

        String latLng = event.point.latitude + "," + event.point.longitude;

        googleApi.geocodeLatLng(latLng, DEFAULT_COUNTRY_COMPONENT, new Callback<GeocodingResponse>() {
            @Override
            public void success(GeocodingResponse geocodingResponse, Response response) {
                bus.post(new GeocodeLatLngResultEvent(geocodingResponse));
            }

            @Override
            public void failure(RetrofitError error) {
                error.getResponse().getReason();
                Log.e(TAG, error.getMessage());
            }
        });

    }

}
