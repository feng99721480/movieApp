package com.wiseweb.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class LocationService extends Service {
	private static String TAG = "LocationService";
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListerner();
	private String city = null;
	private LocationManager locationManager;
	private double latitude = 0;
	private double longitude = 0;
	private SharedPreferences cityPreferences;
	boolean isFirstLoc = true;// 是否首次定位
	public BDLocation location = new BDLocation();
	private Intent intent = new Intent("com.wiseweb.movie.RECEIVER");

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
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		// option.disableCache(true);//禁止启用缓存定位
		// option.setPoiNumber(5); //最多返回POI个数
		// option.setPoiDistance(1000); //poi查询距离
		// option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息
		mLocationClient.setLocOption(option);
		//发起定位请求
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.requestLocation(); 
			mLocationClient.start();
		}
			
		else 
			Log.d("LocSDK3", "locClient is null or not started");
		
		//通过得到的经纬度获得城市名
		Geocoder geocoder = new Geocoder(this, Locale
				.getDefault());
		List<Address> addresses = null;
		try {
			addresses = geocoder.getFromLocation(
					location.getLatitude(), location.getLongitude(), 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (addresses != null && addresses.size() > 0) {
			Address address = addresses.get(0);
			city = address.getLocality(); 
			System.out.println("city:" + city);
		}
		//向activity发送广播消息
		intent.putExtra("name", city);
		sendBroadcast(intent);
	}

	public class MyLocationListerner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("time:");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			System.out.println(sb.toString());
			
		}

		// public void onReceivePoi(BDLocation poiLocation) {
		// if (poiLocation == null) {
		// return;
		// }
		// StringBuffer sb = new StringBuffer(256);
		// sb.append("Poi time : ");
		// sb.append(poiLocation.getTime());
		// sb.append("\nerror code : ");
		// sb.append(poiLocation.getLocType());
		// sb.append("\nlatitude : ");
		// sb.append(poiLocation.getLatitude());
		// sb.append("\nlontitude : ");
		// sb.append(poiLocation.getLongitude());
		// sb.append("\nradius : ");
		// sb.append(poiLocation.getRadius());
		// if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
		// sb.append("\naddr : ");
		// sb.append(poiLocation.getAddrStr());
		// }
		// if (poiLocation.hasPoi()) {
		// sb.append("\nPoi:");
		// sb.append(poiLocation.getPoi());
		// } else {
		// sb.append("noPoi information");
		// }
		// logMsg(sb.toString());
		// }
		// }
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
