package com.mobile.taxi.events;

import com.mobile.taxi.models.PlaceResponse;

/**
 * Created by Irving on 21/03/2015.
 */
public class PlaceDetailResultEvent {

    public final PlaceResponse placeResponse;

    public PlaceDetailResultEvent(PlaceResponse placeResponse) {
        this.placeResponse = placeResponse;
    }

}
