package com.hrc.myapplication.common;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * get system service
 */

public class SystemUtil {
    private static SystemUtil systemUtils;
    private Context context;
    private TelephonyManager telManager;
    private ConnectivityManager connManager;
    private LocationManager locationManager;
    private String position;

    private SystemUtil(Context context) {
        this.context = context;
        telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * only have one SystemUtil
     *
     * @param context context
     * @return
     */
    public static SystemUtil getInstance(Context context) {
        if (systemUtils == null) {
            systemUtils = new SystemUtil(context);
        }
        return systemUtils;
    }

    /**
     * true if have networke
     *
     * @return true if have
     */
    public boolean isNetConn() {
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * get the sim type
     *
     * @return "移动"or"联通"or"电信"
     */
    public String simType() {
        String simOperator = telManager.getSimOperator();
        String type = "";
        if ("46000".equals(simOperator)) {
            type = "移动";
        } else if ("46002".equals(simType())) {
            type = "移动";
        } else if ("46001".equals(simType())) {
            type = "联通";
        } else if ("46003".equals(simType())) {
            type = "电信";
        }
        return type;
    }

    /**
     * 获取手机IMEI 号码
     *
     * @return devices identifiers
     */
    public String getIMEI() {
        return telManager.getDeviceId();
    }

    public String getPosition() {
        return this.position;
    }

    public void locatPosition() {
        //请求调用的权限可能被用户拒绝，所以需要处理可能出现的SecurityException e
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double longitude=location.getLongitude();
                    double latitude=location.getLatitude();
                    //AsyncHttpClient client=new AsyncHttpClient();
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
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取手机的IMEI值
     * @param context
     * @return
     */
    public static String getIMEI(Context context){
        TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
