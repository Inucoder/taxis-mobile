package com.mobile.taxi.events;

/**
 * Created by Irving on 21/03/2015.
 */
public class GetZoneCostsResultEvent {

    public final int[][] costs;

    public GetZoneCostsResultEvent(int[][] costs) {
        this.costs = costs;
    }

}