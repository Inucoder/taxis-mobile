package com.mobile.taxi.models;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Irving on 21/03/2015.
 */
public class GeocodingResponse {

    @Expose
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
