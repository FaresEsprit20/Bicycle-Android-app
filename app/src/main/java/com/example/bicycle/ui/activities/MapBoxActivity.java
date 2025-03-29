package com.example.bicycle.ui.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


import com.example.R;
import com.example.bicycle.Retrofit.MapService;
import com.example.bicycle.models.BikeCyclist;
import com.example.bicycle.models.Circuit;
import com.example.bicycle.models.Community;
import com.example.bicycle.models.Shop;

import java.util.ArrayList;
import java.util.List;


public class MapBoxActivity extends AppCompatActivity {

    private  MapView mapView;
    private MapService apiService;
    private  ArrayList<Shop> shops = new ArrayList<Shop>();
    private  ArrayList<BikeCyclist> cyclists = new ArrayList<BikeCyclist>();
    private  ArrayList<Community> communities = new ArrayList<Community>();
    private  ArrayList<Circuit> circuits = new ArrayList<Circuit>();
    private MapboxMap mbMap;
    PermissionsManager permissionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = RetrofitClient.getClient().create(MapService.class);
        fillDataShops();
        fillDataCircuits();
        fillDataCommunities();
        fillDataCyclists();
// Mapbox access token is configured here. This needs to be called either in your application
// object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.accessToken));

// This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_map_box);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {




                //static markers and positions
               /* Shop shop1 = new Shop("bike shop 1",36.553015,10.592774);
                Shop shop2 = new Shop("bike shop 2",35.499414,10.824846);
                Shop shop3 = new Shop("bike shop 3",35.945377,9.451555);
                shops.add(shop1);
                shops.add(shop2);
                shops.add(shop3);
                Community com1 = new Community("Community 1", 37.276943 , 9.748186);
                Community com2 = new Community("Community 2", 35.427828, 10.934709);
                Community com3 = new Community("Community 3", 36.140808, 8.847307);
                communities.add(com1);
                communities.add(com2);
                communities.add(com3);
                BikeCyclist cyclist1 = new BikeCyclist("Bike Cyclist 1", 35.785118 , 10.000871);
                BikeCyclist cyclist2 = new BikeCyclist("Bike Cyclist 2", 36.900100  , 10.901750);
                BikeCyclist cyclist3 = new BikeCyclist("Bike Cyclist 3",35.900893 , 9.154924);
                cyclists.add(cyclist1);
                cyclists.add(cyclist2);
                cyclists.add(cyclist3);

                Circuit c1 = new Circuit("Circuit 1", 33.773035, 10.857805);
                Circuit c2 = new Circuit("Circuit 2", 36.882526, 8.957170);
                Circuit c3 = new Circuit("Circuit 3", 36.574341, 8.429827);
                circuits.add(c1);
                circuits.add(c2);
                circuits.add(c3);*/


                List<MarkerOptions> shopsOptions = new ArrayList<>();
                List<MarkerOptions> communitiesOptions = new ArrayList<>();
                List<MarkerOptions> cyclistsOptions = new ArrayList<>();
                List<MarkerOptions> circuitsOptions = new ArrayList<>();


                for(Shop s : shops){
                    shopsOptions.add(new MarkerOptions().position(new LatLng(s.getLatitude(),s.getLongitude())).setTitle(s.getTitle()) );
                }
                IconFactory iconFactory = IconFactory.getInstance(MapBoxActivity.this);
                Icon blueicon = iconFactory.fromResource(R.drawable.blue_marker);
                blueicon = IconFactory.recreate(blueicon.getId(), Bitmap.createScaledBitmap(blueicon.getBitmap(), 90, 90, false));
                for(Community s : communities){
                    communitiesOptions.add(new MarkerOptions().icon(blueicon).position(new LatLng(s.getLatitude(),s.getLongitude())).setTitle(s.getTitle()) );
                }

