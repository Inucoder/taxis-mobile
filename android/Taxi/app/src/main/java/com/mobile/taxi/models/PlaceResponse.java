package com.mobile.taxi.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Irving on 21/03/2015.
 */
public class PlaceResponse {

    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
