package com.mobile.taxi.services;

import com.squareup.otto.Bus;

/**
 * Created by Irving on 20/03/2015.
 */
public class BusInstance {

    private static final Bus INSTANCE = new Bus();

    public static Bus getInstance() {
        return INSTANCE;
    }

    private BusInstance() {
    }

}
