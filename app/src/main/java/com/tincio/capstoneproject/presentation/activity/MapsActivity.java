package com.tincio.capstoneproject.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.tincio.capstoneproject.R;
import com.tincio.capstoneproject.data.database.PichangaDB;
import com.tincio.capstoneproject.data.model.SoccerField;
import com.tincio.capstoneproject.data.provider.SoccerFieldContract;
import com.tincio.capstoneproject.presentation.presenter.MapsPresenter;
import com.tincio.capstoneproject.presentation.view.MapsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, MapsView {

    private GoogleMap mMap;
    /**get location user***/
    private static final int REQUEST_CHECK_SETTINGS = 200;
    private static final int START_LOCATION_UPDATE_REQUEST = 101;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final int FINE_LOCATION_REQUEST_CODE = 100;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private double latitudUser;
    private double longitudUser;
    private int ZOOM_MAP = 16;
    private Marker markerUser;
    MapsPresenter presenter;

    //private Button btnExpBottomSheet;
    @BindView(R.id.bottomSheet) LinearLayout bottomSheet;
    BottomSheetBehavior bsb;
    InterstitialAd mInterstitialAd;

    @BindView(R.id.lbl_title) TextView lblName;
    @BindView(R.id.lbl_description) TextView lblDescription;
    @BindView(R.id.lbl_price) TextView lblPrice;
    @BindView(R.id.img_field) ImageView imgField;
    @BindView(R.id.appbarLayout) AppBarLayout toolbar;

    /**SEARCH*/
    boolean SEARCH = false;
    Marker markerSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getGoogleAPIClient();
        PichangaDB db = new PichangaDB(getApplicationContext());
        presenter = new MapsPresenter(db,this);

        bsb = BottomSheetBehavior.from(bottomSheet);
        bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
        initSearchPlaces();
        initAdmob();
    }
    /**START ADMOB***/
    void initAdmob(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        requestNewInterstitial();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                actionClickMarker(markerSelected);
            }
        });
    }

    /**Anuncios intersticiales**/
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(getString(R.string.test_device))//AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
    public void showAnuncio(Marker marker) {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            actionClickMarker(marker);
        }
    }
    /****END ADMOB***/
    @OnClick(R.id.lbl_title)
    public void onClickBottomSheet(){
        bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    private void getGoogleAPIClient(){
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_MAP));
        updateMarkerPosition();
        presenter.getSoccerField();
        this.initBottomSheet();
    }

    void actionClickMarker(Marker marker){
        bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
        setData((SoccerField) marker.getTag());
    }
    private void initBottomSheet(){
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.getTag()==null)return false;
                markerSelected = marker;
                showAnuncio(marker);

                return false;
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(bsb != null)bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        bsb.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                String nuevoEstado="";
                switch(newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        nuevoEstado = "STATE_COLLAPSED";
                        imgField.setVisibility(View.GONE);
                        toolbar.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        nuevoEstado = "STATE_EXPANDED";
                        imgField.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        nuevoEstado = "STATE_HIDDEN";
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        nuevoEstado = "STATE_DRAGGING";
                        imgField.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        imgField.setVisibility(View.VISIBLE);
                        nuevoEstado = "STATE_SETTLING";
                        break;
                }

                Log.i("BottomSheets", "Nuevo estado: " + nuevoEstado);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


    }

    private void updateMarkerPosition(){
        if (Double.isNaN(latitudUser) || latitudUser == 0.0)return;
        LatLng userPosition = new LatLng(latitudUser, longitudUser);
        if (markerUser!=null)  markerUser.remove();

        markerUser =mMap.addMarker(new MarkerOptions().position(userPosition).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_usermarker)));
        if(SEARCH == false){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(userPosition));
            SEARCH = true;
        }

    }

    private void addNewMarker(SoccerField mField){

        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(mField.getLatitude()), Double.parseDouble(mField.getLongitude()))).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
        marker.setTag(mField);
    }


    /***get user's location ***/
    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        if(mGoogleApiClient!= null)
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //requestLocationsUpdate();
        addAndCheckRequest();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestLocationsUpdate();
        } else if (requestCode == START_LOCATION_UPDATE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        }
    }

    private void requestLocationsUpdate() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUEST_CODE);
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void addAndCheckRequest() {
        createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        startLocationUpdates();

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // La configuracion no ha sido satisfactoria pero puede ser arreglada mostrando el siguiente dialogo
                        try {

                            status.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignorar el error
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // La configuracion no ha sido satisfactoria y no hay nada que hacer por lo que el dialogo no se mostrara

                        break;
                }
            }
        });
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, START_LOCATION_UPDATE_REQUEST);
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }



    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateUI();
    }

    private void updateUI() {
        Log.i("current",""+mCurrentLocation.getLatitude()+" "+mCurrentLocation.getLatitude());
        latitudUser = mCurrentLocation.getLatitude();
        longitudUser = mCurrentLocation.getLongitude();
        updateMarkerPosition();
     //   textView.setText(String.format("Latitud = %f, Longitud = %f", mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void getSoccerFields(Cursor listFields) {
        mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_MAP));
        Cursor cursor = listFields;
        SoccerField mField;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            mField = new SoccerField();
          //  String positions = cursor.getString(1);
            mField.setLatitude(cursor.getString(cursor.getColumnIndex(SoccerFieldContract.SoccerEntry.COLUMN_LATITUDE)));
            mField.setLongitude(cursor.getString(cursor.getColumnIndex(SoccerFieldContract.SoccerEntry.COLUMN_LONGITUDE)));
            mField.setId(cursor.getString(cursor.getColumnIndex(SoccerFieldContract.SoccerEntry._ID)));
            mField.setImage(cursor.getString(cursor.getColumnIndex(SoccerFieldContract.SoccerEntry.COLUMN_IMAGE)));
            mField.setName(cursor.getString(cursor.getColumnIndex(SoccerFieldContract.SoccerEntry.COLUMN_NAME)));
            mField.setDescription(cursor.getString(cursor.getColumnIndex(SoccerFieldContract.SoccerEntry.COLUMN_DESCRIPTION)));
            mField.setPrice(cursor.getString(cursor.getColumnIndex(SoccerFieldContract.SoccerEntry.COLUMN_PRICE)));
            mField.setService(cursor.getString(cursor.getColumnIndex(SoccerFieldContract.SoccerEntry.COLUMN_SERVICE)));
            addNewMarker(mField);
            cursor.moveToNext();
        }
// make sure to close the cursor
        cursor.close();
    }

    public List<LatLng> getAllPositions(Cursor listFields) {
        List<LatLng> positionList = new ArrayList<LatLng>();

        Cursor cursor = listFields;

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            String positions = cursor.getString(1);
            double latitude = cursor.getColumnIndex("lat");
            double longitude = cursor.getColumnIndex("long");
            LatLng newLatLng = new LatLng(latitude, longitude);
            positionList.add(newLatLng);
            cursor.moveToNext();
        }
// make sure to close the cursor
        cursor.close();
        return positionList;
    }

    private void setData(SoccerField field){
        this.lblDescription.setText(field.getDescription());
        this.lblName.setText(field.getName());
        this.lblPrice.setText(field.getPrice());
        Picasso.with(getApplicationContext()).load(field.getImage()).into(this.imgField);
    }

    @Override
    public void onBackPressed() {
        if(this.bsb.getState() == BottomSheetBehavior.STATE_EXPANDED){
            this.bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }else{
            super.onBackPressed();
        }

    }

    /*****SEARCH PLACES***/
    private void initSearchPlaces(){
      ///dentro de activity
          PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                SEARCH = true;
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
            }//

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                //  Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            addAndCheckRequest();

        }
    }


}
