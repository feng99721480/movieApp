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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.CinemaFilmAdapter;
import com.wiseweb.fragment.adapter.HorizontalListViewAdapter;
import com.wiseweb.json.MoviePlanResult;
import com.wiseweb.json.MoviePlanResult.MoviePlan;
import com.wiseweb.json.MovieResult;
import com.wiseweb.json.MovieResult.Movie;
import com.wiseweb.movie.R;
import com.wiseweb.ui.HorizontalListView;
import com.wiseweb.ui.ListViewForScrollView;
import com.wiseweb.util.GetEnc;
import com.wiseweb.util.Util;

public class CinemaSelectFilmActivity extends Activity {
	private RelativeLayout toCinemaDetail;
	private ListViewForScrollView cinemaFilmList;
	private HorizontalListView hListView;
	private HorizontalListViewAdapter hListViewAdapter;
	private View olderSelectView = null;
	private CinemaFilmAdapter cinemaFilmAdapter = null;
	private List<String> startList;
	private ScrollView cinemaFilmScroll;
	private RadioGroup cinemaFilmDate;
	private TextView filmName, filmProperty, filmScore;
	private RelativeLayout cinemaFilmDetail;
	private RelativeLayout cinemaFilmTittleBack;
	// private List<FilmInfo> mFilmInfo = new ArrayList<FilmInfo>();
	private List<Bitmap> bms;
	private List<String> names = new ArrayList<String>();
	private List<String> properties = new ArrayList<String>();
	private List<String> scores = new ArrayList<String>();
	private List<MoviePlan> moviePlans;
	private static final int LIST_OF_MOVIES_IN_CINEMA = 0; 
	private static final int MOVIE_PLAN = 1;
	// private List<>

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cinema_select_film);

		/*
		 * WindowManager wm =
		 * (WindowManager)this.getSystemService(Context.WINDOW_SERVICE); final
		 * int width = wm.getDefaultDisplay().getWidth(); final int height =
		 * wm.getDefaultDisplay().getHeight();
		 */
		initUI();
		cinemaFilmScroll.smoothScrollTo(0, 0);
		toCinemaDetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CinemaSelectFilmActivity.this,
						CinemaDetailActivity.class);
				startActivity(intent);
			}
		});
		// 一进入这个界面就显示此影院上映的电影
