package org.apache.cordova.getlocation;

import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.apache.cordova.CallbackContext;


import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import android.location.Location;
import com.google.android.gms.location.LocationServices;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import org.json.JSONObject;

public class GetLocation extends CordovaPlugin {

    protected static final String TAG = "Location";
    FusedLocationProviderClient mFusedLocationClient;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        if (action.equals("getLocation")) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(cordova.getActivity());
            getLocation(callbackContext);
            return true;
        }
        callbackContext.error("Invalid action: " + action);
        return false;
    }

    private void getLocation(final CallbackContext callbackContext){
        cordova.getThreadPool().execute(() -> {
            try {
                if (checkPermissions()) {
                    if (isLocationEnabled()) {
                        try {
                            mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                                Location location = task.getResult();
                                JSONObject loc = new JSONObject();
                                try{
                                    loc.put("latitude", location.getLatitude());
                                    loc.put("longitude", location.getLongitude());
                                    callbackContext.success(loc);
                                }
                                catch (Exception e){
                                    callbackContext.error(e.toString());
                                }
                            });
                        }
                        catch (SecurityException e){
                            callbackContext.error(e.toString());
                        }
                    } else {
                        callbackContext.error("Location not enabled.");
                    }
                } else {
                    callbackContext.error("Permission not available.");
                }

            } catch (Exception e) {
                callbackContext.error(e.toString());
            }
        });
    }
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(
                cordova.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(cordova.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}
