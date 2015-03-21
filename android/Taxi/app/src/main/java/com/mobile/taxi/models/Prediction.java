package com.mobile.taxi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Irving on 21/03/2015.
 */
public class Prediction {

    @Expose
    private String description;
    @SerializedName("place_id")
    @Expose
    private String placeId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

}