//		 new Thread(runnable).start();
//		startList = getData();
//		cinemaFilmAdapter = new CinemaFilmAdapter(startList, this);
//		cinemaFilmList.setAdapter(cinemaFilmAdapter);
		cinemaFilmList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(CinemaSelectFilmActivity.this, "点击了",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(CinemaSelectFilmActivity.this,
						SelectSeatBuyTicketActivity.class);
				startActivity(intent);
			}

		});

		cinemaFilmDate
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup radioGroup,
							int checkedId) {
						// TODO Auto-generated method stub
//						if (checkedId == R.id.cinema1) {
//
//							cinemaFilmAdapter = new CinemaFilmAdapter(
//									startList, CinemaSelectFilmActivity.this);
//							cinemaFilmList.setAdapter(cinemaFilmAdapter);
//						}
//						if (checkedId == R.id.cinema2) {
//							List<String> day2 = new ArrayList<String>();
//							day2.add("11:00");
//							day2.add("11:00");
//							day2.add("11:00");
//							cinemaFilmAdapter = new CinemaFilmAdapter(day2,
//									CinemaSelectFilmActivity.this);
//							cinemaFilmList.setAdapter(cinemaFilmAdapter);
//						}
//						if (checkedId == R.id.cinema3) {
//							List<String> day3 = new ArrayList<String>();
//							day3.add("13:00");
//							day3.add("13:00");
//							day3.add("13:00");
//							cinemaFilmAdapter = new CinemaFilmAdapter(day3,
//									CinemaSelectFilmActivity.this);
//							cinemaFilmList.setAdapter(cinemaFilmAdapter);
//						}
//						if (checkedId == R.id.cinema4) {
//							List<String> day2 = new ArrayList<String>();
//							day2.add("17:00");
//							day2.add("18:00");
//							day2.add("19:00");
//							cinemaFilmAdapter = new CinemaFilmAdapter(day2,
//									CinemaSelectFilmActivity.this);
//							cinemaFilmList.setAdapter(cinemaFilmAdapter);
//						}
//						if (checkedId == R.id.cinema5) {
//							List<String> day2 = new ArrayList<String>();
//							day2.add("08:00");
//							day2.add("09:00");
//							day2.add("10:00");
//							cinemaFilmAdapter = new CinemaFilmAdapter(day2,
//									CinemaSelectFilmActivity.this);
//							cinemaFilmList.setAdapter(cinemaFilmAdapter);
//						}
//						if (checkedId == R.id.cinema6) {
//							List<String> day2 = new ArrayList<String>();
//							day2.add("11:00");
//							day2.add("11:00");
//							day2.add("11:00");
//							cinemaFilmAdapter = new CinemaFilmAdapter(day2,
//									CinemaSelectFilmActivity.this);
//							cinemaFilmList.setAdapter(cinemaFilmAdapter);
//						}

					}

				});
		cinemaFilmDetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(CinemaSelectFilmActivity.this,
						FilmDetailsActivity.class);
				startActivity(intent);
			}

		});
		cinemaFilmTittleBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}

		});
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case LIST_OF_MOVIES_IN_CINEMA:
				hListViewAdapter = new HorizontalListViewAdapter(
						getApplicationContext(), bms);
				hListView.setAdapter(hListViewAdapter);
				hListViewAdapter.notifyDataSetChanged();
				break;
			case MOVIE_PLAN:
				cinemaFilmAdapter = new CinemaFilmAdapter(moviePlans, CinemaSelectFilmActivity.this);
				cinemaFilmList.setAdapter(cinemaFilmAdapter);
				break;
			}
		}

	};
	// 影院上映电影列表
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			HashMap<String, Object> params = new HashMap<String, Object>();
			// action
			params.put("action", "cinema_movies");
			// time_stamp
			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp + "");
			// cinema_id (get the cinema_id)
			long cinema_id = 66;
			params.put("cinema_id", cinema_id);
			// start 开始位置
			int start = 0;
			params.put("start", start);
			// count 数量
			int count = 10;
			params.put("count", count);
			// SharedPreferences s = mMainActivity.getSharedPreferences("city",
			// Context.MODE_PRIVATE);
			// String cityId = s.getString("cityId", null);
			// params.put("city_id", cityId);
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "cinema_id=" + cinema_id
					+ "&" + "start=" + start + "&" + "count=" + count + "enc="
					+ enc + "&" + "time_stamp=" + time_stamp);
			System.out.println(Constant.baseURL + "action="
					+ params.get("action") + "&" + "cinema_id=" + cinema_id
					+ "&" + "start=" + start + "&" + "count=" + count + "enc="
					+ enc + "&" + "time_stamp=" + time_stamp);
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					Gson gson = new Gson();
					MovieResult movieResult = gson.fromJson(result,
							MovieResult.class);
					List<Movie> movies = movieResult.getMovies();
					// mFilmInfo.clear();
					for (int i = 0; i < movies.size(); i++) {
						// FilmInfo film = new FilmInfo();
						String movieName = "";
						String movieScore = "";
						Boolean has2D;
						Boolean has3D;
						Boolean hasImax;
						String movieProperty;
						// String actionTime = "无数据";
						String posterPath;
						if (!(movies.get(i).getMovieName().equals(null))) {
							movieName = movies.get(i).getMovieName();
							// film.setFilmName(movieName);
						}
						names.add(movieName);
						if (!(movies.get(i).getScore().equals(null))) {
							movieScore = movies.get(i).getScore();

						} else {
							movieScore = "无评分";
						}
						scores.add(movieScore);
						// film.setScore(movieScore);
						has2D = movies.get(i).getHas2D();
						has3D = movies.get(i).getHas3D();
						hasImax = movies.get(i).getHasImax();
						if (has2D && hasImax == true) {
							movieProperty = "Imax2D";
						} else if (has3D && hasImax == true) {
							movieProperty = "Imax3D";
						} else if (hasImax == false && has3D == true) {
							movieProperty = "3D";
						} else {
							movieProperty = "";
						}
						properties.add(movieProperty);
						// film.setiMax(movieProperty);
						// if (!(movies.get(i).getPublishTime().equals(null))) {
						// actionTime = movies.get(i).getPublishTime();
						// }
						// film.setActionTime(actionTime);
						posterPath = movies.get(i).getPosterPath();
						if (!posterPath.equals(null)) {
							Bitmap filmImage = Util.getBitmap(posterPath);
							// film.setImgId(filmImage);
							bms.add(filmImage);
						}

						// mFilmInfo.add(film);
					}
					Message msg = new Message();
					// Bundle data = new Bundle();
					// msg.setData(data);
					msg.what = LIST_OF_MOVIES_IN_CINEMA;
					handler.sendMessage(msg);
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	};
	// 电影排期
	Runnable planRunnable = new Runnable() {

		@Override
		public void run() {
			HashMap<String, Object> params = new HashMap<String, Object>();
			// action
			params.put("action", "movie_plan");
			// time_stamp
			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp + "");
			// cinema_id (get the cinema_id)
			long cinema_id = 66;
			params.put("cinema_id", cinema_id);
			// movie_id(get the movie_id)
			long movie_id = 7250;
			params.put("movie_id", movie_id);
			// start 开始位置
			int start = 0;
			params.put("start", start);
			// count 数量
			int count = 10;
			params.put("count", count);
			// SharedPreferences s = mMainActivity.getSharedPreferences("city",
			// Context.MODE_PRIVATE);
			// String cityId = s.getString("cityId", null);
			// params.put("city_id", cityId);
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "cinema_id=" + cinema_id
					+ "&" + "movie_id=" + movie_id + "&" + "start=" + start
					+ "&" + "count=" + count + "enc=" + enc + "&"
					+ "time_stamp=" + time_stamp);
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					Gson gson = new Gson();
					MoviePlanResult moviePlanResult = gson.fromJson(result,
							MoviePlanResult.class);
					List<MoviePlan> plans = moviePlanResult.getPlans();
					// mFilmInfo.clear();
					moviePlans = new ArrayList<MoviePlan>();
					//获得相应的数据 再展示出来
					for (int i = 0; i < plans.size(); i++) {
						MoviePlan moviePlan = new MoviePlan();
						//开场时间
						String featureTime = "";
						if (!plans.get(i).getFeatureTime().equals(null)) {
							featureTime = plans.get(i).getFeatureTime();
						}
						moviePlan.setFeatureTime(featureTime);
						//屏幕类型
						String screenType = "";
						if(!plans.get(i).getScreenType().equals(null)){
							screenType = plans.get(i).getScreenType();
						}
						moviePlan.setScreenType(screenType);
						//厅名
						String hallName = "";
						if(!plans.get(i).getHallName().equals(null)){
							hallName = plans.get(i).getHallName();
						}
						moviePlan.setHallName(hallName);
						//场次售价
						String price = "";
						if(!plans.get(i).getPrice().equals(null)){
							price = plans.get(i).getPrice();
						}
						moviePlan.setPrice(price);
						moviePlans.add(moviePlan);
					}
					
					Message msg = new Message();
					// Bundle data = new Bundle();
					// msg.setData(data);
					msg.what = MOVIE_PLAN;
					handler.sendMessage(msg);
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	};

	public void initUI() {
		toCinemaDetail = (RelativeLayout) findViewById(R.id.to_cinema_detail);
		// ///////////////////////////////////
		hListView = (HorizontalListView) findViewById(R.id.horizon_listview);
		// final int[] ids = { R.drawable.runman, R.drawable.runman,
		// R.drawable.runman, R.drawable.runman, R.drawable.runman,
		// R.drawable.runman };
		// hListViewAdapter = new HorizontalListViewAdapter(
		// getApplicationContext(), bms);
		// hListView.setAdapter(hListViewAdapter);
		hListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO Auto-generated method stub
				// if(olderSelectView == null){
				// olderSelectView = view;
				// }else{
				// olderSelectView.setSelected(false);
				// olderSelectView = null;
				// }
				// olderSelectView = view;
				// view.setSelected(true);
				// previewImg.setImageResource(ids[position]);
				hListViewAdapter.setSelectIndex(position);
				// WindowManager wm =
				// (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
				// hListView.scrollTo(wm.getDefaultDisplay().getWidth(),0);
				// hListView.scrollTo(300);
				hListViewAdapter.notifyDataSetChanged();
				// 不能这样写吧
//				switch (position) {
//				case 0:
//					filmName.setText("电影名称");
//					filmProperty.setText("iMax3D");
//					filmProperty
//							.setBackgroundResource(R.drawable.film_property_imax);
//					filmScore.setText("8.3分");
//					cinemaFilmAdapter = new CinemaFilmAdapter(startList,
//							CinemaSelectFilmActivity.this);
//					cinemaFilmList.setAdapter(cinemaFilmAdapter);
//					break;
//				case 1:
//					filmName.setText("电影2");
//					filmProperty.setText("iMax3D");
//					filmProperty
//							.setBackgroundResource(R.drawable.film_property_imax);
//					filmScore.setText("8.3分");
//					List<String> day2 = new ArrayList<String>();
//					day2.add("11:00");
//					day2.add("11:00");
//					day2.add("11:00");
//					cinemaFilmAdapter = new CinemaFilmAdapter(day2,
//							CinemaSelectFilmActivity.this);
//					cinemaFilmList.setAdapter(cinemaFilmAdapter);
//					break;
//				case 2:
//					filmName.setText("电影3");
//					filmProperty.setText("iMax3D");
//					filmProperty
//							.setBackgroundResource(R.drawable.film_property_imax);
//					filmScore.setText("8.3分");
//					List<String> day3 = new ArrayList<String>();
//					day3.add("13:00");
//					day3.add("13:00");
//					day3.add("13:00");
//					cinemaFilmAdapter = new CinemaFilmAdapter(day3,
//							CinemaSelectFilmActivity.this);
//					cinemaFilmList.setAdapter(cinemaFilmAdapter);
//					break;
//				case 3:
//					filmName.setText("电影4");
//					filmProperty.setText("iMax3D");
//					filmProperty
//							.setBackgroundResource(R.drawable.film_property_imax);
//					filmScore.setText("8.3分");
//					List<String> day4 = new ArrayList<String>();
//					day4.add("13:00");
//					day4.add("13:00");
//					day4.add("13:00");
//					cinemaFilmAdapter = new CinemaFilmAdapter(day4,
//							CinemaSelectFilmActivity.this);
//					cinemaFilmList.setAdapter(cinemaFilmAdapter);
//					break;
//				case 4:
//					filmName.setText("电影5");
//					filmProperty.setText("iMax3D");
//					filmProperty
//							.setBackgroundResource(R.drawable.film_property_imax);
//					filmScore.setText("8.3分");
//					List<String> day5 = new ArrayList<String>();
//					day5.add("13:00");
//					day5.add("13:00");
//					day5.add("13:00");
//					cinemaFilmAdapter = new CinemaFilmAdapter(day5,
//							CinemaSelectFilmActivity.this);
//					cinemaFilmList.setAdapter(cinemaFilmAdapter);
//					break;
//				case 5:
//					filmName.setText("电影6");
//					filmProperty.setText("iMax3D");
//					filmProperty
//							.setBackgroundResource(R.drawable.film_property_imax);
//					filmScore.setText("8.3分");
//					List<String> day6 = new ArrayList<String>();
//					day6.add("13:00");
//					day6.add("13:00");
//					day6.add("13:00");
//					cinemaFilmAdapter = new CinemaFilmAdapter(day6,
//							CinemaSelectFilmActivity.this);
//					cinemaFilmList.setAdapter(cinemaFilmAdapter);
//					break;
//				}

			}
		});
		cinemaFilmList = (ListViewForScrollView) findViewById(R.id.cinema_film_list);
		cinemaFilmScroll = (ScrollView) findViewById(R.id.cinema_film_scroll);
		cinemaFilmDate = (RadioGroup) findViewById(R.id.cinema_film_date);
		filmName = (TextView) findViewById(R.id.film_name);
		filmProperty = (TextView) findViewById(R.id.film_property);
		filmScore = (TextView) findViewById(R.id.film_score);
		cinemaFilmDetail = (RelativeLayout) findViewById(R.id.cinema_film_detail);
		cinemaFilmTittleBack = (RelativeLayout) findViewById(R.id.cinema_film_title_back);
	}

	public List<String> getData() {
		List<String> list = new ArrayList<String>();

		list.add("17:25");
		list.add("17:25");
		list.add("17:25");
		list.add("17:25");
		list.add("17:25");
		list.add("17:25");
		list.add("17:25");
		return list;

	}
}
