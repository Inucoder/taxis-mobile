package com.mobile.taxi.services;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.mobile.taxi.events.ApiErrorEvent;
import com.mobile.taxi.events.GeocodeLatLngEvent;
import com.mobile.taxi.events.GeocodeLatLngResultEvent;
import com.mobile.taxi.events.GetSuggestionsEvent;
import com.mobile.taxi.events.GetSuggestionsResultEvent;
import com.mobile.taxi.events.GetZoneCostsEvent;
import com.mobile.taxi.events.GetZoneCostsResultEvent;
import com.mobile.taxi.events.GetZonesEvent;
import com.mobile.taxi.events.GetZonesResultEvent;
import com.mobile.taxi.events.PlaceDetailEvent;
import com.mobile.taxi.events.PlaceDetailResultEvent;
import com.mobile.taxi.models.AutocompleteResponse;
import com.mobile.taxi.models.GeocodingResponse;
import com.mobile.taxi.models.PlaceResponse;
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

    private static final String DEFAULT_COUNTRY_COMPONENT = "country:mx";
    private static final String DEFAULT_COMPONENTS = DEFAULT_COUNTRY_COMPONENT + "|locality:" + Uri.encode("Cancún");
    private static final String DEFAULT_TYPES = "(cities)";
    private static final String DEFAULT_LANGUAGE = "es";
    private static final String GOOGLE_API_KEY = "AIzaSyBHJAB-jGgbOZn024MYdfvMUtO2AQ9SIdA";
    public static final String CANCUN_CENTER_POINT = "21.161681372089326,-86.85911178588867";
    public static final String DEFAULT_RADIUS = "11000";

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
        String json = TaxiJsonReader.loadJSONFromAsset(context, "zones.json");

        Gson gson = new Gson();

        TaxiZone[] zones = gson.fromJson(json, TaxiZone[].class);

        List<TaxiZone> taxiZoneList = Arrays.asList(zones);
        bus.post(new GetZonesResultEvent(taxiZoneList));
    }

    @Subscribe
    public void onGetCosts(GetZoneCostsEvent event){

        String json = TaxiJsonReader.loadJSONFromAsset(context, "costs.json");

        Gson gson = new Gson();

        int[][] costs = gson.fromJson(json, int[][].class);

        bus.post(new GetZoneCostsResultEvent(costs));

    }

    @Subscribe
    public void onGeocodePoint(GeocodeLatLngEvent event) {

        String latLng = event.point.latitude + "," + event.point.longitude;

        googleApi.geocodeLatLng(latLng, new Callback<GeocodingResponse>() {

            @Override
            public void success(GeocodingResponse geocodingResponse, Response response) {
                bus.post(new GeocodeLatLngResultEvent(geocodingResponse));
            }

            @Override
            public void failure(RetrofitError error) {
                bus.post(new ApiErrorEvent());
            }

        });

    }

    @Subscribe
    public void onGetSuggestion(GetSuggestionsEvent event) {

        String query = event.query;

        googleApi.getPredictions(GOOGLE_API_KEY, DEFAULT_COUNTRY_COMPONENT, query, DEFAULT_LANGUAGE, CANCUN_CENTER_POINT, DEFAULT_RADIUS, new Callback<AutocompleteResponse>() {
            @Override
            public void success(AutocompleteResponse autocompleteResponse, Response response) {
                bus.post(new GetSuggestionsResultEvent(autocompleteResponse));
            }

            @Override
            public void failure(RetrofitError error) {
                bus.post(new ApiErrorEvent());
            }
        });

    }

    @Subscribe
    public void onPredictionDetail(PlaceDetailEvent event) {

        Log.i(TAG, "Detalles place_id: " + event.placeId);

        String placeId = event.placeId;

        googleApi.getPredictionDetail(GOOGLE_API_KEY, placeId, DEFAULT_LANGUAGE, new Callback<PlaceResponse>() {
            @Override
            public void success(PlaceResponse placeResponse, Response response) {
                bus.post(new PlaceDetailResultEvent(placeResponse));
            }

            @Override
            public void failure(RetrofitError error) {
                bus.post(new ApiErrorEvent());
            }

        });


    }

}