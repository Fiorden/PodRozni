package com.fjord.podrozni.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by fjord on 18.04.2017.
 */

public class GmapFragment extends Fragment implements OnMapReadyCallback {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng zamek = new LatLng(50.586486,16.8075443);
        googleMap.addMarker(new MarkerOptions().position(zamek)
                .title("Zamek Książęcy"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(zamek));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }
}
