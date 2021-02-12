

package com.launchered.dynamiclayouts;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class FragmentLayout extends FragmentActivity {

    private static final String TAG = "FragmentLayoutACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, " onCreate()");

        setContentView(R.layout.fragment_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, " onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, " onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, " onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, " onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, " onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, " onRestart()");
    }


    public static class DetailsActivity extends FragmentActivity {

        private static final String TAG = "DetailsACTIVITY";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Log.d(TAG, " onCreate()");

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                finish();
                return;
            }

            if (savedInstanceState == null) {
                DetailsFragment details = new DetailsFragment();

                details.setArguments(getIntent().getExtras());

                //
                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, details).commit();
            }
        }

        @Override
        protected void onStart() {
            super.onStart();
            Log.d(TAG, " onStart()");
        }

        @Override
        protected void onResume() {
            super.onResume();
            Log.d(TAG, " onResume()");
        }

        @Override
        protected void onPause() {
            super.onPause();
            Log.d(TAG, " onPause()");
        }

        @Override
        protected void onStop() {
            super.onStop();
            Log.d(TAG, " onStop()");
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            Log.d(TAG, " onDestroy()");
        }

        @Override
        protected void onRestart() {
            super.onRestart();
            Log.d(TAG, " onRestart()");
        }


    }

    public static class TitlesFragment extends ListFragment {
        private static final String TAG = "TitlesFRAGMENT";
        boolean mDualPane;
        int mCurCheckPosition = 0;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Log.d(TAG, "onAttach()");
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d(TAG, "onCreate()");
        }


        @Override
        public void onStart() {
            super.onStart();
            Log.d(TAG, "onStart()");
        }

        @Override
        public void onResume() {
            Log.d(TAG, "onResume()");
            super.onResume();
        }


        @Override
        public void onPause() {
            super.onPause();
            Log.d(TAG, "onPause()");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d(TAG, "onStop()");

        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.d(TAG, " onDestroyView()");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d(TAG, "onDestroy()");
        }

        @Override
        public void onDetach() {
            super.onDetach();
            Log.d(TAG, " onDetach()");
        }


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            Log.d(TAG, "onActivityCreated()");

            List<String> supplierNames = Arrays.asList("Current Location", "Dublin", "Kerry", "Belfast",
                    "Cork","Galway","Wexford");
            setListAdapter(new ArrayAdapter<String>(Objects.requireNonNull(getActivity()),
                    android.R.layout.simple_list_item_activated_1,
                    supplierNames));

            View detailsFrame = getActivity().findViewById(R.id.details);


            mDualPane = detailsFrame != null
                    && detailsFrame.getVisibility() == View.VISIBLE;


            if (savedInstanceState != null) {
                mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
            }

            if (mDualPane) {
                showDetails(mCurCheckPosition);
            } else {
                getListView().setItemChecked(mCurCheckPosition, true);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("curChoice", mCurCheckPosition);
        }


        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {

            showDetails(position);
        }

        void showDetails(int index) {
            mCurCheckPosition = index;


            if (mDualPane) {

                getListView().setItemChecked(index, true);

                DetailsFragment details = (DetailsFragment) getFragmentManager()
                        .findFragmentById(R.id.details);
                if (details == null || details.getShownIndex() != index) {
                    details = DetailsFragment.newInstance(index);
                    FragmentTransaction ft = getFragmentManager()
                            .beginTransaction();
                    ft.replace(R.id.details, details);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }

            } else {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailsActivity.class);

                intent.putExtra("index", index);

                startActivity(intent);
            }
        }
    }

    public static class DetailsFragment extends Fragment {

        MapView mMapView;
        private GoogleMap googleMap;
        private static final int REQUEST_LOCATION = 1;
        LocationManager locationManager;

        private static final String TAG = "DetailsFRAGMENT";
        public static DetailsFragment newInstance(int index) {
            DetailsFragment f = new DetailsFragment();

            Bundle args = new Bundle();
            args.putInt("index", index);
            f.setArguments(args);

            return f;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Log.d(TAG, "onAttach()");
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d(TAG, "onCreate()");
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.d(TAG, "onStart()");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d(TAG, "onStop()");

        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.d(TAG, " onDestroyView()");
        }

        @Override
        public void onResume() {
            super.onResume();
            mMapView.onResume();
        }

        @Override
        public void onPause() {
            super.onPause();
            mMapView.onPause();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
//            mMapView.onDestroy();
        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();
            mMapView.onLowMemory();
        }

        @Override
        public void onDetach() {
            super.onDetach();
            Log.d(TAG, " onDetach()");
        }

        public int getShownIndex() {
            return getArguments().getInt("index", 0);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.map_layout, container, false);

            mMapView = (MapView) rootView.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume();
            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

                        return;
                    }
                    googleMap.setMyLocationEnabled(true);

                    LatLng location = null;

                    if (getShownIndex()==1){
                        location = new LatLng(53.347860, -6.272487);
                        googleMap.addMarker(new MarkerOptions().position(location));
                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }else if (getShownIndex()==2){
                        location = new LatLng(52.264007, -9.686990);
                        googleMap.addMarker(new MarkerOptions().position(location));
                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }else if (getShownIndex()==3){
                        location = new LatLng(54.602755, -5.945180);
                        googleMap.addMarker(new MarkerOptions().position(location));
                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }else if (getShownIndex()==4){
                        location = new LatLng(51.892171, -8.475068);
                        googleMap.addMarker(new MarkerOptions().position(location));
                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }else if (getShownIndex()==5){
                        location = new LatLng(53.276533, -9.069362);
                        googleMap.addMarker(new MarkerOptions().position(location));
                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }else if (getShownIndex()==6){
                        location = new LatLng(52.336521, -6.462855);
                        googleMap.addMarker(new MarkerOptions().position(location));
                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }else {
                        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            OnGPS();
                        } else {
                            final LocationListener mLocationListener = new LocationListener() {
                                @Override
                                public void onLocationChanged(final Location locationGPS) {
                                    if (locationGPS != null) {
                                        LatLng mlocation =new LatLng (locationGPS.getLatitude(), locationGPS.getLongitude());
                                        googleMap.addMarker(new MarkerOptions().position(mlocation));
                                        // For zooming automatically to the location of the marker
                                        CameraPosition cameraPosition = new CameraPosition.Builder().target(mlocation).zoom(12).build();
                                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                    } else {
                                        Toast.makeText(getActivity(), "Unable to find location.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onStatusChanged(String provider, int status, Bundle extras) {

                                }

                                @Override
                                public void onProviderEnabled(String provider) {

                                }

                                @Override
                                public void onProviderDisabled(String provider) {

                                }
                            };
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, (android.location.LocationListener) mLocationListener);

                        }
                    }
                }
            });

            return rootView;
        }

        private void OnGPS() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

}
