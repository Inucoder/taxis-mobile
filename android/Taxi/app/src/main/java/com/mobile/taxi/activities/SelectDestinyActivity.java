package com.mobile.taxi.activities;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.PolyUtil;
import com.mobile.taxi.R;
import com.mobile.taxi.adapters.PlacesSuggestionAdapter;
import com.mobile.taxi.events.ApiErrorEvent;
import com.mobile.taxi.events.GeocodeLatLngResultEvent;
import com.mobile.taxi.events.GetSuggestionsEvent;
import com.mobile.taxi.events.GetSuggestionsResultEvent;
import com.mobile.taxi.events.GetZoneCostsEvent;
import com.mobile.taxi.events.GetZoneCostsResultEvent;
import com.mobile.taxi.events.GetZonesEvent;
import com.mobile.taxi.events.GetZonesResultEvent;
import com.mobile.taxi.events.PlaceDetailEvent;
import com.mobile.taxi.events.PlaceDetailResultEvent;
import com.mobile.taxi.models.GeocodingResponse;
import com.mobile.taxi.models.Location;
import com.mobile.taxi.models.Prediction;
import com.mobile.taxi.models.Result;
import com.mobile.taxi.models.TaxiZone;
import com.mobile.taxi.services.BusInstance;
import com.mobile.taxi.utils.PredictionsCursorFactory;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SelectDestinyActivity extends ActionBarActivity {

    private static final String TAG = SelectDestinyActivity.class.getName();

    private static final String[] FROM_PLACES = new String[]{"_id", "place_id", "description"};

    private static final LatLng CANCUN_CENTER_POINT = new LatLng(21.161681372089326, -86.85911178588867);

    private Bus bus = BusInstance.getInstance();
    private GoogleMap map;

    private List<TaxiZone> zones;

    private TaxiZone sourceZone;
    private TaxiZone destinationZone;
    private SimpleCursorAdapter suggestionAdapter;
    private int[][] costs;
    private DragListener mapListener;
    private Button button;
    private ProgressBar progressBar;
    private SearchView destinyLocationSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_destiny);

        bus.register(this);

        // Replace action bar with toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.mipmap.ic_launcher);
            progressBar = (ProgressBar) findViewById(R.id.progress_spinner);
        }

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.taxi_map)).getMap();
        assert (map != null);

        configureMap();

        LocalitySearchListener searchListener = new LocalitySearchListener();

        destinyLocationSearch = (SearchView) findViewById(R.id.sv_destiny_location);
        destinyLocationSearch.setOnSuggestionListener(searchListener);
        destinyLocationSearch.setOnQueryTextListener(searchListener);

        suggestionAdapter = new PlacesSuggestionAdapter(this, android.R.layout.simple_list_item_1, null, FROM_PLACES, null, -100);
        destinyLocationSearch.setSuggestionsAdapter(suggestionAdapter);


        button = (Button) findViewById(R.id.btn_calculate_cost);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCosts();
            }
        });

        showProgressBar(true);

        //Request Zones and Costs
        bus.post(new GetZonesEvent());
        bus.post(new GetZoneCostsEvent());

    }

    private void showProgressBar(boolean show) {
        if (show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
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

        showProgressBar(false);

    }

    private void configureMap() {

        map.setMyLocationEnabled(true);

        mapListener = new DragListener();

        map.setOnMapClickListener(mapListener);
        map.setOnMarkerDragListener(mapListener);

        map.setOnMyLocationChangeListener(mapListener);


        map.moveCamera(CameraUpdateFactory.newLatLng(CANCUN_CENTER_POINT));
        map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);

    }

    @Subscribe
    public void onPointGeocoded(GeocodeLatLngResultEvent event) {

        GeocodingResponse geocodeResponse = event.geocodingResponse;

        if (geocodeResponse.getResults().isEmpty()) {
            Toast.makeText(this, "No se encontró dirección", Toast.LENGTH_SHORT).show();
            return;
        }

        Result first = geocodeResponse.getResults().get(0);

        showProgressBar(false);
    }

    @Subscribe
    public void onCostsEvent(GetZoneCostsResultEvent event) {
        costs = event.costs;
        Log.i(TAG, Arrays.toString(costs));

        showProgressBar(false);

    }

    @Subscribe
    public void onSuggestionsReceived(GetSuggestionsResultEvent event) {

        List<Prediction> predictions = event.response.getPredictions();
        Cursor cursor = PredictionsCursorFactory.generate(predictions, FROM_PLACES);
        suggestionAdapter.changeCursor(cursor);

        showProgressBar(false);

    }

    @Subscribe
    public void onPredictionDetail(PlaceDetailResultEvent event) {

        Result result = event.placeResponse.getResult();

        Location resultLocation = result.getGeometry().getLocation();

        LatLng point = new LatLng(resultLocation.getLat(), resultLocation.getLng());

        map.moveCamera(CameraUpdateFactory.newLatLng(point));
        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        mapListener.getMarker().setPosition(point);
        checkZones(mapListener.marker);

        showProgressBar(false);

    }

    @Subscribe
    public void onApiError(ApiErrorEvent event) {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¿Estás conectado a Internet?")
                .setContentText("Habilita tu conexión de datos primero.")
                .show();

        showProgressBar(false);

    }

    class DragListener implements GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMyLocationChangeListener {

        private Marker marker;

        @Override
        public void onMapClick(LatLng latLng) {

            Marker currentMarker = getMarker();

            currentMarker.setPosition(latLng);
            checkZones(marker);

        }

        @Override
        public void onMarkerDragStart(Marker marker) {

        }

        @Override
        public void onMarkerDrag(Marker marker) {

        }

        @Override
        public void onMarkerDragEnd(Marker marker) {
            checkZones(marker);
        }

        @Override
        public void onMyLocationChange(android.location.Location location) {

            LatLng sourcePoint = new LatLng(location.getLatitude(), location.getLongitude());

            if (sourceZone != null) {
                sourceZone.fillAsNonSelected();
            }

            for (TaxiZone zone : zones) {

                if (PolyUtil.containsLocation(sourcePoint, zone.getPoints(), true)) {
                    sourceZone = zone;

                    if (sourceZone != destinationZone) {
                        sourceZone.fillAsSource();
                    } else {
                        sourceZone.fillAsDestination();
                    }

                    break;
                }
            }

            changeButtonState();

        }

        public Marker getMarker() {

            if (this.marker == null) {
                marker = map.addMarker(new MarkerOptions().position(CANCUN_CENTER_POINT).draggable(true));
            }

            return marker;
        }

    }

    class LocalitySearchListener implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return searchPredictions(query);
        }

        @Override
        public boolean onQueryTextChange(String query) {
            return searchPredictions(query);
        }

        private boolean searchPredictions(String query) {

            if (query.length() > 3) {
                showProgressBar(true);
                bus.post(new GetSuggestionsEvent(query));
                return true;
            }

            return false;

        }

        @Override
        public boolean onSuggestionSelect(int position) {
            return getDetails(position);
        }

        @Override
        public boolean onSuggestionClick(int position) {
            return getDetails(position);
        }

        private boolean getDetails(int position) {

            Cursor cursor = (Cursor) suggestionAdapter.getItem(position);
            String placeId = cursor.getString(1);

            if (!placeId.isEmpty()) {

                String description = cursor.getString(2);

                destinyLocationSearch.setQuery(description, false);

                bus.post(new PlaceDetailEvent(placeId));
                showProgressBar(true);

                //El teclado se mantiene abierto, un pequenio hack para cerrarlo...
                hideKeyboard();
                return true;
            } else {
                return false;
            }
        }


    }

    private void checkZones(Marker marker) {

        if (destinationZone != null) {
            destinationZone.fillAsNonSelected();
            destinationZone = null;

            marker.setTitle("");

        }

        for (TaxiZone zone : zones) {
            if (PolyUtil.containsLocation(marker.getPosition(), zone.getPoints(), true)) {

                destinationZone = zone;
                zone.fillAsDestination();

                marker.setTitle(zone.getName());
                marker.showInfoWindow();

                return;
            }
        }

        changeButtonState();

    }

    private void changeButtonState() {
        button.setEnabled(sourceZone != null && destinationZone != null);
    }

    private void displayCosts() {

        if (sourceZone == null || destinationZone == null)
            return;

        int price = costs[sourceZone.getId()][destinationZone.getId()];

        SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("Tarifa");
        String content = "Origen: " + sourceZone.getName() + "\n" + "Destino: " + destinationZone.getName() + "\n" + "Costo: $" + price;
        dialog.setContentText(content);
        dialog.show();

    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}