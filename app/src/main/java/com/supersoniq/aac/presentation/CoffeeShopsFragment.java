package com.supersoniq.aac.presentation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.supersoniq.aac.R;
import com.supersoniq.aac.model.CoffeeShop;
import com.supersoniq.aac.model.Place;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.supersoniq.aac.Utils.findById;

public class CoffeeShopsFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @NonNull
    private CoffeeShopsViewModel model;
    @NonNull
    private CoffeeShopsAdapter coffeeShopsAdapter;
    @NonNull
    private RecyclerView coffeeShopsList;
    @NonNull
    private FrameLayout progress;
    @Nullable
    private GoogleMap googleMap;
    @Nullable
    private GoogleApiClient mGoogleApiClient;
    @Nullable
    private Location mLastLocation;
    @Nullable
    private FusedLocationProviderClient fusedLocationClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_coffee_shops, container, false);
        model = ViewModelProviders.of(this).get(CoffeeShopsViewModel.class);
        coffeeShopsAdapter = new CoffeeShopsAdapter();
        coffeeShopsList = findById(view, R.id.coffee_shops_list);
        progress = findById(view, R.id.progress);
        coffeeShopsList.setLayoutManager(new LinearLayoutManager(getContext()));
        coffeeShopsList.setAdapter(coffeeShopsAdapter);
        initMap();
        observeViewModel();
        model.init();
        return view;
    }

    private void observeViewModel() {
        model.observeCoffeeShops().observe(this, coffeeShops ->
                coffeeShopsAdapter.submitList(coffeeShops));
        model.observeProgress().observe(this, bool -> {
            if (bool != null && bool) {
                progress.setVisibility(View.VISIBLE);
            } else {
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void initMap() {
        final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        model.observeCoffeeShops().observe(this, coffeeShops -> {
            for (final CoffeeShop coffeeShop : coffeeShops) {
                for (final Place place : coffeeShop.getPlaces()) {
                    final LatLng coffeeCoords = new LatLng(place.getCoordinates().getX(),
                            place.getCoordinates().getY());
                    this.googleMap.addMarker(new MarkerOptions().position(coffeeCoords).title(coffeeShop.getName()));
                }
            }
        });
    }

    @Override
    public void onStart() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    public void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable final Bundle bundle) {
        if (checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION) ||
                    shouldShowRequestPermissionRationale(
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                Toast.makeText(App.getAppContext(), "Необходимо разрешение для установления вашего местонахождения.", Toast.LENGTH_LONG).show();
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            findMyLocation();
        }
    }


    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           @NonNull final String permissions[],
                                           final int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    findMyLocation();
                } else {
                }
                break;
            }
            default:
                break;
        }
    }

    public void findMyLocation() {
        if (checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), location -> {
                    if (location != null && googleMap != null) {
                        googleMap.setMyLocationEnabled(true);
                        mLastLocation = location;
                        googleMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(new LatLng(mLastLocation.getLatitude(),
                                        mLastLocation.getLongitude()), 14f));
                    }
                });
    }

    @Override
    public void onConnectionSuspended(final int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull final ConnectionResult connectionResult) {

    }
}
