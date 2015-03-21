package com.mobile.taxi.events;

/**
 * Created by Irving on 21/03/2015.
 */
public class GetSuggestionsEvent {

    public final String query;

    public GetSuggestionsEvent(String query) {
        this.query = query;
    }

}
