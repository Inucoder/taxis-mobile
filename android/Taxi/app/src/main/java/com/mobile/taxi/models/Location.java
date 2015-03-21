package com.mobile.taxi.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Irving on 21/03/2015.
 */
public class Location {

    @Expose
    private Double lat;
    @Expose
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

}
