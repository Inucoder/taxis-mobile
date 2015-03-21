package com.mobile.taxi.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Irving on 21/03/2015.
 */
public class AutocompleteResponse {

    @Expose
    private List<Prediction> predictions = new ArrayList<>();

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }
}
