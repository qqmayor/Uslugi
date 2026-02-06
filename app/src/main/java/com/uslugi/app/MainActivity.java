package com.uslugi.app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, ServiceAdapter.OnReviewAddedListener {
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    private GoogleMap googleMap;
    private final List<ServiceItem> serviceItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        Bundle mapViewBundle = savedInstanceState != null
                ? savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
                : null;
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        setupServiceList();
    }

    private void setupServiceList() {
        serviceItems.add(new ServiceItem(
                "Маникюр",
                "Уход и дизайн для рук",
                4.8f,
                new LatLng(55.7558, 37.6173),
                new ArrayList<>(Arrays.asList("Отличный сервис!", "Красиво и быстро."))
        ));
        serviceItems.add(new ServiceItem(
                "Педикюр",
                "Комфорт и красота для ног",
                4.6f,
                new LatLng(55.7512, 37.6184),
                new ArrayList<>(Arrays.asList("Уютная студия."))
        ));
        serviceItems.add(new ServiceItem(
                "Цветы",
                "Быстрая доставка букетов",
                4.9f,
                new LatLng(55.7585, 37.6050),
                new ArrayList<>(Arrays.asList("Свежие цветы.", "Понравилась упаковка."))
        ));
        serviceItems.add(new ServiceItem(
                "Косметика",
                "Бренды и уход для кожи",
                4.7f,
                new LatLng(55.7615, 37.6082),
                new ArrayList<>(Arrays.asList("Большой выбор."))
        ));

        RecyclerView recyclerView = findViewById(R.id.servicesRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ServiceAdapter(serviceItems, this));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        for (ServiceItem item : serviceItems) {
            googleMap.addMarker(new MarkerOptions()
                    .position(item.getLocation())
                    .title(item.getTitle()));
        }
        if (!serviceItems.isEmpty()) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(serviceItems.get(0).getLocation(), 12f));
        }
    }

    @Override
    public void onReviewAdded(ServiceItem item, String reviewText) {
        item.addReview(reviewText);
        Toast.makeText(this, "Отзыв добавлен", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
