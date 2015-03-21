package com.mobile.taxi.models;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Irving on 20/03/2015.
 */
public class TaxiZone {

    @Expose
    private Integer id;
    @Expose
    private String name;

    @Expose
    private List<Polygon> polygon = new ArrayList<Polygon>();

    private transient com.google.android.gms.maps.model.Polygon area;

    private transient List<LatLng> points = new ArrayList<>();

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The polygon
     */
    public List<Polygon> getPolygon() {
        return polygon;
    }

    /**
     * @param polygon The polygon
     */
    public void setPolygon(List<Polygon> polygon) {
        this.polygon = polygon;
    }

    public void inflateArea(GoogleMap map){

        PolygonOptions options = new PolygonOptions();
        LatLng point;

        for (Polygon raw : polygon) {
            point = new LatLng(raw.getLat(), raw.getLng());
            points.add(point);
            options.add(point);
        }

        options.strokeColor(Color.RED)
                .fillColor(Color.BLUE);

        this.area = map.addPolygon(options);

    }

    public com.google.android.gms.maps.model.Polygon getArea() {
        return area;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public class Polygon {

        @Expose
        private Double lat;
        @Expose
        private Double lng;

        /**
         * @return The lat
         */
        public Double getLat() {
            return lat;
        }

        /**
         * @param lat The lat
         */
        public void setLat(Double lat) {
            this.lat = lat;
        }

        /**
         * @return The lng
         */
        public Double getLng() {
            return lng;
        }

        /**
         * @param lng The lng
         */
        public void setLng(Double lng) {
            this.lng = lng;
        }

    }

}