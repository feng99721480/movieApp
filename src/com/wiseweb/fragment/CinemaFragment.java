package com.wiseweb.fragment;

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
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wiseweb.activity.CinemaSelectFilmActivity;
import com.wiseweb.activity.CinemaSearchActivity;
import com.wiseweb.activity.CityListActivity;
import com.wiseweb.activity.MainActivity;
import com.wiseweb.activity.SelectSeatBuyTicketActivity;
import com.wiseweb.bean.CinemaInfo;
import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.CinemaAdapter;
import com.wiseweb.json.CinemaResult;
import com.wiseweb.json.CinemaResult.Cinema;
import com.wiseweb.movie.R;
import com.wiseweb.util.GetEnc;

public class CinemaFragment extends BaseFragment {
	private MainActivity mMainActivity;
	private ListView cinemaList;
	private CinemaAdapter cinemaAdapter;
	private List<CinemaInfo> cinemaInfo = new ArrayList<CinemaInfo>();
	private List<CinemaInfo> cinemaInfo_seat = new ArrayList<CinemaInfo>();
	private List<CinemaInfo> cinemaInfo_group = new ArrayList<CinemaInfo>();
	private SharedPreferences cityPreferences;
	private TextView cityTv;
	private RadioGroup cinemaRadio;
	// private RadioButton cinemaAll;
	private RadioButton selectSeat;
	private RadioButton groupPurchase;
	private View cinemaArea;
	private ImageView cinemaSearch;
	private static final int ALL_CINEMA = 5;
	private static final int SEAT_CINEMA = 6;
	private static final int GROUP_CINEMA = 7;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View cinemaLayout = inflater.inflate(R.layout.cinema_layout, container,
				false);
		mMainActivity = (MainActivity) getActivity();
		mFragmentManager = getActivity().getFragmentManager();
		cinemaList = (ListView) cinemaLayout.findViewById(R.id.listview_cinema);
		cinemaAdapter = new CinemaAdapter(cinemaInfo, mMainActivity);
		cinemaList.setAdapter(cinemaAdapter);
		cityPreferences = mMainActivity.getSharedPreferences("city",
				Context.MODE_PRIVATE);
		cityTv = (TextView) cinemaLayout.findViewById(R.id.city_name);
		cityTv.setText(cityPreferences.getString("city", null));
		// cinemaAll = (RadioButton)cinemaLayout.findViewById(R.id.cinema_all);
		selectSeat = (RadioButton) cinemaLayout
				.findViewById(R.id.cinema_select_seat);
		groupPurchase = (RadioButton) cinemaLayout
				.findViewById(R.id.cinema_group_purchase);
		cinemaRadio = (RadioGroup) cinemaLayout.findViewById(R.id.cinema_radio);
		cinemaArea = (View) cinemaLayout.findViewById(R.id.cinema_area);
		cinemaArea.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass((MainActivity) getActivity(),
						CityListActivity.class);
				// Bundle mBundle = new Bundle();
				// mBundle.putString("className", "cinemaFragment");
				// intent.putExtra("flag", "cinemaFragment");
				startActivity(intent);
			}

		});
		cinemaSearch = (ImageView) cinemaLayout
				.findViewById(R.id.cinema_search);
		cinemaSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(mMainActivity, CinemaSearchActivity.class);
				startActivity(intent);
			}

		});
		cinemaRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

				if (checkedId == selectSeat.getId()) {
					// cinemaInfo_seat.clear();
					for (int i = 0; i < cinemaInfo.size(); i++) {
						if (cinemaInfo.get(i).isSeat() == true) {
							cinemaInfo_seat.add(cinemaInfo.get(i));
						}
					}
					// 开启线程获取可以 选座 的电影院数据
					// new Thread(selectSeatRunnable).start();
					cinemaAdapter = new CinemaAdapter(cinemaInfo_seat,
							mMainActivity);
					cinemaList.setAdapter(cinemaAdapter);
				} else if (checkedId == groupPurchase.getId()) {
					// cinemaInfo_group.clear();
					for (int i = 0; i < cinemaInfo.size(); i++) {
						if (cinemaInfo.get(i).isGroupPurchase() == true) {
							cinemaInfo_group.add(cinemaInfo.get(i));
						}
					}
					// 开启线程获取可以 团购 的电影院数据
					// new Thread(groupPurRunnable).start();
					cinemaAdapter = new CinemaAdapter(cinemaInfo_group,
							mMainActivity);
					cinemaList.setAdapter(cinemaAdapter);
				} else {
					// 开启线程获取所有电影院数据
					 new Thread(runnable).start();
					
//					cinemaAdapter = new CinemaAdapter(cinemaInfo, mMainActivity);
//					cinemaList.setAdapter(cinemaAdapter);
				}
			}

		});

		cinemaList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				//保存cinemaId用于CinemaDetailActivity获取数据
				SharedPreferences sp =mMainActivity.getSharedPreferences("cinemaConfig", Context.MODE_PRIVATE);
				Editor editor = sp.edit();
				editor.putInt("cinemaId", cinemaInfo.get(position).getCinemaId());
				editor.commit();
				
				Intent intent = new Intent();
				intent.setClass(mMainActivity, CinemaSelectFilmActivity.class);
				startActivity(intent);
			}

		});
		// 默认界面 开启线程获取所有电影院数据
		 new Thread(runnable).start();
		return cinemaLayout;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		cinemaInfo.add(new CinemaInfo("UME国际影城", true, true, false, false,
//				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));
//
//		cinemaInfo.add(new CinemaInfo("UME国际影城", true, false, false, false,
//				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));
//
//		cinemaInfo.add(new CinemaInfo("UME国际影城", false, true, false, false,
//				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));
//
//		cinemaInfo.add(new CinemaInfo("UME国际影城", false, false, true, false,
//				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));
//
//		cinemaInfo.add(new CinemaInfo("UME国际影城", true, false, true, true, 19.9,
//				"海淀区双榆树科学院南路44号", "1.5KM"));
//
//		cinemaInfo.add(new CinemaInfo("UME国际影城", false, false, true, false,
//				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));
//
//		cinemaInfo.add(new CinemaInfo("UME国际影城", true, false, true, false,
//				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ALL_CINEMA:
				cinemaAdapter = new CinemaAdapter(cinemaInfo, mMainActivity);
				cinemaList.setAdapter(cinemaAdapter);
				cinemaAdapter.notifyDataSetChanged();
				break;
			case SEAT_CINEMA:
				cinemaAdapter = new CinemaAdapter(cinemaInfo_seat,
						mMainActivity);
				cinemaList.setAdapter(cinemaAdapter);
				cinemaAdapter.notifyDataSetChanged();
				break;
			case GROUP_CINEMA:
				cinemaAdapter = new CinemaAdapter(cinemaInfo_group,
						mMainActivity);
				cinemaList.setAdapter(cinemaAdapter);
				cinemaAdapter.notifyDataSetChanged();
				break;
			}
		};
	};
	// 从服务端获取所有影院
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// String baseURL = "http://192.168.0.141:4000/appAPI";
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("action", "cinema_Query");
			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp + "");
			int count = 10;// 数量
			params.put("count", count);
			int start = 0;// 从第几个开始
			params.put("start", start);
