package com.supersoniq.aac.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.supersoniq.aac.R;

import static com.supersoniq.aac.Utils.findById;

public class CoffeeShopsFragment extends Fragment implements OnMapReadyCallback {

    @NonNull
    private CoffeeShopsViewModel model;
    @NonNull
    private CoffeeShopsAdapter coffeeShopsAdapter;
    @NonNull
    private RecyclerView coffeeShopsList;
    @NonNull
    private FrameLayout progress;
    private GoogleMap mMap;

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

        model.observeCoffeeShops().observe(this, coffeeShops ->
                coffeeShopsAdapter.submitList(coffeeShops));

        model.observeProgress().observe(this, bool -> {
            if (bool != null && bool) {
                progress.setVisibility(View.VISIBLE);
            } else {
                progress.setVisibility(View.GONE);
            }
        });
        model.init();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
