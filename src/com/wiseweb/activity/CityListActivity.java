package com.wiseweb.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.google.gson.Gson;
import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.CityListAdapter;
import com.wiseweb.fragment.adapter.CitySearchAdapter;
import com.wiseweb.json.City;
import com.wiseweb.json.City.Group;
import com.wiseweb.json.City.Group.CityList;
import com.wiseweb.json.CityResult;
import com.wiseweb.listener.MyLocationListenner;
import com.wiseweb.movie.R;
import com.wiseweb.service.LocationService;
import com.wiseweb.util.GetEnc;

//import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类

public class CityListActivity extends Activity {
	private CityListAdapter adapter = null;
	private ListView cityList = null;
	private ArrayList<String> list = new ArrayList<String>();
	private ArrayList<String> listTag = new ArrayList<String>();
	private ArrayList<String> listId = new ArrayList<String>();
	private ArrayList<String> listName = new ArrayList<String>();
	private SharedPreferences cityPreferences;
	// private SharedPreferences mflag; //
	private View citySearchBack;
	private EditText citySearchEdit;
	private ImageView citySearchDelText;
	public LocationClient mLocationClient = null;
	public BDLocation location = new BDLocation();
	public BDLocationListener myListener = new MyLocationListenner();
	private TextView positionCityName;
	private String city = null;
	private String cityId = null;
	private CitySearchAdapter searchAdapter;
	private ImageView cityImageSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.city_list_activity);
		// positionCityName = (TextView) findViewById(R.id.position_city_name);

/*		mLocationClient = new LocationClient(this); // 声明LocationClient类

		// 设置参数
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 是否打开GPS
		option.setCoorType("bd09ll"); //
		// 设置返回值的坐标类型。
//		option.setPriority(LocationClientOption.NetWorkFirst); // 设置定位优先级
		option.setProdName("LocationDemo"); //
		// 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
		option.setScanSpan(5000); // 设置定时定位的时间间隔。单位毫秒
		option.setAddrType("detail");

		mLocationClient.setLocOption(option);

		mLocationClient.start();

		mLocationClient.requestLocation();

		// option.setIsNeedAddress(true);

		// option.disableCache(true);
		// 禁止启用缓存定位
		// option.setPoiNumber(5);
		// 最多返回POI个数
		// option.setPoiDistance(1000);
		// poi查询距离 //
//		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息 //
		mLocationClient.setLocOption(option);
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
					e.printStackTrace();
				}
				if (addresses != null && addresses.size() > 0) {
					Address address = addresses.get(0);
					city = address.getLocality(); //
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
//				if (poiLocation.hasPoi()) {
//					sb.append("\nPoi:");
//					sb.append(poiLocation.getPoi());
//				} else {
//					sb.append("noPoi information");
//				}
				Log.d("Poi_sb", sb.toString());
			}
		}); // 注册监听函数  */

		// setData();
		cityList = (ListView) findViewById(R.id.city_list);
		list.add("定位的城市");
		list.add("定位中");
		listTag.add("定位的城市");
		new Thread(runnable).start();

//		adapter = new CityListAdapter(this, list, listTag);
//		cityList = (ListView) findViewById(R.id.city_list);
//		cityList.setAdapter(adapter);

		searchAdapter = new CitySearchAdapter(CityListActivity.this);
//		searchAdapter.setDataSource(list);
//		cityList.setAdapter(searchAdapter);
		cityImageSearch = (ImageView) findViewById(R.id.city_image_search);

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

				// searchAdapter.searchData(citySearchEdit.getText().toString());

			}

		});
		citySearchDelText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				citySearchEdit.setText("");
				cityList.setAdapter(adapter);
			}

		});
		// 搜索按钮的点击事件
		cityImageSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (citySearchEdit.getText().toString() != null) {

					searchAdapter.setDataSource(list);
					cityList.setAdapter(searchAdapter);
					searchAdapter.searchData(citySearchEdit.getText()
							.toString());
				}
			}

		});

		cityList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == 1) {
					// 启动服务，执行定位
					Intent intent = new Intent(CityListActivity.this,
							LocationService.class);
					startService(intent);

				}
				String city = null;
				// city = list.get(position).toString();
				city = cityList.getItemAtPosition(position).toString();
				//

				cityPreferences = getSharedPreferences("city",
						Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = cityPreferences.edit();
				editor.putString("city", city);
				// 取得cityId
				for (int i = 0; i < listName.size(); i++) {
					if (listName.get(i).equals(city))
						cityId = listId.get(i);
				}
				editor.putString("cityId", cityId);
				editor.commit();
				// String str = cityPreferences.getString("city", null);
				// System.out.println(str);

				// Intent intent = new Intent();
				// intent.setClass(CityListActivity.this, MainActivity.class);
				// startActivity(intent);
				Intent data = new Intent();
				data.putExtra("city", city);
				// 请求代码可以自己设置，这里设置成2
				setResult(2, data);
				finish();

			}

		});
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			// String val = data.getString("response");
			// System.out.println("请求结果-->" + val);
			list = data.getStringArrayList("list");
			listTag = data.getStringArrayList("listTag");
			adapter = new CityListAdapter(CityListActivity.this, list, listTag);

			cityList.setAdapter(adapter);
		}
	};

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("action", "city_Query");
			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp + "");
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();

			HttpGet getMethod = new HttpGet(Constant.baseURL  + "action="
					+ params.get("action") + "&" + "enc=" + enc + "&"
					+ "time_stamp=" + time_stamp);
			System.out.println("cities---------"+Constant.baseURL + "action=" + params.get("action")
					+ "&" + "enc=" + enc + "&" + "time_stamp=" + time_stamp);
			HttpResponse httpResponse;

			String result;

			try {
				httpResponse = httpClient.execute(getMethod);

				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					Gson gson = new Gson();
					CityResult cityResult = gson.fromJson(result,
							CityResult.class);
					List<City> citys = cityResult.getCitis();
					for (int i = 0; i < citys.size(); i++) {
						List<Group> group = citys.get(i).getGroup();
						System.out.println("group.size()" + group.size());
						String text = citys.get(i).getText();
						listTag.add(text);
						list.add(text);
//						listId.add(text);
						for (int j = 0; j < group.size(); j++) {
							List<CityList> cityList = group.get(j).getList();
							for (int m = 0; m < cityList.size(); m++) {
								String cityName = cityList.get(m).getCityName();
								list.add(cityName);
								listName.add(cityName);
								String cityId = cityList.get(m).getCityId();
								listId.add(cityId);
							}
						}
					}
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putStringArrayList("list", list);
					data.putStringArrayList("listTag", listTag);
					data.putStringArrayList("listId", listId);
					msg.setData(data);
					handler.sendMessage(msg);

				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	
	

	public void setData() {
		list.add("定位的城市");
		listTag.add("定位的城市");
		list.add("定位中");
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
