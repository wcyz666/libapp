package com.example.libapp;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class NetReceiver extends BroadcastReceiver {
	
	private static boolean TRIED = false;

@Override
public void onReceive(Context context, Intent intent) {
	  
		if (TRIED == true)
			return;
		
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isConnected = activeNetInfo != null
				&& activeNetInfo.isAvailable();

		if (isConnected) {
			TRIED = true;
			WifiManager wifiManager = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			String networkSSID = "CUHK";
			WifiConfiguration conf = new WifiConfiguration();
			conf.SSID = "\"" + networkSSID + "\"";
			conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
			for (WifiConfiguration i : list) {
				if (i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
					wifiManager.disconnect();
					wifiManager.enableNetwork(i.networkId, true);
					wifiManager.reconnect();
					break;
				}
			}
			Toast.makeText(context, "Wifi Connected", Toast.LENGTH_LONG).show();
		} else
			Toast.makeText(context, "Not Connected", Toast.LENGTH_LONG).show();
	}
}