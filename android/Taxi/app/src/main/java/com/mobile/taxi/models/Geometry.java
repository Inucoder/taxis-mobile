package com.mobile.taxi.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Irving on 21/03/2015.
 */
public class Geometry {

    @Expose
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
