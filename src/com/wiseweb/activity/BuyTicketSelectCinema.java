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

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;

import com.google.gson.Gson;
import com.viewpagerindicator.TabPageIndicator;
import com.wiseweb.bean.CinemaInfo;
import com.wiseweb.constant.Constant;
import com.wiseweb.db.CinemaSQLiteOpenHelper;
import com.wiseweb.fragment.adapter.CinemaAdapter;
import com.wiseweb.fragment.adapter.TabPageIndicatorAdapter;
import com.wiseweb.json.CinemaResult;
import com.wiseweb.json.CinemaResult.Cinema;
import com.wiseweb.movie.R;
import com.wiseweb.ui.AutoListView;
import com.wiseweb.ui.AutoListView.OnLoadListener;
import com.wiseweb.ui.AutoListView.OnRefreshListener;
import com.wiseweb.util.GetEnc;

public class BuyTicketSelectCinema extends FragmentActivity implements
		OnRefreshListener, OnLoadListener {
	private TabPageIndicator indicator;
	private ViewPager pager;
	private FragmentPagerAdapter adapter;
	private AutoListView listview;
	private View buyTicketBack;
	// private RadioGroup radio;// 选择影院的类型
	// private RadioButton selectSeat;
	// private RadioButton groupPurchase;
	// private RadioButton all;
	private List<CinemaInfo> cinemaInfo = new ArrayList<CinemaInfo>();
	// private List<CinemaInfo> cinemaInfo_seat = new ArrayList<CinemaInfo>();
	// private List<CinemaInfo> cinemaInfo_group = new ArrayList<CinemaInfo>();
	// private BuyTicketSelectCinemaListAdapter cinemaAdapter;
	private CinemaAdapter cinemaAdapter;
	private int start = 0;
	private static final int ALL_CINEMA = 3;
	private RelativeLayout relativeFilter;
	// private CinemaFilterView cinemaFilterView;
	private PopupWindow popupWindow;
	private int displayWidth;
	private int displayHeight;
	private ListView listView1, listView2;
	private String[] district = { "按区域" };
	private List<String> districts = new ArrayList<String>();
	private CinemaSQLiteOpenHelper helper;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			List<CinemaInfo> result = (List<CinemaInfo>) msg.obj;
			switch (msg.what) {
			case AutoListView.REFRESH:
				listview.onRefreshComplete();
				cinemaInfo.clear();
				cinemaInfo.addAll(result);
				deleteAll();
				break;
			case AutoListView.LOAD:
				listview.onLoadComplete();
				cinemaInfo.addAll(result);
				break;
			case ALL_CINEMA:
				cinemaInfo.addAll(result);
				cinemaAdapter = new CinemaAdapter(cinemaInfo,
						BuyTicketSelectCinema.this);
				listview.setAdapter(cinemaAdapter);
				break;

			}
			insert(result);
			listview.setResultSize(result.size());
			cinemaAdapter.notifyDataSetChanged();
			System.out.println("result.size()*******************"
					+ result.size());
			System.out.println("cinemaInfo.size()*******************"
					+ cinemaInfo.size());
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_buy_ticket_select_cinema);
		ShareSDK.initSDK(this);
		// 初始化控件
		initUI();
		initPopupWindow();
		indicator = (TabPageIndicator) findViewById(R.id.page_indicator);

		pager = (ViewPager) findViewById(R.id.view_pager);
		//
		adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);

		//
		// indicator.setViewPager(pager);

		// cinemaInfo.add(new CinemaInfo("UME国际影城", true, true, false, false,
		// 19.9, "海淀区双榆树科学院南路44号", "1.5KM"));
		//
		// cinemaInfo.add(new CinemaInfo("UME国际影城", true, false, false, false,
		// 19.9, "海淀区双榆树科学院南路44号", "1.5KM"));
		//
		// cinemaInfo.add(new CinemaInfo("UME国际影城", false, true, false, false,
		// 19.9, "海淀区双榆树科学院南路44号", "1.5KM"));
		//
		// cinemaInfo.add(new CinemaInfo("UME国际影城", false, false, true, false,
		// 19.9, "海淀区双榆树科学院南路44号", "1.5KM"));
		cinemaAdapter = new CinemaAdapter(cinemaInfo,
				BuyTicketSelectCinema.this);
		listview.setAdapter(cinemaAdapter);
		listview.setOnRefreshListener(this);
		listview.setOnLoadListener(this);
		// indicator.setOnPageChangeListener(new OnPageChangeListener() {
		//
		// @Override
		// public void onPageSelected(int position) {
		// Toast.makeText(getApplicationContext(),
		// Constant.CONTENT[position], Toast.LENGTH_SHORT).show();
		// if(radio.getCheckedRadioButtonId() == R.id.cinema_all){
		// cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
		// cinemaInfo, BuyTicketSelectCinema.this);
		// listview.setAdapter(cinemaAdapter);
		// }
		// else if(radio.getCheckedRadioButtonId() == R.id.cinema_select_seat){
		// cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
		// cinemaInfo_seat, BuyTicketSelectCinema.this);
		// listview.setAdapter(cinemaAdapter);
		// }
		// else if(radio.getCheckedRadioButtonId() ==
		// R.id.cinema_group_purchase){
		// cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
		// cinemaInfo_group, BuyTicketSelectCinema.this);
		// listview.setAdapter(cinemaAdapter);
		// }
		// String name = adapter.getPageTitle(position).toString();
		// System.out.println("name-----"+name);
		//
		// }
		//
		// @Override
		// public void onPageScrolled(int arg0, float arg1, int arg2) {
		//
		// }
		//
		// @Override
		// public void onPageScrollStateChanged(int arg0) {
		//
		// }
		// });
		// Time t = new Time("GMT+8");
		// t.setToNow();
		// int month = t.month+1;
		// int day = t.monthDay;

		// 加载数据
		initData();

	}

	private void initData() {
		loadData(ALL_CINEMA);
	}

	// 从服务端获取所有影院
	private void loadData(final int what) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// String baseURL = "http://192.168.0.141:4000/appAPI";
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("action", "cinema_Query");
				Date date = new Date();
				long time_stamp = date.getTime();
				params.put("time_stamp", time_stamp + "");
				int count = start + 10;// 数量
				params.put("count", count);
				// int start = 0;// 从第几个开始
				params.put("start", start);
				// SharedPreferences s =
				// mMainActivity.getSharedPreferences("city",
				// Context.MODE_PRIVATE);
				// String cityId = s.getString("cityId", null);
				String cityId = "209";
				params.put("city_id", cityId);
				String enc = GetEnc.getEnc(params, "wiseMovie");
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
						+ params.get("action") + "&" + "city_id=" + cityId
						+ "&" + "start=" + start + "&" + "count=" + count + "&"
						+ "enc=" + enc + "&" + "time_stamp" + time_stamp);
				System.out
						.println("*******************BuyTicketSelectCinema*************************");
				System.out.println(Constant.baseURL + "action="
						+ params.get("action") + "&" + "city_id=" + cityId
						+ "&" + "start=" + start + "&" + "count=" + count + "&"
						+ "enc=" + enc + "&" + "time_stamp=" + time_stamp);
				HttpResponse httpResponse;
				String result;
				try {
					httpResponse = httpClient.execute(getMethod);
					System.out.println("应答码="
							+ httpResponse.getStatusLine().getStatusCode());
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = httpResponse.getEntity();
						result = EntityUtils.toString(entity, "utf-8");
						if (result == null) {
							System.out.println("result为空");
						} else {
							System.out.println("result====" + result);
						}

						Gson gson = new Gson();
						CinemaResult cinemaResult = gson.fromJson(result,
								CinemaResult.class);
						List<Cinema> cinemas = cinemaResult.getCinemas();
						if (cinemaResult == null) {
							System.out.println("--------");
						} else {
							System.out.println("cinemaResult==="
									+ cinemaResult.toString());
						}
						// cinemaInfo.clear();
						List<CinemaInfo> tempList = new ArrayList<CinemaInfo>();
						for (int i = 0; i < cinemas.size(); i++) {
							CinemaInfo info = new CinemaInfo();
							int cinemaId;
							String cinemaName = "";
							boolean hasPreferential;
							boolean hasImax;
							boolean hasSeat;
							boolean hasGroupPurchase;
							double cinemaLowestPrice = 0.0;
							String cinemaAddress = "";
							String cinameDistance = "";
							String districtName = "";
							if (cinemas.get(i).getDistrictName() != null) {
								districtName = cinemas.get(i).getDistrictName();
								districts.add(cinemas.get(i).getDistrictName());
								info.setDistrictName(districtName);
							}
							// 获取影院ID
							if (cinemas.get(i).getCinemaId() != 0) {
								cinemaId = cinemas.get(i).getCinemaId();
								info.setCinemaId(cinemaId);
							} else {
								info.setCinemaId(0);
							}
							// 获取影院名称
							if (cinemas.get(i).getCinemaName() != null) {
								cinemaName = cinemas.get(i).getCinemaName();
								System.out.println("cinemaName============="
										+ cinemaName);
								info.setCinemaName(cinemaName);
							} else {
								info.setCinemaName("无影院名称信息");
							}
							// 获取新用户专享
							hasPreferential = cinemas.get(i).isPreferential();
							if (hasPreferential == true) {

								info.setPreferential(hasPreferential);
							} else {
								System.out
										.println("hasPreferential============="
												+ hasPreferential);
							}
							// 获取imax
							hasImax = cinemas.get(i).isImax();
							if (hasImax == true) {

								info.setImax(hasImax);
							} else {
								System.out.println("hasImax============="
										+ hasImax);
							}
							// 获取座
							hasSeat = cinemas.get(i).isSeat();
							if (hasSeat == true) {

								info.setSeat(hasSeat);
							} else {
								System.out.println("hasSeat============="
										+ hasSeat);
							}
							// 获取团购
							hasGroupPurchase = cinemas.get(i).isGroupPurchase();
							if (hasGroupPurchase == true) {

								info.setGroupPurchase(hasGroupPurchase);
							} else {
								System.out
										.println("hasGroupPurchase============="
												+ hasGroupPurchase);
							}
							// 获取金额
							if (cinemas.get(i).getLowestPrice() >= 0.0) {
								cinemaLowestPrice = cinemas.get(i)
										.getLowestPrice();
								System.out
										.println("cinemaLowestPrice============="
												+ cinemaLowestPrice);
								info.setLowestPrice(cinemaLowestPrice);
							} else {
								info.setLowestPrice(0.0);
								System.out
										.println("cinemaLowestPrice============="
												+ cinemaLowestPrice);
							}
							//获取影院地址
							if (cinemas.get(i).getCinemaAddress() != null) {
								cinemaAddress = cinemas.get(i)
										.getCinemaAddress();
								System.out.println("cinemaAddress============="
										+ cinemaAddress);
								info.setCinemaAddress(cinemaAddress);
							} else {
								info.setCinemaAddress("无地址信息");
							}

							// 这样写报空指针异常 数据本身就为空
							// if (!(cinemas.get(i).getDistance().equals(null)))
							// {
							// cinameDistance = cinemas.get(i).getDistance();
							// info.setDistance(cinameDistance);
							// }else{
							// System.out.println("cinameDistance=="+cinameDistance);
							// }
							//获取距离
							if (cinemas.get(i).getDistance() != null) {
								cinameDistance = cinemas.get(i).getDistance();
								info.setDistance(cinameDistance);
							} else {
								System.out.println("cinameDistance=="
										+ cinameDistance);
							}

							tempList.add(info);
						}
						Message msg = new Message();
						msg.what = what;
						msg.obj = tempList;
						handler.sendMessage(msg);

					} else {
						Toast.makeText(BuyTicketSelectCinema.this, "没有获取到数据", 0)
								.show();
					}

				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}).start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// private void startAnimation(){
	// if(popupWindow == null){
	// popupWindow = new PopupWindow(cinemaFilterView, displayWidth,
	// displayHeight);
	// popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
	// popupWindow.setFocusable(false);
	// popupWindow.setOutsideTouchable(true);
	//
	// }
	//
	// // if(relativeFilter.isClickable()){
	// // if (!popupWindow.isShowing()) {
	// // showPopup();
	// // }else{
	// // popupWindow.setOnDismissListener(this);
	// // popupWindow.dismiss();
	// // hideView();
	// // }
	// // }else{
	// // if (popupWindow.isShowing()){
	// // popupWindow.dismiss();
	// // hideView();
	// // }
	// // }
	// }
	// private void showPopup(){
	// if (cinemaFilterView instanceof ViewBaseAction) {
	// ViewBaseAction f = (ViewBaseAction) cinemaFilterView;
	// f.showMenu();
	// }
	// popupWindow.showAsDropDown(cinemaFilterView, 0, 0);
	// }
	// private void hideView() {
	// if (cinemaFilterView instanceof ViewBaseAction) {
	// ViewBaseAction f = (ViewBaseAction) cinemaFilterView;
	// f.hideMenu();
	// }
	// }
	private void initPopupWindow() {
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_region, null);
		listView1 = (ListView) layout.findViewById(R.id.listView1);
		listView2 = (ListView) layout.findViewById(R.id.listView2);
		popupWindow = new PopupWindow(layout);
		popupWindow.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事件
		listView1.setAdapter(new ArrayAdapter<String>(
				BuyTicketSelectCinema.this, R.layout.choose_item,
				R.id.choose_item_tv, district));
		listView2.setAdapter(new ArrayAdapter<String>(
				BuyTicketSelectCinema.this, R.layout.choose_item,
				R.id.choose_item_tv, districts));
		for (String str : districts) {
			System.out.println("区域名称————》" + str);
		}
		listView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				List<CinemaInfo> districtList = find(districts.get(position));
				cinemaAdapter = new CinemaAdapter(districtList,
						BuyTicketSelectCinema.this);
				listview.setAdapter(cinemaAdapter);
				cinemaAdapter.notifyDataSetChanged();//刷新
				popupWindow.dismiss();//隐藏窗体
				listview.setLoadEnable(false);//设置不可下拉加载
				Toast.makeText(BuyTicketSelectCinema.this,
						"点击了" + districts.get(position), 0).show();
			}
		});
		popupWindow.setWidth(displayWidth);
		popupWindow.setHeight(displayHeight);
		// 设置背景图片，不能在布局中设置，要通过代码来设置
		popupWindow.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.choosearea_bg_mid));
		// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
		popupWindow.setOutsideTouchable(true);
	}
	//根据区域查询
	public List<CinemaInfo> find(String districtNameStr){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<CinemaInfo> infos = new ArrayList<CinemaInfo>();
		infos.clear();
		Cursor cursor = db.rawQuery("select * from cinema where districtName = ?", new String[]{districtNameStr});
		while(cursor.moveToNext()){
			System.out.println("下面还有数据呢。。。");
			String name = cursor.getString(cursor.getColumnIndex("cinemaName"));
			String address = cursor.getString(cursor.getColumnIndex("cinemaAddress"));
			CinemaInfo info = new CinemaInfo();
			info.setCinemaName(name);
			info.setCinemaAddress(address);
			infos.add(info);
			
		}
		cursor.close();
		db.close();
		
		return infos;
	}
	//将取得的数据添加到数据库
	public void insert(List<CinemaInfo> list){
		SQLiteDatabase db = helper.getWritableDatabase();
		for (int i = 0; i < list.size(); i++) {
			String address = list.get(i).getCinemaAddress();
			String cinemaName = list.get(i).getCinemaName();
			String districtName = list.get(i).getDistrictName();
			int cinemaId = list.get(i).getCinemaId();
			db.execSQL(
					"insert into cinema (cinemaAddress,cinemaName,districtName,cinemaId) values (?,?,?,?)",
					new Object[] { address, cinemaName, districtName, cinemaId });
		}
		db.close();
	}
	public void deleteAll(){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("cinema", null, null);
		db.close();
	}
	private void initUI() {
		helper = new CinemaSQLiteOpenHelper(BuyTicketSelectCinema.this);
		displayWidth = getWindowManager().getDefaultDisplay().getWidth();
		displayHeight = (int) (getWindowManager().getDefaultDisplay()
				.getHeight() * 0.6);
		// cinemaFilterView = new CinemaFilterView(BuyTicketSelectCinema.this);
		relativeFilter = (RelativeLayout) findViewById(R.id.relative_filter);
		relativeFilter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// startAnimation();
				popupWindow.showAsDropDown(relativeFilter);

			}
		});
		// indicator = (TabPageIndicator) findViewById(R.id.page_indicator);
		// pager = (ViewPager) findViewById(R.id.view_pager);
		buyTicketBack = (View) findViewById(R.id.buy_ticket_back);
		buyTicketBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				deleteAll();
				finish();
			}

		});
		listview = (AutoListView) findViewById(R.id.buy_ticket_select_cinema_list);
		// radio = (RadioGroup) findViewById(R.id.cinema_radio);
		// selectSeat = (RadioButton) findViewById(R.id.cinema_select_seat);
		// all = (RadioButton) findViewById(R.id.cinema_all);
		// groupPurchase = (RadioButton)
		// findViewById(R.id.cinema_group_purchase);
		// radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		// // TODO Auto-generated method stub
		// if (checkedId == selectSeat.getId()) {
		// cinemaInfo_seat.clear();
		// for (int i = 0; i < cinemaInfo.size(); i++) {
		// if (cinemaInfo.get(i).isSeat() == true) {
		// cinemaInfo_seat.add(cinemaInfo.get(i));
		// }
		// }
		// cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
		// cinemaInfo_seat, BuyTicketSelectCinema.this);
		// listview.setAdapter(cinemaAdapter);
		// } else if (checkedId == groupPurchase.getId()) {
		// cinemaInfo_group.clear();
		// for (int i = 0; i < cinemaInfo.size(); i++) {
		// if (cinemaInfo.get(i).isGroupPurchase() == true) {
		// cinemaInfo_group.add(cinemaInfo.get(i));
		// }
		// }
		// cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
		// cinemaInfo_group, BuyTicketSelectCinema.this);
		// listview.setAdapter(cinemaAdapter);
		// } else {
		// cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
		// cinemaInfo, BuyTicketSelectCinema.this);
		// listview.setAdapter(cinemaAdapter);
		// }
		// }
		//
		// });
	}

	@Override
	public void onLoad() {
		loadData(AutoListView.LOAD);
		start += 10;

	}

	@Override
	public void onRefresh() {
		loadData(AutoListView.REFRESH);
		start = 0;

	}

}