                IconFactory iconYellowFactory = IconFactory.getInstance(MapBoxActivity.this);
                Icon yellowicon = iconFactory.fromResource(R.drawable.yellow_marker);
                yellowicon = IconFactory.recreate(yellowicon.getId(), Bitmap.createScaledBitmap(yellowicon.getBitmap(), 90, 90, false));
                for(BikeCyclist s : cyclists){
                    cyclistsOptions.add(new MarkerOptions().icon(yellowicon).position(new LatLng(s.getLatitude(),s.getLongitude())).setTitle(s.getTitle()) );
                }

                IconFactory iconDarkFactory = IconFactory.getInstance(MapBoxActivity.this);
                Icon darkicon = iconFactory.fromResource(R.drawable.dark_marker);
                darkicon = IconFactory.recreate(darkicon.getId(), Bitmap.createScaledBitmap(darkicon.getBitmap(), 90, 90, false));
                for(Circuit s : circuits){
                    circuitsOptions.add(new MarkerOptions().icon(darkicon).position(new LatLng(s.getLatitude(),s.getLongitude())).setTitle(s.getTitle()) );
                }
                Log.e("Community LIST", communities.toString());
                Log.e("Community OPTIONS", communitiesOptions.toString());
                mapboxMap.addMarkers(shopsOptions);
                mapboxMap.addMarkers(cyclistsOptions);
                mapboxMap.addMarkers(communitiesOptions);
                mapboxMap.addMarkers(circuitsOptions);



                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments

                        //enableLocationComponent(style);

                    }
                });

            }
        });
    }






    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(MapBoxActivity.this)) {
            LocationComponent locationComponent = mbMap.getLocationComponent();
            locationComponent.activateLocationComponent(MapBoxActivity.this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(new PermissionsListener() {
                @Override
                public void onExplanationNeeded(List<String> permissionsToExplain) {
                    Toast.makeText(MapBoxActivity.this, "location not enabled", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onPermissionResult(boolean granted) {
                    if (granted) {
                        mbMap.getStyle(new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {
                              //  initMapStuff(style);
                            }
                        });
                    } else {
                        Toast.makeText(MapBoxActivity.this, "Location services not allowed", Toast.LENGTH_LONG).show();
                    }
                }
            });
            permissionsManager.requestLocationPermissions(MapBoxActivity.this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();



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
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }



    public void fillDataShops(){

        Call<List<Shop>> call = apiService.getShops();
        call.enqueue(new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                if(response.isSuccessful()){
                    shops.addAll(response.body());
                    Log.e("SHOP LIST", shops.toString());
                }
            }
            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }

    public void fillDataCircuits(){

        Call<List<Circuit>> call = apiService.getCircuits();
        call.enqueue(new Callback<List<Circuit>>() {
            @Override
            public void onResponse(Call<List<Circuit>> call, Response<List<Circuit>> response) {
                if(response.isSuccessful()){
                    circuits.addAll(response.body());
                    Log.e("Circuit LIST", circuits.toString());
                }
            }
            @Override
            public void onFailure(Call<List<Circuit>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }

    public void fillDataCommunities(){

        Call<List<Community>> call = apiService.getCommunities();
        call.enqueue(new Callback<List<Community>>() {
            @Override
            public void onResponse(Call<List<Community>> call, Response<List<Community>> response) {
                if(response.isSuccessful()){
                    communities.addAll(response.body());
                    Log.e("Community LIST", communities.toString());
                }
            }
            @Override
            public void onFailure(Call<List<Community>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }

    public void fillDataCyclists(){

        Call<List<BikeCyclist>> call = apiService.getCyclists();
        call.enqueue(new Callback<List<BikeCyclist>>() {
            @Override
            public void onResponse(Call<List<BikeCyclist>> call, Response<List<BikeCyclist>> response) {
                if(response.isSuccessful()){
                    cyclists.addAll(response.body());
                    Log.e("Cyclist LIST", cyclists.toString());
                }
            }
            @Override
            public void onFailure(Call<List<BikeCyclist>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }


    public static Icon drawableToIcon(@NonNull Context context, @DrawableRes int id) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(context.getResources(), id, context.getTheme());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return IconFactory.getInstance(context).fromBitmap(bitmap);
    }






}