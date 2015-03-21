package com.mobile.taxi.events;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Irving on 21/03/2015.
 */
public class GeocodeLatLngEvent {

    public final LatLng point;

    public GeocodeLatLngEvent(LatLng point) {
        this.point = point;
    }

}
