package com.godchin.codelife.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.util.ArrayList;

/**
 * Created by Jean on 2015/11/10.
 */
public class WiFiReceivers extends BroadcastReceiver {

    private static ArrayList<IWiFiStatusInterface> list = new ArrayList<IWiFiStatusInterface>();

    public interface IWiFiStatusInterface {
        void onWiFiStateStatusChange();

        void onWiFiRSSIStatusChange();

        void onWiFiConnectStatusChange();
    }

    public static void addWiFiStatusInterface(IWiFiStatusInterface iWiFiStatusInterface) {
        if (!list.contains(iWiFiStatusInterface)) {
            list.add(iWiFiStatusInterface);
        }
    }

    public static void removeWiFiStatusInterface(IWiFiStatusInterface iWiFiStatusInterface) {
        if (list.contains(iWiFiStatusInterface)) {
            list.remove(iWiFiStatusInterface);
        }
    }

    public static boolean isMobileConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mobileNetInfo.isConnected();
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetInfo.isConnected();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
            for (IWiFiStatusInterface listener : list) {
                listener.onWiFiRSSIStatusChange();
            }
        } else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            for (IWiFiStatusInterface listener : list) {
                listener.onWiFiConnectStatusChange();
            }
        } else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            for (IWiFiStatusInterface listener : list) {
                listener.onWiFiStateStatusChange();
            }
        }
    }
}
