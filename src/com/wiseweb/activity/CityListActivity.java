package com.wiseweb.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.wiseweb.fragment.adapter.CityListAdapter;
import com.wiseweb.movie.R;

//import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类

public class CityListActivity extends Activity {
	private CityListAdapter adapter = null;
	private ListView cityList = null;
	private List<String> list = new ArrayList<String>();
	private List<String> listTag = new ArrayList<String>();
	private SharedPreferences cityPreferences;
	// private SharedPreferences mflag; //
	private View citySearchBack;
	private EditText citySearchEdit;
	private ImageView citySearchDelText;
	public LocationClient mLocationClient = null;
	public BDLocation location = new BDLocation();
	// public BDLocationListener myListener = new MyLocationListenner();
	private Button ding;
	private Button positionCityName;
	private String city = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.city_list_activity);
		positionCityName = (Button) findViewById(R.id.position_city_name);
		mLocationClient = new LocationClient(this); // 声明LocationClient类

		// 设置参数
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 是否打开GPS
		option.setCoorType("bd09ll"); // 设置返回值的坐标类型。
		option.setPriority(LocationClientOption.NetWorkFirst);
		// option.setPriority(LocationClientOption.NetWorkFirst); // 设置定位优先级
		option.setProdName("LocationDemo"); // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
		option.setScanSpan(5000); // 设置定时定位的时间间隔。单位毫秒
		option.setAddrType("detail");
		mLocationClient.setLocOption(option);

		mLocationClient.start();

		mLocationClient.requestLocation();

		// option.setIsNeedAddress(true);

		// option.disableCache(true);// 禁止启用缓存定位
		// option.setPoiNumber(5); // 最多返回POI个数
		// option.setPoiDistance(1000); // poi查询距离
		// option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		// mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(new BDLocationListener() {
			public void onReceiveLocation(BDLocation location) {
				LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				Location mlocation = locationManager
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);

				if (location == null)
					return;
				StringBuffer sb = new StringBuffer(256);
				sb.append("time : ");
				sb.append(location.getTime());
				sb.append("\nerror code : ");
				sb.append(location.getLocType());
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());
				sb.append("\nradius : ");
				sb.append(location.getRadius());

				sb.append("\naddr : ");
				sb.append(location.getAddrStr());

				if (location.getLocType() == BDLocation.TypeGpsLocation) {
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
					sb.append("\naddr : ");
					sb.append(location.getAddrStr());

				}
				Geocoder geocoder = new Geocoder(CityListActivity.this, Locale
						.getDefault());
				List<Address> addresses = null;
				try {
					addresses = geocoder.getFromLocation(
							location.getLatitude(), location.getLongitude(), 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (addresses != null && addresses.size() > 0) {
					Address address = addresses.get(0);
					city = address.getLocality();
					System.out.println("city:" + city);
					positionCityName.setText(city);
				}

			}

			public void onReceivePoi(BDLocation poiLocation) {
				if (poiLocation == null) {
					return;
				}
				StringBuffer sb = new StringBuffer(256);
				sb.append("Poi time : ");
				sb.append(poiLocation.getTime());
				sb.append("\nerror code : ");
				sb.append(poiLocation.getLocType());
				sb.append("\nlatitude : ");
				sb.append(poiLocation.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(poiLocation.getLongitude());
				sb.append("\nradius : ");
				sb.append(poiLocation.getRadius());
				if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
					sb.append("\naddr : ");
					sb.append(poiLocation.getAddrStr());
				}
				if (poiLocation.hasPoi()) {
					sb.append("\nPoi:");
					sb.append(poiLocation.getPoi());
				} else {
					sb.append("noPoi information");
				}
				Log.d("Poi_sb", sb.toString());
			}
		}); // 注册监听函数
		/*
		 * ding = (Button) findViewById(R.id.ding); ding.setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { if (mLocationClient ==
		 * null) { return; } if (mLocationClient.isStarted()) {
		 * ding.setText("Start"); mLocationClient.stop(); } else {
		 * ding.setText("Stop"); mLocationClient.start();
		 * 
		 * 当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。调用requestLocation(
		 * )后，每隔设定的时间，定位SDK就会进行一次定位。 如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，
		 * 返回上一次定位的结果；如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。
		 * 定时定位时，调用一次requestLocation，会定时监听到定位结果。
		 * 
		 * mLocationClient.requestLocation(); } // if (mLocationClient != null
		 * && mLocationClient.isStarted()) // mLocationClient.requestLocation();
		 * // else // Log.d("LocSDK_2.0_Demo1", //
		 * "locClient is null or not started"); }
		 * 
		 * });
		 */
		// 请求
		// if (mLocationClient != null && mLocationClient.isStarted())
		// mLocationClient.requestLocation();
		// else
		// Log.d("LocSDK_2.0_Demo1", "locClient is null or not started");
		// if (mLocationClient != null && mLocationClient.isStarted())
		// mLocationClient.requestPoi();

		setData();

		adapter = new CityListAdapter(this, list, listTag);
		cityList = (ListView) findViewById(R.id.city_list);
		cityList.setAdapter(adapter);
		citySearchBack = (View) findViewById(R.id.city_search_title);
		citySearchBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}

		});
		citySearchEdit = (EditText) findViewById(R.id.city_etSearch);
		citySearchDelText = (ImageView) findViewById(R.id.city_ivDeleteText);

		citySearchEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					citySearchDelText.setVisibility(View.GONE);
				} else {
					citySearchDelText.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

		});
		citySearchDelText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				citySearchEdit.setText("");
			}

		});
		/*
		 * mflag = getSharedPreferences("fragmentFlag",Context.MODE_PRIVATE);
		 * SharedPreferences.Editor flagEditor = mflag.edit(); Intent intent =
		 * getIntent(); String flag = intent.getStringExtra("flag");
		 * System.out.println(flag==null); if(flag.equals("filmFragment")){
		 * flagEditor.putString("flag", "filmFragment"); }else
		 * if(flag.equals("cinemaFragment")){ flagEditor.putString("flag",
		 * "cinemaFragment"); } flagEditor.commit();
		 */

		cityList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String city = null;
				city = list.get(position).toString();
				//
				cityPreferences = getSharedPreferences("city",
						Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = cityPreferences.edit();
				editor.putString("city", city);
				editor.commit();

				// String str = cityPreferences.getString("city", null);
				// System.out.println(str);

				// Intent intent = new Intent();
				// intent.setClass(CityListActivity.this, MainActivity.class);
				// startActivity(intent);
				finish();
			}

		});
	}

	public void setData() {
		list.add("定位的城市");
		listTag.add("定位的城市");
		list.add(city);
		list.add("热门城市");
		listTag.add("热门城市");
		list.add("上海");
		list.add("北京");
		list.add("广州");
		list.add("深圳");
		list.add("武汉");
		list.add("天津");
		list.add("西安");
		list.add("南京");
		list.add("杭州");
		list.add("成都");
		list.add("重庆");
		list.add("A");
		listTag.add("A");
		list.add("阿坝");
		list.add("阿克苏");
		list.add("阿拉善盟");
		list.add("安吉");
		list.add("安康");
		list.add("安庆");
		list.add("B");
		listTag.add("B");
		list.add("白城");
		list.add("百色");
		list.add("白山");
		list.add("白银");
		list.add("保定");
		list.add("C");
		listTag.add("C");
		list.add("沧州");
		list.add("长春");
		list.add("常德");
		list.add("昌吉");
		list.add("长乐");
		list.add("长沙");
		list.add("长治");
		list.add("常州");
	}
}
