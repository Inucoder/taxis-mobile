package com.mobile.taxi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Irving on 21/03/2015.
 */
public class AddressComponent {

    @Expose
    @SerializedName("long_name")
    private String longName;

    @Expose
    @SerializedName("short_name")
    private String shortName;

    @Expose
    private List<String> types = new ArrayList<>();

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

}