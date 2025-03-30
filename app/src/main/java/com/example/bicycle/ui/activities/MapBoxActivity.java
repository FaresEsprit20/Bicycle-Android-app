package com.example.bicycle.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.R;
import com.example.bicycle.Retrofit.MapService;
import com.example.bicycle.models.BikeCyclist;
import com.example.bicycle.models.Circuit;
import com.example.bicycle.models.Community;
import com.example.bicycle.models.Shop;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.delegates.MapDelegateProvider;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapBoxActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

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
        mapView = findViewById(R.id.mapView);

        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, style -> {
            // THIS WILL WORK - verified in v10.16.0
            AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
            pointAnnotationManager = new PointAnnotationManager(
                    (MapDelegateProvider) mapView,  // explicit cast
                    new AnnotationConfig()           // new config
            );

            enableLocationComponent();
            loadAllData();
        });
    }

    private void enableLocationComponent() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            LocationComponentPlugin locationPlugin = LocationComponentUtils.getLocationComponent(mapView);
            locationPlugin.setEnabled(true);

            locationPlugin.updateSettings(settings -> {
                settings.setEnabled(true);
                settings.setPulsingEnabled(true);
                return null; // Required for Java compatibility
            });

        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        }
    }
    private void loadAllData() {
        fillDataShops();
        fillDataCircuits();
        fillDataCommunities();
        fillDataCyclists();
    }

    private void fillDataShops() {
        apiService.getShops().enqueue(new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    shops.addAll(response.body());
                    for (Shop shop : shops) {
                        addMarker(R.drawable.shop_icon, shop.getLatitude(), shop.getLongitude());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                Log.e("MapBoxActivity", "Shops load failed: " + t.getMessage());
            }
        });
    }

    private void fillDataCircuits() {
        apiService.getCircuits().enqueue(new Callback<List<Circuit>>() {
            @Override
            public void onResponse(Call<List<Circuit>> call, Response<List<Circuit>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    circuits.addAll(response.body());
                    for (Circuit circuit : circuits) {
                        addMarker(R.drawable.circuit_icon, circuit.getLatitude(), circuit.getLongitude());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Circuit>> call, Throwable t) {
                Log.e("MapBoxActivity", "Circuits load failed: " + t.getMessage());
            }
        });
    }

    private void fillDataCommunities() {
        apiService.getCommunities().enqueue(new Callback<List<Community>>() {
            @Override
            public void onResponse(Call<List<Community>> call, Response<List<Community>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    communities.addAll(response.body());
                    for (Community community : communities) {
                        addMarker(R.drawable.community_icon, community.getLatitude(), community.getLongitude());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Community>> call, Throwable t) {
                Log.e("MapBoxActivity", "Communities load failed: " + t.getMessage());
            }
        });
    }

    private void fillDataCyclists() {
        apiService.getCyclists().enqueue(new Callback<List<BikeCyclist>>() {
            @Override
            public void onResponse(Call<List<BikeCyclist>> call, Response<List<BikeCyclist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cyclists.addAll(response.body());
                    for (BikeCyclist cyclist : cyclists) {
                        addMarker(R.drawable.cyclist_icon, cyclist.getLatitude(), cyclist.getLongitude());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BikeCyclist>> call, Throwable t) {
                Log.e("MapBoxActivity", "Cyclists load failed: " + t.getMessage());
            }
        });
    }

    private void addMarker(@DrawableRes int drawableId, double latitude, double longitude) {
        if (pointAnnotationManager == null) {
            Log.e("MapBoxActivity", "Annotation manager not initialized");
            return;
        }

        try {
            Bitmap bitmap = drawableToBitmap(this, drawableId);
            pointAnnotationManager.create(
                    new PointAnnotationOptions()
                            .withPoint(Point.fromLngLat(longitude, latitude))
                            .withIconImage(bitmap)
            );
        } catch (Exception e) {
            Log.e("MapBoxActivity", "Marker creation failed", e);
        }
    }

    private Bitmap drawableToBitmap(Context context, @DrawableRes int drawableId) {
        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), drawableId, context.getTheme());
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable not found");
        }

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableLocationComponent();
            } else {
                Toast.makeText(this, "Location permission required", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pointAnnotationManager != null) {
            pointAnnotationManager.destroy();
        }
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }



}