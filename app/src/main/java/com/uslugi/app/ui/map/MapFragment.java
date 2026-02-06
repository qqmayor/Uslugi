package com.uslugi.app.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uslugi.app.R;
import com.uslugi.app.databinding.FragmentMapBinding;
import com.uslugi.app.domain.model.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapFragment extends Fragment {
    private static final int REQUEST_LOCATION = 2001;
    private FragmentMapBinding binding;
    private MapViewModel viewModel;
    private GoogleMap googleMap;
    private final Map<Marker, Service> markerServiceMap = new HashMap<>();
    private FusedLocationProviderClient locationClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        locationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        NavController navController = NavHostFragment.findNavController(this);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.mapContainer, mapFragment)
                .commit();

        mapFragment.getMapAsync(map -> {
            googleMap = map;
            setupMarkers();
            enableLocation();
            googleMap.setOnMarkerClickListener(marker -> {
                Service service = markerServiceMap.get(marker);
                if (service != null) {
                    Bundle args = new Bundle();
                    args.putString("serviceId", service.getId());
                    navController.navigate(R.id.action_map_to_detail, args);
                }
                return true;
            });
        });

        binding.backButton.setOnClickListener(v -> navController.navigateUp());
    }

    private void setupMarkers() {
        viewModel.getServices().observe(getViewLifecycleOwner(), this::renderMarkers);
    }

    private void renderMarkers(List<Service> services) {
        if (googleMap == null || services == null) {
            return;
        }
        googleMap.clear();
        markerServiceMap.clear();
        for (Service service : services) {
            LatLng point = new LatLng(service.getLatitude(), service.getLongitude());
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title(service.getName()));
            if (marker != null) {
                markerServiceMap.put(marker, service);
            }
        }
        if (!services.isEmpty()) {
            Service first = services.get(0);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(first.getLatitude(), first.getLongitude()), 12f));
        }
    }

    private void enableLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }
        googleMap.setMyLocationEnabled(true);
        locationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                LatLng user = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(user, 13f));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableLocation();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
