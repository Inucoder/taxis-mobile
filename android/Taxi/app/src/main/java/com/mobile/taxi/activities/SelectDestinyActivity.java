package com.mobile.taxi.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.mobile.taxi.R;
import com.mobile.taxi.events.GetZonesEvent;
import com.mobile.taxi.events.GetZonesResultEvent;
import com.mobile.taxi.models.TaxiZone;
import com.mobile.taxi.services.BusInstance;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

public class SelectDestinyActivity extends ActionBarActivity {

    private static final String TAG = SelectDestinyActivity.class.getName();

    private Bus bus = BusInstance.getInstance();
    private GoogleMap map;

    private List<TaxiZone> zones;

    private TaxiZone selectedZone;

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

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                for (TaxiZone zone : zones) {
                    if (PolyUtil.containsLocation(latLng, zone.getPoints(), true)) {

                        if(selectedZone != null){
                            selectedZone.getArea().setFillColor(Color.BLUE);
                        }

                        selectedZone = zone;
                        zone.getArea().setFillColor(Color.GREEN);

                        Toast.makeText(SelectDestinyActivity.this, "Zona " + zone.getName() + " tocada", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                if(selectedZone != null){
                    selectedZone.getArea().setFillColor(Color.BLUE);
                    selectedZone = null;
                }

            }
        });

    }

}
