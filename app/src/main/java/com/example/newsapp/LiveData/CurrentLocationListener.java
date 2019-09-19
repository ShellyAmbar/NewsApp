package com.example.newsapp.LiveData;

import android.content.Context;
import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class CurrentLocationListener extends MutableLiveData<Location> {

    private static CurrentLocationListener instance;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;

    public static CurrentLocationListener getInstance(Context appContext) {
        if (instance == null) {
            instance = new CurrentLocationListener(appContext);
        }
        return instance;
    }
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

                if (locationResult != null)
                    setValue(locationResult.getLastLocation());

        }
    };

    private CurrentLocationListener(Context appContext) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null)
                    setValue(location);
            }
        });
        createLocationRequest();
    }


    private void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000000000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    @Override
    protected void onActive() {
        super.onActive();
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (mLocationCallback != null)
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
}
