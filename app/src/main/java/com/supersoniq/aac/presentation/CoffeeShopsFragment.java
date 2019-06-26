package com.supersoniq.aac.presentation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.supersoniq.aac.R;
import com.supersoniq.aac.model.CoffeeShop;
import com.supersoniq.aac.utils.Utils;
import com.supersoniq.aac.model.CoffeeNetwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.supersoniq.aac.utils.Utils.findById;

public class CoffeeShopsFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMarkerClickListener {

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
        coffeeShopsAdapter = new CoffeeShopsAdapter(this::coffeeNetworkClicked);
        coffeeShopsList = findById(view, R.id.coffee_shops_list);
        progress = findById(view, R.id.progress);
        coffeeShopsList.setLayoutManager(new LinearLayoutManager(getContext()));
        coffeeShopsList.setAdapter(coffeeShopsAdapter);
        initMap();
        observeViewModel();
        model.init();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        model.observeCoffeeShops().observe(this, coffeeShops -> {
            if (this.googleMap != null) {
                for (final CoffeeNetwork coffeeNetwork : coffeeShops) {
                    for (final CoffeeShop coffeeShop : coffeeNetwork.getCoffeeShops()) {
                        addMarker(coffeeNetwork, coffeeShop, googleMap);
                    }
                }
            }
        });
        googleMap.setOnMarkerClickListener(this);

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
    @Override
    public void onMyLocationClick(@NonNull final Location location) {
        // нажат маркер текущей локации (не кнопка)
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // нажата кнопка перемещения камеры на текущую локацию
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onConnectionSuspended(final int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull final ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //TODO Запровайдить какое именно место из сети было нажато
        coffeeNetworkClicked((CoffeeNetwork) marker.getTag());
        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    private void coffeeNetworkClicked(CoffeeNetwork coffeeNetwork) {
        //TODO Смена фрагмента в боттомщите
        //TODO Махинации на карте

    }

    private void observeViewModel() {
        model.observeCoffeeShops().observe(this, coffeeShops ->
                coffeeShopsAdapter.submitList(coffeeShops));
        model.observeProgress().observe(this, bool -> {
            if (Boolean.TRUE.equals(bool)) {
                progress.setVisibility(View.VISIBLE);
            } else {
                progress.setVisibility(View.GONE);
            }
        });
        model.observeMapType().observe(this, type -> {
            if (googleMap != null) {
                googleMap.setMapType(type);
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

    private void addMarker(@NonNull final CoffeeNetwork coffeeNetwork, @NonNull final CoffeeShop coffeeShop, @NonNull final GoogleMap googleMap) {
        final LatLng coffeeCoords = new LatLng(coffeeShop.getCoordinates().getX(),
                coffeeShop.getCoordinates().getY());
        final Marker marker  = googleMap.addMarker(new MarkerOptions().position(coffeeCoords).title(coffeeNetwork.getName()));
        marker.setTag(coffeeNetwork);
        marker.setIcon(BitmapDescriptorFactory
                .fromBitmap(Utils.getBitmapFromVectorDrawable(getActivity(), R.drawable.ic_circle_marker)));
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
                        googleMap.setOnMyLocationButtonClickListener(this);
                        googleMap.setOnMyLocationClickListener(this);
                        mLastLocation = location;
                        googleMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(new LatLng(mLastLocation.getLatitude(),
                                        mLastLocation.getLongitude()), 14f));
                    }
                });
    }
}
