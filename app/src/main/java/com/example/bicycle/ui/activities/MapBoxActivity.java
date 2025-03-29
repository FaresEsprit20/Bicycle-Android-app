package com.example.bicycle.ui.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.R;
import com.example.bicycle.Retrofit.RetrofitClient;
import com.example.bicycle.Retrofit.MapService;
import com.example.bicycle.models.BikeCyclist;
import com.example.bicycle.models.Circuit;
import com.example.bicycle.models.Community;
import com.example.bicycle.models.Shop;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils;
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings;


import java.util.ArrayList;


public class MapBoxActivity extends AppCompatActivity {

    private MapView mapView;
    private MapService apiService;
    private ArrayList<Shop> shops = new ArrayList<>();
    private ArrayList<BikeCyclist> cyclists = new ArrayList<>();
    private ArrayList<Community> communities = new ArrayList<>();
    private ArrayList<Circuit> circuits = new ArrayList<>();
    private PointAnnotationManager pointAnnotationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_box);

        // Initialize MapView
        mapView = findViewById(R.id.mapView);

        // Initialize Retrofit service
        apiService = RetrofitClient.getClient().create(MapService.class);

        // Initialize annotation manager
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
        pointAnnotationManager = new PointAnnotationManager(annotationPlugin, new AnnotationConfig());

        // Load map style and setup
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, style -> {
            setupInitialMapPosition();
            enableLocationComponent(style);

            // Fetch data after map is ready
            fetchAllMapData();
        });
    }

    private void setupInitialMapPosition() {
        // Set initial camera position (centered on Tunisia)
        CameraOptions cameraOptions = new CameraOptions.Builder()
                .center(Point.fromLngLat(10.0, 36.0))
                .zoom(6.0)
                .build();
        mapView.getMapboxMap().setCamera(cameraOptions);
    }

    private void enableLocationComponent(@NonNull Style style) {
        LocationComponentPlugin locationComponent = LocationComponentUtils.getLocationComponent(mapView);

        // Check and request location permissions if needed
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Configure location component
            locationComponent.setEnabled(true);
            LocationComponentSettings settings = locationComponent.getSettings();
            settings.setEnabled(true);
            settings.setPulsingEnabled(true);
            locationComponent.updateSettings(settings);
        } else {
            // Handle permission request
            requestLocationPermission();
        }
    }

    private void requestLocationPermission() {
        // Implement your permission request logic here
        // This should use ActivityResultContracts.RequestPermission in modern Android
    }

    private void fetchAllMapData() {
        fillDataShops();
        fillDataCircuits();
        fillDataCommunities();
        fillDataCyclists();
    }

    private void addMarkersToMap() {
        // Clear existing markers
        pointAnnotationManager.deleteAll();

        // Add all markers
        addShopMarkers();
        addCommunityMarkers();
        addCyclistMarkers();
        addCircuitMarkers();
    }

    private void addShopMarkers() {
        for (Shop shop : shops) {
            addMarker(
                    shop.getLatitude(),
                    shop.getLongitude(),
                    shop.getTitle(),
                    R.drawable.blue_marker
            );
        }
    }

    private void addCommunityMarkers() {
        for (Community community : communities) {
            addMarker(
                    community.getLatitude(),
                    community.getLongitude(),
                    community.getTitle(),
                    R.drawable.green_marker
            );
        }
    }

    private void addCyclistMarkers() {
        for (BikeCyclist cyclist : cyclists) {
            addMarker(
                    cyclist.getLatitude(),
                    cyclist.getLongitude(),
                    cyclist.getTitle(),
                    R.drawable.yellow_marker
            );
        }
    }


}