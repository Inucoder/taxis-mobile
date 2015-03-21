package com.mobile.taxi.activities;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.PolyUtil;
import com.mobile.taxi.R;
import com.mobile.taxi.adapters.PlacesSuggestionAdapter;
import com.mobile.taxi.events.GeocodeLatLngEvent;
import com.mobile.taxi.events.GeocodeLatLngResultEvent;
import com.mobile.taxi.events.GetSuggestionsEvent;
import com.mobile.taxi.events.GetSuggestionsResultEvent;
import com.mobile.taxi.events.GetZonesEvent;
import com.mobile.taxi.events.GetZonesResultEvent;
import com.mobile.taxi.models.GeocodingResponse;
import com.mobile.taxi.models.Prediction;
import com.mobile.taxi.models.Result;
import com.mobile.taxi.models.TaxiZone;
import com.mobile.taxi.services.BusInstance;
import com.mobile.taxi.utils.PredictionsCursorFactory;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

public class SelectDestinyActivity extends ActionBarActivity {

    private static final String TAG = SelectDestinyActivity.class.getName();

    private static final String[] FROM_PLACES = new String[]{"_id", "place_id", "description"};

    private Bus bus = BusInstance.getInstance();
    private GoogleMap map;

    private List<TaxiZone> zones;

    private TaxiZone selectedZone;
    private TextView destinyAddress;
    private SimpleCursorAdapter suggestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_destiny);

        bus.register(this);

        // Replace action bar with toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.taxi_map)).getMap();
        assert (map != null);

        configureMap();

        destinyAddress = (TextView) findViewById(R.id.tv_destiny_address);

        LocalitySearchListener searchListener = new LocalitySearchListener();

        SearchView destinyLocationSearch = (SearchView) findViewById(R.id.sv_destiny_location);
        destinyLocationSearch.setOnSuggestionListener(searchListener);
        destinyLocationSearch.setOnQueryTextListener(searchListener);

        suggestionAdapter = new PlacesSuggestionAdapter(this, android.R.layout.simple_list_item_1, null, FROM_PLACES, null, -100);
        destinyLocationSearch.setSuggestionsAdapter(suggestionAdapter);

        bus.post(new GetZonesEvent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_destiny, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Subscribe
    public void onZonesReceived(GetZonesResultEvent event) {

        this.zones = event.zones;

        for (TaxiZone zone : zones) {
            zone.inflateArea(map);
        }

    }

    private void configureMap() {

        map.setMyLocationEnabled(true);

        DragListener mapListener = new DragListener();

        map.setOnMapClickListener(mapListener);
        map.setOnMarkerDragListener(mapListener);

    }

    @Subscribe
    public void onPointGeocoded(GeocodeLatLngResultEvent event) {

        GeocodingResponse geocodeResponse = event.geocodingResponse;

        if (geocodeResponse.getResults().isEmpty()) {
            Toast.makeText(this, "No se encontró dirección", Toast.LENGTH_SHORT).show();
            return;
        }

        Result first = geocodeResponse.getResults().get(0);

        destinyAddress.setText(first.getFormatedAddress());

    }


    @Subscribe
    public void onSuggestionsReceived(GetSuggestionsResultEvent event){

        List<Prediction> predictions = event.response.getPredictions();
        Cursor cursor = PredictionsCursorFactory.generate(predictions, FROM_PLACES);
        suggestionAdapter.changeCursor(cursor);

    }

    class DragListener implements GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener {

        private Marker marker;

        @Override
        public void onMapClick(LatLng latLng) {

            if (marker == null) {
                marker = map.addMarker(new MarkerOptions().position(latLng).draggable(true));
            } else {
                marker.setPosition(latLng);
                marker.setDraggable(true);
            }

            checkZones();
            geocodePosition();

        }

        @Override
        public void onMarkerDragStart(Marker marker) {

        }

        @Override
        public void onMarkerDrag(Marker marker) {

        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            checkZones();
            geocodePosition();
        }

        private void checkZones() {

            for (TaxiZone zone : zones) {
                if (PolyUtil.containsLocation(marker.getPosition(), zone.getPoints(), true)) {

                    if (selectedZone != null) {
                        selectedZone.getArea().setFillColor(Color.BLUE);
                    }

                    selectedZone = zone;
                    zone.getArea().setFillColor(Color.GREEN);

                    Toast.makeText(SelectDestinyActivity.this, "Zona " + zone.getName() + " tocada", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            if (selectedZone != null) {
                selectedZone.getArea().setFillColor(Color.BLUE);
                selectedZone = null;
            }

        }

        private void geocodePosition() {
            LatLng point = marker.getPosition();
            bus.post(new GeocodeLatLngEvent(point));
        }

    }

    class LocalitySearchListener implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener{

        @Override
        public boolean onQueryTextSubmit(String query) {
            if(query.length() > 3){
                bus.post(new GetSuggestionsEvent(query));
                return true;
            }
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            if(query.length() > 3){
                bus.post(new GetSuggestionsEvent(query));
                return true;
            }
            return false;
        }

        @Override
        public boolean onSuggestionSelect(int i) {
            return false;
        }

        @Override
        public boolean onSuggestionClick(int i) {
            return false;
        }

    }

}