//			SharedPreferences s = mMainActivity.getSharedPreferences("city",
//					Context.MODE_PRIVATE);
//			String cityId = s.getString("cityId", null);
			String cityId = "209";
			params.put("city_id", cityId);
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "city_id=" + cityId + "&"
					+ "start=" + start + "&" + "count=" + count + "&" + "enc="
					+ enc + "&" + "time_stamp" + time_stamp);
			System.out.println("*******************CinemaFragment*************************");
			System.out.println(Constant.baseURL + "action="
					+ params.get("action") + "&" + "city_id=" + cityId + "&"
					+ "start=" + start + "&" + "count=" + count + "&" + "enc="
					+ enc + "&" + "time_stamp=" + time_stamp);
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				System.out.println("应答码="+httpResponse.getStatusLine().getStatusCode());
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					if(result == null){
						System.out.println("result为空");
					}else{
						System.out.println("result===="+result);
					}
					
					Gson gson = new Gson();
					CinemaResult cinemaResult = gson.fromJson(result,
							CinemaResult.class);
					List<Cinema> cinemas = cinemaResult.getCinemas();
					if(cinemaResult == null ){
						System.out.println("--------");
					}else{
						System.out.println("cinemaResult==="+cinemaResult.toString());
					}
					cinemaInfo.clear();
					for (int i = 0; i < cinemas.size(); i++) {
						CinemaInfo info = new CinemaInfo();
						int cinemaId ;
						String cinemaName = "";
						boolean hasPreferential;
						boolean hasImax;
						boolean hasSeat;
						boolean hasGroupPurchase;
						double cinemaLowestPrice = 0.0;
						String cinemaAddress = "";
						String cinameDistance = "";
						if(cinemas.get(i).getCinemaId() != 0){
							cinemaId = cinemas.get(i).getCinemaId();
							info.setCinemaId(cinemaId);
						}
						if (!(cinemas.get(i).getCinemaName().equals(null))) {
							cinemaName = cinemas.get(i).getCinemaName();
							System.out.println("cinemaName============="+cinemaName);
							info.setCinemaName(cinemaName);
						}
						hasPreferential = cinemas.get(i).isPreferential();
						if (hasPreferential == true) {
							
							info.setPreferential(hasPreferential);
						}else{
							System.out.println("hasPreferential============="+hasPreferential);
						}
						hasImax = cinemas.get(i).isImax();
						if (hasImax == true) {
							
							info.setImax(hasImax);
						}else{
							System.out.println("hasImax============="+hasImax);
						}
						hasSeat = cinemas.get(i).isSeat();
						if (hasSeat == true) {
							
							info.setSeat(hasSeat);
						}else{
							System.out.println("hasSeat============="+hasSeat);
						}
						hasGroupPurchase = cinemas.get(i).isGroupPurchase();
						if (hasGroupPurchase == true) {
							
							info.setGroupPurchase(hasGroupPurchase);
						}else{
							System.out.println("hasGroupPurchase============="+hasGroupPurchase);
						}
						if (cinemas.get(i).getLowestPrice() == 0.0) {
							cinemaLowestPrice = cinemas.get(i).getLowestPrice();
							System.out.println("cinemaLowestPrice============="+cinemaLowestPrice);
							info.setLowestPrice(cinemaLowestPrice);
						}else{
							System.out.println("cinemaLowestPrice============="+cinemaLowestPrice);
						}
						if (!(cinemas.get(i).getCinemaAddress().equals(null))) {
							cinemaAddress = cinemas.get(i).getCinemaAddress();
							System.out.println("cinemaAddress============="+cinemaAddress);
							info.setCinemaAddress(cinemaAddress);
						}
						//这样写报空指针异常 数据本身就为空
//						if (!(cinemas.get(i).getDistance().equals(null))) {
//							cinameDistance = cinemas.get(i).getDistance();
//							info.setDistance(cinameDistance);
//						}else{
//							System.out.println("cinameDistance=="+cinameDistance);
//						}
						if(cinemas.get(i).getDistance() != null){
							cinameDistance = cinemas.get(i).getDistance();
							info.setDistance(cinameDistance);
						}else{
							System.out.println("cinameDistance=="+cinameDistance);
						}
						
						cinemaInfo.add(info);
					}
					Message msg = new Message();
					msg.what = ALL_CINEMA;
					handler.sendMessage(msg);

				}else{
					Toast.makeText(mMainActivity, "没有获取到数据", 0).show();
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};

	// 从服务端获取所有可以选座的影院

	Runnable selectSeatRunnable = new Runnable() {

		@Override
		public void run() {
			// String baseURL = "http://192.168.0.141:4000/appAPI";
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("action", "cinema_Query");
			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp + "");
			SharedPreferences s = mMainActivity.getSharedPreferences("city",
					Context.MODE_PRIVATE);
			String cityId = s.getString("cityId", null);
			params.put("city_id", cityId);
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "?" + "action="
					+ params.get("action") + "&" + "city_id=" + cityId + "&"
					+ "enc=" + enc + "&" + "time_stamp" + time_stamp);
			System.out.println(Constant.baseURL + "?" + "action="
					+ params.get("action") + "&" + "city_id=" + cityId + "&"
					+ "enc=" + enc + "&" + "time_stamp" + time_stamp);
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					Gson gson = new Gson();
					CinemaResult cinemaResult = gson.fromJson(result,
							CinemaResult.class);
					List<Cinema> cinemas = cinemaResult.getCinemas();
					cinemaInfo.clear();
					for (int i = 0; i < cinemas.size(); i++) {
						CinemaInfo info = new CinemaInfo();
						String cinemaName = "";
						boolean hasPreferential;
						boolean hasImax;
						boolean hasSeat;
						boolean hasGroupPurchase;
						double cinemaLowestPrice;
						String cinemaAddress;
						String cinameDistance;
						hasSeat = cinemas.get(i).isSeat();
						// 只取可以选座的数据
						if (hasSeat == true) {
							info.setSeat(hasSeat);
							if (!(cinemas.get(i).getCinemaName().equals(null))) {
								cinemaName = cinemas.get(i).getCinemaName();
								info.setCinemaName(cinemaName);
							}
							hasPreferential = cinemas.get(i).isPreferential();
							if (hasPreferential == true) {
								info.setPreferential(hasPreferential);
							}
							hasImax = cinemas.get(i).isImax();
							if (hasImax == true) {
								info.setImax(hasImax);
							}

							hasGroupPurchase = cinemas.get(i).isGroupPurchase();
							if (hasGroupPurchase == true) {
								info.setGroupPurchase(hasGroupPurchase);
							}
							if (cinemas.get(i).getLowestPrice() > 0.001) {
								cinemaLowestPrice = cinemas.get(i)
										.getLowestPrice();
								info.setLowestPrice(cinemaLowestPrice);
							}
							if (!(cinemas.get(i).getCinemaAddress()
									.equals(null))) {
								cinemaAddress = cinemas.get(i)
										.getCinemaAddress();
								info.setCinemaAddress(cinemaAddress);
							}
							if (!(cinemas.get(i).getDistance().equals(null))) {
								cinameDistance = cinemas.get(i).getDistance();
								info.setDistance(cinameDistance);
							}
							cinemaInfo_seat.add(info);

						}

					}
					Message msg = new Message();
					msg.what = SEAT_CINEMA;
					handler.sendMessage(msg);

				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	// 从服务端获取所有可以团购的影院

	Runnable groupPurRunnable = new Runnable() {

		@Override
		public void run() {
			// String baseURL = "http://192.168.0.141:4000/appAPI";
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("action", "cinema_Query");
			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp + "");
			SharedPreferences s = mMainActivity.getSharedPreferences("city",
					Context.MODE_PRIVATE);
			String cityId = s.getString("cityId", null);
			params.put("city_id", cityId);
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "?" + "action="
					+ params.get("action") + "&" + "city_id=" + cityId + "&"
					+ "enc=" + enc + "&" + "time_stamp" + time_stamp);
			System.out.println(Constant.baseURL + "?" + "action="
					+ params.get("action") + "&" + "city_id=" + cityId + "&"
					+ "enc=" + enc + "&" + "time_stamp" + time_stamp);
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					Gson gson = new Gson();
					CinemaResult cinemaResult = gson.fromJson(result,
							CinemaResult.class);
					List<Cinema> cinemas = cinemaResult.getCinemas();
					cinemaInfo.clear();
					for (int i = 0; i < cinemas.size(); i++) {
						CinemaInfo info = new CinemaInfo();
						String cinemaName = "";
						boolean hasPreferential;
						boolean hasImax;
						boolean hasSeat;
						boolean hasGroupPurchase;
						double cinemaLowestPrice;
						String cinemaAddress;
						String cinameDistance;
						hasGroupPurchase = cinemas.get(i).isGroupPurchase();
						// 只取可以团购的数据
						if (hasGroupPurchase == true) {
							info.setGroupPurchase(hasGroupPurchase);
							if (!(cinemas.get(i).getCinemaName().equals(null))) {
								cinemaName = cinemas.get(i).getCinemaName();
								info.setCinemaName(cinemaName);
							}
							hasPreferential = cinemas.get(i).isPreferential();
							if (hasPreferential == true) {
								info.setPreferential(hasPreferential);
							}
							hasImax = cinemas.get(i).isImax();
							if (hasImax == true) {
								info.setImax(hasImax);
							}

							hasSeat = cinemas.get(i).isSeat();
							if (hasSeat == true) {
								info.setSeat(hasSeat);
							}
							if (cinemas.get(i).getLowestPrice() > 0.001) {
								cinemaLowestPrice = cinemas.get(i)
										.getLowestPrice();
								info.setLowestPrice(cinemaLowestPrice);
							}
							if (!(cinemas.get(i).getCinemaAddress()
									.equals(null))) {
								cinemaAddress = cinemas.get(i)
										.getCinemaAddress();
								info.setCinemaAddress(cinemaAddress);
							}
							if (!(cinemas.get(i).getDistance().equals(null))) {
								cinameDistance = cinemas.get(i).getDistance();
								info.setDistance(cinameDistance);
							}
							cinemaInfo_group.add(info);

						}

					}
					Message msg = new Message();
					msg.what = GROUP_CINEMA;
					handler.sendMessage(msg);

				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();

		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_CINEMA;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	// }

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

}
