package me.daylight.ktzs.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import androidx.core.app.ActivityCompat;

/**
 * @author Daylight
 * @date 2019/03/09 01:36
 */
public class LocationUtil {
    public static void getLocation(Context context, LocationChangedListener listener){
        //从GPS获取最近的定位信息
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            DialogUtil.showTipDialog(context, QMUITipDialog.Builder.ICON_TYPE_FAIL,"请授予定位权限",true);
            return;
        }

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            listener.onChanged(location);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 8, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    listener.onChanged(location);
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
            });
        }

    }

    public interface LocationChangedListener {
        void onChanged(Location location);
    }
}
