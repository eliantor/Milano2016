package com.aktor.training.course.aware;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

/**
 * Created by aktor on 20/09/16.
 */

public class FindLocation extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "FindLocation";
    private int REQ_PERMISSIONS = 2;

    private GoogleApiClient mClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .build();
    }


    private void findLocationSafely() {
        if (mClient != null && mClient.isConnected()) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mClient);

            Log.e(TAG, "findLocationSafely: "+ lastLocation);
            if (lastLocation==null){
                LocationRequest req = new LocationRequest()
                        .setNumUpdates(1)
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                LocationListener listener =   new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        findAddress(location);
                        Log.e(TAG, "findLocationSafely: "+ location);
                    }
                };
//                Intent  i = new Intent();
//                PendingIntent pi = PendingIntent.getService(this,0,i,0);
//
//                LocationServices.FusedLocationApi.requestLocationUpdates(mClient,req,pi);

                LocationServices.FusedLocationApi.requestLocationUpdates(mClient, req,
                      listener);
//                LocationServices.FusedLocationApi.removeLocationUpdates(mClient,listener);
            } else {
                findAddress(lastLocation);
            }
        }
    }

    private void findAddress(Location lastLocation) {
        ///WARNING
        if (Geocoder.isPresent()){
            Geocoder geo = new Geocoder(this);
            try {
                List<Address> addresses = geo.getFromLocation(lastLocation.getLatitude(),
                        lastLocation.getLongitude(), 10);
                for (Address a:addresses){

                }
            } catch (IOException e) {

            }
        }
    }

    private void findCurrentLocation(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                ==PackageManager.PERMISSION_GRANTED&&
            ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                    ==PackageManager.PERMISSION_GRANTED ){
            findLocationSafely();
        } else {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },REQ_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQ_PERMISSIONS){
            for (int g:grantResults){
                if (g == PackageManager.PERMISSION_DENIED){
                    Log.d(TAG, "onRequestPermissionsResult: ");
                    return;
                } else {

                }
            }
            findLocationSafely();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        findCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mClient.connect();
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: "+connectionResult );
        if (connectionResult.hasResolution()){
            try {
                connectionResult.startResolutionForResult(this,120);
            } catch (IntentSender.SendIntentException e) {

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
