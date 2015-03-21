package com.mobile.taxi.events;

import com.mobile.taxi.models.GeocodingResponse;

/**
 * Created by Irving on 21/03/2015.
 */
public class GeocodeLatLngResultEvent {

    public final GeocodingResponse geocodingResponse;

    public GeocodeLatLngResultEvent(GeocodingResponse geocodingResponse) {
        this.geocodingResponse = geocodingResponse;
    }

}
