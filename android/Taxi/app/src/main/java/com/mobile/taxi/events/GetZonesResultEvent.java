package com.mobile.taxi.events;

import com.mobile.taxi.models.TaxiZone;

import java.util.List;

/**
 * Created by Irving on 20/03/2015.
 */
public class GetZonesResultEvent {

    public final List<TaxiZone> zones;

    public GetZonesResultEvent(List<TaxiZone> zones) {
        this.zones = zones;
    }

}
