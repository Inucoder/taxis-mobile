package com.mobile.taxi.models;

import com.google.android.gms.maps.model.PolygonOptions;

/**
 * Created by Irving on 20/03/2015.
 */
public class TaxiZone {

    private PolygonOptions area;

    public PolygonOptions getArea() {
        return area;
    }

    public void setArea(PolygonOptions area) {
        this.area = area;
    }
}
