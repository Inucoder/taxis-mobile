package com.mobile.taxi.events;

import com.mobile.taxi.models.AutocompleteResponse;

/**
 * Created by Irving on 21/03/2015.
 */
public class GetSuggestionsResultEvent {

    public final AutocompleteResponse response;

    public GetSuggestionsResultEvent(AutocompleteResponse response) {
        this.response = response;
    }

}
