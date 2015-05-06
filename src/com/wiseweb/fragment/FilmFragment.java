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
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wiseweb.activity.CityListActivity;
import com.wiseweb.activity.FilmDetailsActivity;
import com.wiseweb.activity.FilmSearchActivity;
import com.wiseweb.activity.MainActivity;
import com.wiseweb.bean.FilmInfo;
import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.FilmAdapter;
import com.wiseweb.fragment.adapter.UpComingFilmAdapter;
import com.wiseweb.json.MovieComingResult;
import com.wiseweb.json.MovieComingResult.MovieComing;
import com.wiseweb.json.MovieResult;
import com.wiseweb.json.MovieResult.Movie;
import com.wiseweb.movie.R;
import com.wiseweb.util.GetEnc;
import com.wiseweb.util.Util;

public class FilmFragment extends BaseFragment {

	private MainActivity mMainActivity;
	private ListView mListView;
	private FilmAdapter mFilmAdapter;
	private UpComingFilmAdapter upComingFilmAdapter;
	private List<FilmInfo> mFilmInfo = new ArrayList<FilmInfo>();
	private List<FilmInfo> mOnFilmInfo = new ArrayList<FilmInfo>();
	private RadioGroup radioGroup;
	private RadioButton releasing;
	private RadioButton onReleasing;
	private View area;
	private TextView city;
	private SharedPreferences cityPreferences;
	private ImageView filmSearch;
	private Button buyTicket;
	private BitmapDrawable bd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View filmLayout = inflater.inflate(R.layout.film_layout, container,
				false);

		mMainActivity = (MainActivity) getActivity();
		mFragmentManager = getActivity().getFragmentManager();
		city = (TextView) filmLayout.findViewById(R.id.city_title);
		cityPreferences = mMainActivity.getSharedPreferences("city",
				Context.MODE_PRIVATE);
		// SharedPreferences.Editor editor = cityPreferences.edit();
		// editor.putString("city", "北京");
		// editor.commit();
		city.setText(cityPreferences.getString("city", null));

