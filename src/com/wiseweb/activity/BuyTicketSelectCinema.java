package com.wiseweb.activity;

import java.io.IOException;
import java.text.ParseException;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;

import com.viewpagerindicator.TabPageIndicator;
import com.wiseweb.bean.CinemaInfo;
import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.BuyTicketSelectCinemaListAdapter;
import com.wiseweb.fragment.adapter.TabPageIndicatorAdapter;
import com.wiseweb.json.MoviePlan;
import com.wiseweb.movie.R;
import com.wiseweb.util.GetEnc;
import com.wiseweb.util.Util;

public class BuyTicketSelectCinema extends FragmentActivity {
	private TabPageIndicator indicator;
	private ViewPager pager;
	private FragmentPagerAdapter adapter;
	private ListView listview;
	private View buyTicketBack;
	private List<CinemaInfo> cinemaInfo = new ArrayList<CinemaInfo>();
//	private List<CinemaInfo> cinemaInfo_seat = new ArrayList<CinemaInfo>();
//	private List<CinemaInfo> cinemaInfo_group = new ArrayList<CinemaInfo>();
	private BuyTicketSelectCinemaListAdapter cinemaAdapter;
	private SharedPreferences movieConfigure;
	private String movieName;
	private TextView filmName;
	private long movieId;
	private List<String> dates = new ArrayList<String>();
	private String selectedDate = "";  //选中的日期

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_buy_ticket_select_cinema);
		ShareSDK.initSDK(this);
		initUI();
		movieConfigure = getSharedPreferences("movieConfigure", Context.MODE_PRIVATE);
		movieName = movieConfigure.getString("movieName", null);    //取得movieName
		movieId = movieConfigure.getLong("movieId", 0);       //取得movieId
		filmName.setText(movieName);
		adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		
		//
		indicator.setViewPager(pager);

		cinemaInfo.add(new CinemaInfo("UME国际影城", true, true, false, false,
				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));

		cinemaInfo.add(new CinemaInfo("UME国际影城", true, false, false, false,
				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));

		cinemaInfo.add(new CinemaInfo("UME国际影城", false, true, false, false,
				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));

		cinemaInfo.add(new CinemaInfo("UME国际影城", false, false, true, false,
				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));
		cinemaAdapter = new BuyTicketSelectCinemaListAdapter(cinemaInfo,
				BuyTicketSelectCinema.this);
		listview.setAdapter(cinemaAdapter);
		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				Toast.makeText(getApplicationContext(),
						Constant.CONTENT[position], Toast.LENGTH_SHORT).show();
				
//					cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
//							cinemaInfo, BuyTicketSelectCinema.this);
//					listview.setAdapter(cinemaAdapter);
//				
//					cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
//							cinemaInfo_seat, BuyTicketSelectCinema.this);
//					listview.setAdapter(cinemaAdapter);
//				
//					cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
//							cinemaInfo_group, BuyTicketSelectCinema.this);
//					listview.setAdapter(cinemaAdapter);
				
				// String name = adapter.getPageTitle(position).toString();

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}
	
//	Runnable planRunnable = new Runnable() {
//
//		@Override
//		public void run() {
//			HashMap<String, Object> params = new HashMap<String, Object>();
//			// action
//			params.put("action", "movie_plan");
//			// time_stamp
//			Date date = new Date();
//			long time_stamp = date.getTime();
//			params.put("time_stamp", time_stamp + "");
//			// cinema_id (get the cinema_id)
//			int cinemaId = 66;
//			params.put("cinemaId", cinemaId);
//			// movie_id(get the movie_id)
//			// long movie_id = 7250;
//			params.put("movie_id", movieId);
//			// start 开始位置
//			int start = 0;
//			params.put("start", start);
//			// count 数量
//			int count = 10;
//			params.put("count", count);
//			
//			String enc = GetEnc.getEnc(params, "wiseMovie");
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
//					+ params.get("action") + "&" + "cinemaId=" + cinemaId + "&"
//					+ "movie_id=" + movieId + "&" + "start=" + start + "&"
//					+ "count=" + count + "enc=" + enc + "&" + "time_stamp="
//					+ time_stamp);
//			System.out.println("plans---" + Constant.baseURL + "action="
//					+ params.get("action") + "&" + "cinemaId=" + cinemaId + "&"
//					+ "movie_id=" + movieId + "&" + "start=" + start + "&"
//					+ "count=" + count + "enc=" + enc + "&" + "time_stamp="
//					+ time_stamp);
//			HttpResponse httpResponse;
//			String result;
//			try {
//				httpResponse = httpClient.execute(getMethod);
//				if (httpResponse.getStatusLine().getStatusCode() == 200) {
//					HttpEntity entity = httpResponse.getEntity();
//					result = EntityUtils.toString(entity, "utf-8");
//
//					JSONObject object = new JSONObject(result)
//							.getJSONObject("plans");
//					JSONArray datesArray = object.getJSONArray("date");
//					
//
//					dates.clear();
//					List<String> list = new ArrayList<String>();
//					for (int i = 0; i < datesArray.length(); i++) {
//						String dateStr = (String) datesArray.getString(i);
//						dates.add(dateStr);
//					}
//					
//					List<MoviePlan> plans = new ArrayList<MoviePlan>();
//					JSONArray plansArray = object.getJSONObject("plans")
//							.getJSONArray(selectedDate);
//					// 获得相应的数据 再展示出来
//					String featureTime = "";
//					String screenType = "";
//					String hallName = "";
//					String price = "";
//					for (int i = 0; i < plansArray.length(); i++) {
//						MoviePlan moviePlan = new MoviePlan();
//						// 开场时间
//						JSONObject aPlan = plansArray.getJSONObject(i);
//						if (aPlan.getString("featureTime") != null) {
//							featureTime = Util.getHourAndMin(aPlan
//									.getString("featureTime"));
//						}
//						moviePlan.setFeatureTime(featureTime);
//						// 屏幕类型
//
//						if (aPlan.getString("screenType") != null) {
//							screenType = aPlan.getString("screenType");
//						}
//						moviePlan.setScreenType(screenType);
//						// 厅名
//
//						if (aPlan.getString("hallName") != null) {
//							hallName = aPlan.getString("hallName");
//						}
//						moviePlan.setHallName(hallName);
//						// 场次售价
//
//						if (aPlan.getString("price") != null) {
//							price = aPlan.getString("price");
//						}
//						moviePlan.setPrice(price);
//						
//						moviePlans.add(moviePlan);
//					}
//
//					Message msg = new Message();
//					// Bundle data = new Bundle();
//					// msg.setData(data);
//					msg.what = MOVIE_PLAN;
//					handler.sendMessage(msg);
//				}
//			} catch (ClientProtocolException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (JSONException e) {
//				e.printStackTrace();
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initUI() {
		// indicator = (TabPageIndicator) findViewById(R.id.page_indicator);
		// pager = (ViewPager) findViewById(R.id.view_pager);
		buyTicketBack = (View) findViewById(R.id.buy_ticket_back);
		buyTicketBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});
		indicator = (TabPageIndicator) findViewById(R.id.page_indicator);

		pager = (ViewPager) findViewById(R.id.view_pager);
		listview = (ListView) findViewById(R.id.buy_ticket_select_cinema_list);
		filmName = (TextView)findViewById(R.id.film_name);

	}
}