package com.mobile.taxi.services;

import com.mobile.taxi.models.AutocompleteResponse;
import com.mobile.taxi.models.GeocodingResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Irving on 21/03/2015.
 */
public interface GoogleApi {

    public static final String ENDPOINT = "https://maps.googleapis.com/maps/api";

    @GET("/geocode/json")
    public void geocodeLatLng(@Query("latlng") String latLng, Callback<GeocodingResponse> callback);

    @GET("/place/autocomplete/json")
    public void getPredictions(@Query("key") String key, @Query("components") String components,
                               @Query("input") String input, @Query("types") String types, @Query("language") String language,
                               Callback<AutocompleteResponse> callback);

}