		mListView = (ListView) filmLayout.findViewById(R.id.listview_film);
		// mFilmAdapter = new FilmAdapter(mFilmInfo, mMainActivity);
		// mListView.setAdapter(mFilmAdapter);
		releasing = (RadioButton) filmLayout.findViewById(R.id.releasingFilm); //
		onReleasing = (RadioButton) filmLayout //
				.findViewById(R.id.onreleasingFilm);
		radioGroup = (RadioGroup) filmLayout
				.findViewById(R.id.film_title_group);
		filmSearch = (ImageView) filmLayout.findViewById(R.id.film_search);
		filmSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mMainActivity, FilmSearchActivity.class);
				startActivity(intent);
			}

		});
		area = (View) filmLayout.findViewById(R.id.area);

		area.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View area) {
				Intent intent = new Intent();
				// intent.putExtra("flag", "filmFragment");
				intent.setClass((MainActivity) getActivity(),
						CityListActivity.class);

				startActivityForResult(intent, 1);
			}
		});
		// mListView.setEnabled(true);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Toast.makeText(getApplicationContext(), "test",
				// Toast.LENGTH_LONG).show();
				// Toast.makeText(mMainActivity,
				// mFilmInfo.get(position).toString(), Toast.LENGTH_SHORT)
				// .show();
				Intent intent = new Intent();
				intent.setClass(mMainActivity, FilmDetailsActivity.class);
				startActivity(intent);
			}

		});
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
				if (checkedId == onReleasing.getId()) {
					// mFilmInfo.clear();
					upComingFilmAdapter = new UpComingFilmAdapter(mOnFilmInfo,
							mMainActivity);
					mListView.setAdapter(upComingFilmAdapter);
				} else {
					// mFilmInfo.clear();
					// new Thread(onReleasingRunnable).start();
					mFilmAdapter = new FilmAdapter(mFilmInfo, mMainActivity);
					mListView.setAdapter(mFilmAdapter);

				}
			}

		});
		// new Thread(runnable).start();
		bd = (BitmapDrawable) (mMainActivity.getResources()
				.getDrawable(R.drawable.runman));
		Bitmap runman = bd.getBitmap();
		mFilmInfo.add(new FilmInfo("奔跑吧，兄弟", "超级喜剧", "导演", "邓超，李晨", "大陆",
				"88分钟", "超级好看的电影", "很好看的电影啊", "2015-01-30", runman, "6.8分",
				"2D", true, "今天127家影院上映102场"));

		mOnFilmInfo.add(new FilmInfo("RUNNING", "超级喜剧", "导演", "邓超，李晨", "大陆",
				"88分钟", "超级好看的电影", "很好看的电影啊", "2015-01-30", runman, "6.8分",
				"iMax3D", true, "今天127家影院上映102场"));
		return filmLayout;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (2 == resultCode) {
			String cityName = data.getExtras().getString("city");
			city.setText(cityName);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				// Bundle data = msg.getData();
				// String val = data.getString("result");
				mFilmAdapter = new FilmAdapter(mFilmInfo, mMainActivity);
				mListView.setAdapter(mFilmAdapter);
				mFilmAdapter.notifyDataSetChanged();
			case 1:
				upComingFilmAdapter = new UpComingFilmAdapter(mOnFilmInfo,
						mMainActivity);
				mListView.setAdapter(upComingFilmAdapter);
				upComingFilmAdapter.notifyDataSetChanged();
			}
			return false;
		}
	});

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("action", "movie_on");
			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp + "");
			SharedPreferences s = mMainActivity.getSharedPreferences("city",
					Context.MODE_PRIVATE);
			String cityId = s.getString("cityId", null);
			params.put("city_id", cityId);
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "city_id=" + cityId + "&"
					+ "enc=" + enc + "&" + "time_stamp=" + time_stamp);
			System.out.println(Constant.baseURL + "action="
					+ params.get("action") + "&" + "city_id=" + cityId + "&"
					+ "enc=" + enc + "&" + "time_stamp=" + time_stamp);
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
					mFilmInfo.clear();
					for (int i = 0; i < movies.size(); i++) {
						FilmInfo film = new FilmInfo();
						String movieName = "";
						String movieScore = "";
						Boolean has2D;
						Boolean has3D;
						Boolean hasImax;
						String movieProperty;
						String actionTime = "无数据";
						String posterPath;
						if (!(movies.get(i).getMovieName().equals(null))) {
							movieName = movies.get(i).getMovieName();
							film.setFilmName(movieName);
						}
						if (!(movies.get(i).getScore().equals(null))) {
							movieScore = movies.get(i).getScore();

						} else {
							movieScore = "无评分";
						}
						film.setScore(movieScore);
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
						film.setiMax(movieProperty);
						if (!(movies.get(i).getPublishTime().equals(null))) {
							actionTime = movies.get(i).getPublishTime();
						}
						film.setActionTime(actionTime);
						posterPath = movies.get(i).getPosterPath();
						if (!posterPath.equals(null)) {
							Bitmap filmImage = Util.getBitmap(posterPath);
							film.setImgId(filmImage);
						}

						mFilmInfo.add(film);
					}
					Message msg = new Message();
					// Bundle data = new Bundle();
					// msg.setData(data);
					msg.what = 0;
					handler.sendMessage(msg);
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	};

	Runnable onReleasingRunnable = new Runnable() {

		@Override
		public void run() {
			HashMap<String, Object> params = new HashMap<String, Object>();
			// 动作
			params.put("action", "movie_coming");
			// 时间戳参数
			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp + "");
			// 数量参数
			int count = 12;
			params.put("count", count);
			// 开始位置，默认为0
			int startTime = 0;
			params.put("startTime", startTime);
			// 获得enc参数
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "count=" + count + "&"
					+ "startTime=" + startTime + "&" + "enc=" + enc + "&"
					+ "time_stamp=" + time_stamp);
			System.out.println(Constant.baseURL + "action="
					+ params.get("action") + "&" + "count=" + count + "&"
					+ "startTime=" + startTime + "&" + "enc=" + enc + "&"
					+ "time_stamp=" + time_stamp);
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					// System.out.println("即将上映++++++++" + result);
					Gson gson = new Gson();
					MovieComingResult movieComingResult = gson.fromJson(result,
							MovieComingResult.class);
					List<MovieComing> movies = movieComingResult.getMovies();
					mOnFilmInfo.clear();
					for (int i = 0; i < movies.size(); i++) {
						FilmInfo film = new FilmInfo();
						String movieName = "";
						String movieScore = "";
						Boolean has2D;
						Boolean has3D;
						Boolean hasImax;
						String movieProperty;
						String actionTime = "无数据";
						String posterPath;

						if (!(movies.get(i).getMovieName().equals(null))) {
							movieName = movies.get(i).getMovieName();
							film.setFilmName(movieName);
						}
						if (!(movies.get(i).getScore().equals(null))) {
							movieScore = movies.get(i).getScore();

						} else {
							movieScore = "无评分";
						}
						film.setScore(movieScore);
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
						film.setiMax(movieProperty);
						if (!(movies.get(i).getPublishTime().equals(null))) {
							actionTime = movies.get(i).getPublishTime();
						}
						film.setActionTime(actionTime);
						posterPath = movies.get(i).getPathSquare();
						if (posterPath != null) {
							Bitmap filmImage = Util.getBitmap(posterPath);
							film.setImgId(filmImage);
						}
						mOnFilmInfo.add(film);
						// properties.add(movieProperty);
						// names.add(movieName);
						// scores.add(movieScore);
					}
					Message msg = new Message();
					msg.what = 1;
					// Bundle data = new Bundle();
					// msg.setData(data);
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
		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_FILM;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

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
