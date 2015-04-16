package com.wiseweb.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.LocationClient;

public class LocationService extends Service {
	private static String TAG = "LocationService";
	public LocationClient mLocationClient = null;
	private String city = null;
	private LocationManager locationManager;
	private double latitude = 0;
	private double longitude = 0;
	private SharedPreferences cityPreferences;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("LocationService OnCreate()");
		// 获取经纬度
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
		}
		// 根据经纬度得到城市名称
		Geocoder geocoder = new Geocoder(LocationService.this,
				Locale.getDefault());
		System.out.println("latitude" + latitude);
		System.out.println("longitude" + longitude);
		List<Address> addresses = null;
		try {
			addresses = geocoder.getFromLocation(location.getLatitude(),
					location.getLongitude(), 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (addresses != null && addresses.size() > 0) {
			Address address = addresses.get(0);
			city = address.getLocality();
			System.out.println("city:" + city);
			//定位的城市
			cityPreferences = getSharedPreferences("locationCity", Context.MODE_PRIVATE);
			// String str = cityPreferences.getString("city", null);
			SharedPreferences.Editor editor = cityPreferences.edit();
			editor.putString("locationCity", city);
			editor.commit();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		System.out.println("LocationService---onDestroy()");
		super.onDestroy();
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		super.onRebind(intent);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		System.out.println("LocationService---onStart()");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("LocationService---onStartCommand()");
		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

}
