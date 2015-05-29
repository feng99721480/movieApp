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
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.wiseweb.util.DatabaseHelper;
import com.wiseweb.util.GetEnc;
import com.wiseweb.util.Util;

//import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类

public class CityListActivity extends Activity {
	public static final int STORAGE_COMPLETION = 0;
	private CityListAdapter adapter = null;
	private ListView cityList = null;
	private ArrayList<String> list = new ArrayList<String>(); // 所有的数据
	private ArrayList<String> listTag = new ArrayList<String>(); // tag数据
	private ArrayList<String> listId = new ArrayList<String>(); // 城市id
	private ArrayList<String> listName = new ArrayList<String>(); // 城市名称
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
	private DatabaseHelper db;
	private SQLiteDatabase dbRead, dbWrite;
	private Cursor cursor;
	private String oldFirst = "";
	private String newFirst = "";
	private String oldText = "";
	private String newText = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.city_list_activity);
		Intent intent = new Intent("com.wiseweb.movie.MSG_ACTION");
		startService(intent);
		initView();
		
		db = new DatabaseHelper(this);
		dbRead = db.getReadableDatabase();
		dbWrite = db.getWritableDatabase();
		initListView();
//		list.add("定位的城市");
//		list.add("定位中");
//		listTag.add("定位的城市");

		// adapter = new CityListAdapter(this, list, listTag);
		// cityList = (ListView) findViewById(R.id.city_list);
		// cityList.setAdapter(adapter);

		searchAdapter = new CitySearchAdapter(CityListActivity.this);
		// searchAdapter.setDataSource(list);
		// cityList.setAdapter(searchAdapter);

		citySearchBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}

		});

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
		// 搜索框中的删除按钮点击事件
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

					searchAdapter.setDataSource(Util
							.removeDuplicateFromArrayList(list));
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
				
				Intent data = new Intent();
				data.putExtra("city", city);
				data.putExtra("cityId", cityId);
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
			switch(msg.what){
			case STORAGE_COMPLETION:
				initListView();
				break;
			}
				
//			Bundle data = msg.getData();
//			list = data.getStringArrayList("list");
//			listTag = data.getStringArrayList("listTag");
//			adapter = new CityListAdapter(CityListActivity.this, list, listTag);
//
//			cityList.setAdapter(adapter);
		}
	};
   /**
    * 请求数据
    */
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

			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "enc=" + enc + "&"
					+ "time_stamp=" + time_stamp);
			System.out.println("cities---------" + Constant.baseURL + "action="
					+ params.get("action") + "&" + "enc=" + enc + "&"
					+ "time_stamp=" + time_stamp);
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
						String tab = citys.get(i).getTab();
						String text = citys.get(i).getText();
						// listTag.add(text);
						// list.add(text);
						// listId.add(text);
						for (int j = 0; j < group.size(); j++) {
							List<CityList> cityList = group.get(j).getList();
							for (int m = 0; m < cityList.size(); m++) {
								String cityName = cityList.get(m).getCityName();
								String cityId = cityList.get(m).getCityId();
								String first = cityList.get(m).getFirst();
								ContentValues cv = new ContentValues();
								cv.put("cityId", cityId);
								cv.put("cityName", cityName);
								cv.put("first", first);
								cv.put("tab", tab);
								cv.put("text", text);
								cursor = dbRead.query("cityInfo", new String[]{"cityId","text"}, "cityId=? and text=?", new String[]{cityId,text}, null, null, null);
								if(cursor.moveToFirst() == false){  //查询结果，无此数据，插入
									dbWrite.insert("cityInfo", null, cv);
								}
															
							}
						}
					}
					Message msg = new Message();
					msg.what = STORAGE_COMPLETION;
//					Bundle data = new Bundle();
//					data.putStringArrayList("list", list);
//					data.putStringArrayList("listTag", listTag);
//					data.putStringArrayList("listId", listId);
//					msg.setData(data);
					handler.sendMessage(msg);

				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 初始化UI
	 */
	public void initView() {
		cityList = (ListView) findViewById(R.id.city_list);
		cityImageSearch = (ImageView) findViewById(R.id.city_image_search);
		citySearchBack = (View) findViewById(R.id.city_search_title);
		citySearchEdit = (EditText) findViewById(R.id.city_etSearch);
		citySearchDelText = (ImageView) findViewById(R.id.city_ivDeleteText);
	}

	/**
	 * 展示数据
	 */
	private void initListView() {
		cursor = dbRead.query("cityInfo", null, null, null, null, null, null);
		if (cursor.moveToFirst() == false) {
			Thread t = new Thread(runnable);
			t.start();
//			try {
//				t.join();
//				initListView();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} else {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				String name = cursor.getString(cursor
						.getColumnIndex("cityName"));
				String id = cursor.getString(cursor.getColumnIndex("cityId"));
				String first = cursor.getString(cursor.getColumnIndex("first"));
				String tab = cursor.getString(cursor.getColumnIndex("first"));
				String text = cursor.getString(cursor.getColumnIndex("text"));
				newText = text;

				if (text.equals("热门城市") && !newText.equals(oldText)) {
					list.add(text);
					listTag.add(text);
					oldText = newText;
					list.add(name);
					listName.add(name);
					listId.add(id);
				} else if (newText.equals(oldText) && newText.equals("热门城市")) {
					list.add(name);
					listName.add(name);
					listId.add(id);
				} else {
					newFirst = first;
					// 前后的first值不相同的话 加一个tag值
					if (!oldFirst.equals(newFirst)) {
						list.add(newFirst);
						listTag.add(newFirst);
						oldFirst = newFirst;
					}
					list.add(name);
					listName.add(name);
					listId.add(id);

				}
				adapter = new CityListAdapter(CityListActivity.this, list,
						listTag);
				cityList.setAdapter(adapter);

			}
		}
	}

	/**
	 * 广播接收器，接受定位服务广播的消息
	 */
	public class MsgReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 拿到定位的城市名称，更新UI
			String name = intent.getStringExtra("name");
			list.set(1, name);
			adapter = new CityListAdapter(CityListActivity.this, list, listTag);
			cityList.setAdapter(adapter);
		}

	}
}
