package com.mobile.taxi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Irving on 21/03/2015.
 */
public class Result {

    @Expose
    private Geometry geometry;

    @SerializedName("address_components")
    @Expose
    private List<AddressComponent> addressComponents = new ArrayList<AddressComponent>();

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

}
