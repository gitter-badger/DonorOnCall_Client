package com.donor.oncall.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.donor.oncall.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by prashanth on 29/1/16.
 */
public class MapViewFragment extends BaseFragment implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener{
    private GoogleMap mMap;
    private   View rootView=null;
    private boolean showRecipientAlert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.map_screen, container, false);
        SupportMapFragment mapFragment =
                (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        if (getArguments() !=null){
        showRecipientAlert = getArguments().getBoolean("showRecipientAlert",false);
        }
        mapFragment.getMapAsync(this);

            return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView.findViewById(R.id.requestBlood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceViewFragment(new RequestBloodFragment(), false);
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap map) {
        Toast.makeText(getContext(), "OnMap ready", Toast.LENGTH_SHORT).show();
        mMap = map;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        if (showRecipientAlert)
            setShowRecipientAlertDialog();

    }

    public void setShowRecipientAlertDialog() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(getContext())
                        .setTitle("Your Request has been approved !")
                        .setMessage("Your donor request has been approved.You're all set.Track your current donor using map.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }, 10000);
    }
            /**
             * Enables the My Location layer if the fine location permission has been granted.
             */


            @Override
            public boolean onMyLocationButtonClick() {

                LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Location services not Enabled");  // GPS not found
                    builder.setMessage("Enable Gps Location to get your current location"); // Want to enable?
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
                    builder.setNegativeButton(android.R.string.no, null);
                    builder.create().show();
                }


                // Return false so that we don't consume the event and the default behavior still occurs
                // (the camera animates to the user's current position).
                return false;
            }


            @Override
            public void onResume() {
                super.onResume();
            }


        }
