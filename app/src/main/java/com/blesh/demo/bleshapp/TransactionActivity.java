package com.blesh.demo.bleshapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import java.io.IOException;

import blesh.BleshTemplateResultReceiver;
import blesh.RequestInformation;
import blesh.RestClient;
import blesh.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Skylifee7 on 24/06/2017.
 */

public class TransactionActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String bleshKey;
    private String TAG = "Transaction_Activity";
    private String amount;
    private String isSuccessful; //may be cast to boolean type.
    private double latitude, longitude;

    private LocationRequest mLocationRequest;
    protected GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        requestPermission();
        initLocationService();
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        bleshKey = intent.getStringExtra(BleshTemplateResultReceiver.EXTRA_MESSAGE);

        ImageButton paymentBtn = (ImageButton) findViewById(R.id.buttonPay);
        final EditText editTextAmount = (EditText) findViewById(R.id.editTextAmount);

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                amount = editTextAmount.getText().toString();
                callApiService();
            }
        });
    }

    private void callApiService() {

        RetrofitAPI retrofitAPI = RestClient.get().create(RetrofitAPI.class);
        Call<Object> transactionService = retrofitAPI.doTransactionOperation(getBodyParameter());

        transactionService.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                isSuccessful = response.body().toString();

                System.out.println(isSuccessful);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

                if (t instanceof IOException) {
                    Log.d(TAG, "Check your network connection!");
                }
            }
        });
    }

    private RequestInformation getBodyParameter() {

        RequestInformation requestInformation = new RequestInformation();

        requestInformation.setLatitude(latitude);
        requestInformation.setLongitude(longitude);
        requestInformation.setAmount(amount);
        requestInformation.setBleshKey(bleshKey);

        return requestInformation;
    }

    private void initLocationService() {

        try {
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                    .setFastestInterval(1 * 1000); // 1 second, in milliseconds

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .build();

        } catch (NetworkOnMainThreadException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
    }

    /**
     * @pre: GPS, Wifi and/or LTE must be enabled.
     * Sets up location service after permissions is granted.
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermission();
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

        } else {

            System.out.println(location.getLatitude());
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            Toast.makeText(this, "lat: " + location.getLatitude() + " ****** long: " + location.getLongitude(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting()) {
            Log.e("Google API", "Connecting");
            mGoogleApiClient.connect();
        }
    }

    @TargetApi(23)
    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            //We can request the permission if we haven't gotten it yet.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}