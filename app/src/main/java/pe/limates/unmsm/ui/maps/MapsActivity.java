package pe.limates.unmsm.ui.maps;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Place;
import pe.limates.unmsm.ui.details_activity.DetailsMarkerActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MapsActivity extends AppCompatActivity implements MapsContractor.View, OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private MapsContractor.Presenter presenter;
    private Context mContext;
    private final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap map;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private Marker unmsmStadium;
    private ArrayList<Place> places;
    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mContext = this;
        if (presenter == null) {
            presenter = new MapsPresenter(mContext);
        }
        getLocationPermission();
        checkLocationService();
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttached(MapsActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng unmsm = new LatLng(-12.0574224, -77.0832648);

        places = getPresenter().getPlaces();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(unmsm, 16));

        googleMap.setOnMarkerClickListener(this);

        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


            } else {

                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }


    }

    @Override
    public void putMarkers() {
        for (Place place : places) {
            try{
                LatLng latLng = new LatLng( Double.parseDouble(place.getLat()), Double.parseDouble(place.getLng()));
                Marker marker = map.addMarker(new MarkerOptions().position(latLng).title(place.getName()));
                marker.setTag(place.getId());
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.map_permission)
                    .setMessage(R.string.map_permission_message)
                    .setPositiveButton(R.string.default_ok_text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MapsActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    99);
                        }
                    })
                    .setNegativeButton(R.string.default_cancel_text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MapsActivity.this, getString(R.string.map_cancel_permission), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create()
                    .show();
        }

    }

    //Result after requesting location permissions!
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 99:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    }
                } else {
                    //Error de Permisos
                    Toast.makeText(this, getString(R.string.map_error_permission), Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        mPlace = getPresenter().getPlaceInfo((String) marker.getTag());
        return false;
    }

    @Override
    public void goPlaceDetailsActivity() {
        Intent intent = new Intent(this, DetailsMarkerActivity.class);
        intent.putExtra("place", mPlace);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    public void checkLocationService() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, getString(R.string.map_location_enabled), Toast.LENGTH_SHORT).show();
        } else {
            showGPSDisabledAlertToUser();
        }
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setTitle(R.string.map_location_disabled)
                .setMessage(R.string.map_permission_message)
                .setCancelable(false)
                .setPositiveButton(R.string.map_positive_button,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton(R.string.default_cancel_text,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_in_back, R.anim.right_out_back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private MapsContractor.Presenter getPresenter() {
        return presenter;
    }
}
