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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.CinemaFilmAdapter;
import com.wiseweb.fragment.adapter.HorizontalListViewAdapter;
import com.wiseweb.json.MoviePlan;
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
	private List<Bitmap> bms = new ArrayList<Bitmap>(); // 影院上映电影的图片
	private List<String> names = new ArrayList<String>(); // 电影名称
	private List<String> properties = new ArrayList<String>(); // 电影类型
	private List<String> scores = new ArrayList<String>(); // 电影评分
	private List<Long> movieIds = new ArrayList<Long>();
	private List<MoviePlan> moviePlans = new ArrayList<MoviePlan>();
	private static final int LIST_OF_MOVIES_IN_CINEMA = 0;
	private static final int MOVIE_PLAN = 1;
	private long movieId; // 存放被点击movie的movieId
	private SharedPreferences cinemaConfig; // 影院
	private String cinemaName;
	private int cinemaId;
	private String cinemaAddress;
	private TextView cinemaNameText, cinemaBriefName, cinemaBriefAddress;
	private List<String> dates = new ArrayList<String>();
	// private String selectedDate = "2015-05-15";
	private String selectedDate = "";
	private int isCount = 0;
	private SharedPreferences movieConfigure;

	// private List<>

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cinema_select_film);
		initView();
		cinemaFilmScroll.smoothScrollTo(0, 0);
		// 获得影院相关数据
		/*
		 * cinemaConfig = getSharedPreferences("cinemaConfig",
		 * Context.MODE_PRIVATE); cinemaName =
		 * cinemaConfig.getString("cinemaName", null); cinemaId =
		 * cinemaConfig.getInt("cinemaId", 0); cinemaAddress =
		 * cinemaConfig.getString("cinemaAddress", null); //设置影院的名称和地址
		 * cinemaBriefName.setText(cinemaName);
		 * cinemaBriefAddress.setText(cinemaAddress);
		 */
		selectedDate = Util.getSystemYearMonthDay();
		System.out.println("selectedDate" + selectedDate);
		Thread cinemaMovies = new Thread(runnable);
		cinemaMovies.start();
		try {
			cinemaMovies.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		toCinemaDetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CinemaSelectFilmActivity.this,
						CinemaDetailActivity.class);
				startActivity(intent);
			}
		});

		cinemaFilmList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(CinemaSelectFilmActivity.this,
						SelectSeatBuyTicketActivity.class);
				startActivity(intent);
			}

		});
		// 上映电影日期的选择
		cinemaFilmDate
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup radioGroup,
							int checkedId) {
						RadioButton tempButton = (RadioButton) findViewById(checkedId);
						selectedDate = tempButton.getText().toString();
						Thread moviePlan = new Thread(planRunnable);
						moviePlan.start();
						// cinemaFilmDate.removeAllViews();
						try {
							moviePlan.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				});
		cinemaFilmDetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle data = new Bundle();
				data.putLong("movieId", movieId);
				// 既然影院上映的 肯定都是已经上映的电影
				data.putBoolean("movieOnCome", true);
				Intent intent = new Intent();
				intent.putExtras(data);
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

				if (isCount == 0) {
					for (int i = 0; i < dates.size(); i++) {
						RadioButton tempButton = new RadioButton(
								CinemaSelectFilmActivity.this);
						tempButton
								.setBackgroundResource(R.drawable.cinema_film_date);
						tempButton.setPadding(20, 15, 20, 15);
						// LinearLayout.LayoutParams lp = new
						// LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
						// LinearLayout.LayoutParams.WRAP_CONTENT, 1); // , 1选写
						// lp.setMargins(10, 20, 30, 40);
						//
						// tempButton.setLayoutParams(lp);
						// tempButton.requestLayout();
						tempButton
								.setButtonDrawable(android.R.color.transparent);
						tempButton.setText(dates.get(i).toString());
						tempButton.setTag(i);
						tempButton
								.setTextColor(R.drawable.cinema_film_date_textcolor);
						cinemaFilmDate.addView(tempButton,
								LinearLayout.LayoutParams.MATCH_PARENT,
								LinearLayout.LayoutParams.WRAP_CONTENT);
						if (i == 0) {
							tempButton.setChecked(true);
						}

					}
					isCount++;
				}
				cinemaFilmAdapter = new CinemaFilmAdapter(moviePlans,
						CinemaSelectFilmActivity.this);
				cinemaFilmList.setAdapter(cinemaFilmAdapter);
				cinemaFilmAdapter.notifyDataSetChanged();
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
			int cinemaId = 66;
			params.put("cinema_id", cinemaId);
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
			HttpGet getMethod = new HttpGet(Constant.baseURL
					+ "action=cinema_movies" + "&" + "cinemaId=" + cinemaId
					+ "&" + "start=" + start + "&" + "count=" + count + "enc="
					+ enc + "&" + "time_stamp=" + time_stamp);
			System.out.println(Constant.baseURL
					+ "action=cinema_movies" + "&" + "cinemaId=" + cinemaId
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
						long id;
						String movieName = "";
						String movieScore = "";
						Boolean has2D;
						Boolean has3D;
						Boolean hasImax;
						String movieProperty;
						// String actionTime = "无数据";
						String posterPath = "";
						Bitmap filmImage;
						if (!(movies.get(i).getMovieName().equals(null))) {
							movieName = movies.get(i).getMovieName();
							// film.setFilmName(movieName);
						}
						names.add(movieName);
						// 影片id
						id = movies.get(i).getMovieId();
						movieIds.add(id);
						if (!(movies.get(i).getScore().equals(null))) {
							movieScore = movies.get(i).getScore();

						} else {
							movieScore = "无评分";
							
						}
						scores.add(movieScore);

						has2D = movies.get(i).getHas2D();
						has3D = movies.get(i).getHas3D();
						hasImax = movies.get(i).getHasImax();
						if (has2D != null && hasImax != null && has2D
								&& hasImax == true) {
							movieProperty = "Imax2D";
						} else if (has3D != null && hasImax != null && has3D
								&& hasImax == true) {
							movieProperty = "Imax3D";
						} else if (has3D != null && has3D == true) {
							movieProperty = "3D";
						} else {
							movieProperty = "";
						}
						properties.add(movieProperty);

						// 获得图片
						if (movies.get(i).getPathSquare() != null
								|| movies.get(i).getPathHorizonB() != null
								|| movies.get(i).getPathVerticalS() != null) {
							if (movies.get(i).getPathSquare() != null) {
								posterPath = movies.get(i).getPathSquare();

							} else if (movies.get(i).getPathHorizonB() != null) {
								posterPath = movies.get(i).getPathHorizonB();
							} else if (movies.get(i).getPathVerticalS() != null) {
								posterPath = movies.get(i).getPathVerticalS();
							}
							filmImage = Util.getBitmap(posterPath);

						} else {
							// 将drawable对象转为bitmap
							Resources res = getResources();
							filmImage = BitmapFactory.decodeResource(res,
									R.drawable.ic_empty_search_result);
						}
						bms.add(filmImage);
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
			int cinemaId = 66;
			params.put("cinemaId", cinemaId);
			// movie_id(get the movie_id)
			// long movie_id = 7250;
			params.put("movie_id", movieId);
			// start 开始位置
			int start = 0;
			params.put("start", start);
			// count 数量
			int count = 10;
			params.put("count", count);

			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "cinemaId=" + cinemaId + "&"
					+ "movie_id=" + movieId + "&" + "start=" + start + "&"
					+ "count=" + count + "enc=" + enc + "&" + "time_stamp="
					+ time_stamp);
			System.out.println("plans---" + Constant.baseURL + "action="
					+ params.get("action") + "&" + "cinemaId=" + cinemaId + "&"
					+ "movie_id=" + movieId + "&" + "start=" + start + "&"
					+ "count=" + count + "enc=" + enc + "&" + "time_stamp="
					+ time_stamp);
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");

					JSONObject object = new JSONObject(result)
							.getJSONObject("plans");
					JSONArray datesArray = object.getJSONArray("date");
					moviePlans.clear();

					dates.clear();
					List<String> list = new ArrayList<String>();
					for (int i = 0; i < datesArray.length(); i++) {
						String dateStr = (String) datesArray.getString(i);
						dates.add(dateStr);
					}

					List<MoviePlan> plans = new ArrayList<MoviePlan>();
					// selectedDate ="2015-05-21";
					JSONArray plansArray = object.getJSONObject("plans")
							.getJSONArray(selectedDate);
					// 获得相应的数据 再展示出来
					String featureTime = "";
					String screenType = "";
					String hallName = "";
					String price = "";
					for (int i = 0; i < plansArray.length(); i++) {
						MoviePlan moviePlan = new MoviePlan();
						// 开场时间
						JSONObject aPlan = plansArray.getJSONObject(i);
						if (aPlan.getString("featureTime") != null) {
							featureTime = Util.getHourAndMin(aPlan
									.getString("featureTime"));
						}
						moviePlan.setFeatureTime(featureTime);
						// 屏幕类型

						if (aPlan.getString("screenType") != null) {
							screenType = aPlan.getString("screenType");
						}
						moviePlan.setScreenType(screenType);
						// 厅名

						if (aPlan.getString("hallName") != null) {
							hallName = aPlan.getString("hallName");
						}
						moviePlan.setHallName(hallName);
						// 场次售价

						if (aPlan.getString("price") != null) {
							price = aPlan.getString("price");
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
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	};

	public void initView() {
		toCinemaDetail = (RelativeLayout) findViewById(R.id.to_cinema_detail);
		cinemaNameText = (TextView) findViewById(R.id.cinema_film_name);// 标题的电影名称
		cinemaBriefName = (TextView) findViewById(R.id.cinema_name);
		cinemaBriefAddress = (TextView) findViewById(R.id.cinema_address);
		hListView = (HorizontalListView) findViewById(R.id.horizon_listview);
		hListView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});
		hListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// if(olderSelectView == null){
				// olderSelectView = view;
				// }else{
				// olderSelectView.setSelected(false);
				// olderSelectView = null;
				// }
				// olderSelectView = view;
				// view.setSelected(true);
				// previewImg.setImageResource(ids[position]);
				dates.clear();
				isCount = 0;
				cinemaFilmDate.removeAllViews();

				hListViewAdapter.setSelectIndex(position);

				hListViewAdapter.notifyDataSetChanged();
				filmName.setText(names.get(position));
				filmProperty.setVisibility(View.VISIBLE);
				if (properties.get(position).equals("iMax3D")) {
					filmProperty
							.setBackgroundResource(R.drawable.film_property_imax);
				} else if (properties.get(position).equals("iMax2D")) {

					filmProperty
							.setBackgroundResource(R.drawable.film_property_imax);
				} else if (properties.get(position).equals("3D")) {
					filmProperty
							.setBackgroundResource(R.drawable.film_property_3d);
				} else {
					filmProperty.setVisibility(View.GONE);
				}
				filmProperty.setText(properties.get(position));
				filmScore.setText(scores.get(position) + "分");
				// 得到点击的那个movieId,然后根据应用此movieId查询出来排期
				movieId = movieIds.get(position);
				// 保存信息
				movieConfigure = getSharedPreferences("movieConfigure",
						Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = movieConfigure.edit();
				editor.putLong("movieId", movieId);
				editor.putString("movieName", names.get(position));
				editor.putBoolean("movieOnCome", true);
				editor.commit();
				/**
				 * 开启获得电影排期的进程
				 */
				Thread plansThread = new Thread(planRunnable);
				plansThread.start();
				try {
					plansThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
}
