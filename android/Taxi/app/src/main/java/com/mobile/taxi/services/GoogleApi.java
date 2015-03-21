package com.mobile.taxi.services;

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
    public void geocodeLatLng(@Query("latlng") String latLng, @Query("components") String components, Callback<GeocodingResponse> callback);

}
