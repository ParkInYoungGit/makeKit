package com.example.makekit.makekit_bean;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyClustItem implements ClusterItem {

    private final LatLng mPosition;
    //private final String mTitle;

    public MyClustItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
        //this.mTitle = mTitle;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getSnippet() {
        return null;
    }
}
