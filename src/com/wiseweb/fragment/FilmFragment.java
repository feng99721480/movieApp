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
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.wiseweb.ui.AutoListView;
import com.wiseweb.ui.AutoListView.OnLoadListener;
import com.wiseweb.ui.AutoListView.OnRefreshListener;
import com.wiseweb.util.DatabaseHelper;
import com.wiseweb.util.GetEnc;
import com.wiseweb.util.Util;

public class FilmFragment extends BaseFragment implements OnRefreshListener,
		OnLoadListener {
	private static final int MOVIE_ON = 0;
	private static final int MOVIE_COMING = 1;
	private MainActivity mMainActivity;
	// private ListView mListView;
	private AutoListView mListView;
	private FilmAdapter mFilmAdapter;
	private UpComingFilmAdapter upComingFilmAdapter;
	private List<FilmInfo> mFilmInfo = new ArrayList<FilmInfo>();
	private List<FilmInfo> mComeFilmInfo = new ArrayList<FilmInfo>();
	private RadioGroup radioGroup;
	private RadioButton releasing;
	private RadioButton onReleasing;
	private View area;
	private TextView city;
	private SharedPreferences cityPreferences;
	private ImageView filmSearch;
	private Button buyTicket;
	private BitmapDrawable bd;
	private List<Long> movieIdsOn;
	private List<Long> movieIdsCome;
	private SharedPreferences movieConfigure;
	private DatabaseHelper db;
	private SQLiteDatabase dbRead, dbWrite;
	private Cursor cursor;
	private int count = 10;
	private int start = 0;
	private String cityId;
	private String cityName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View filmLayout = inflater.inflate(R.layout.film_layout, container,
				false);

		mMainActivity = (MainActivity) getActivity();
		mFragmentManager = getActivity().getFragmentManager();
		// 建表
		db = new DatabaseHelper(mMainActivity);
		dbRead = db.getReadableDatabase();
		dbWrite = db.getWritableDatabase();

		city = (TextView) filmLayout.findViewById(R.id.city_title);
		cityPreferences = mMainActivity.getSharedPreferences("city",
				Context.MODE_PRIVATE);
		cityId = cityPreferences.getString("cityId", null);
		cityName = cityPreferences.getString("city", null);
		city.setText(cityPreferences.getString("city", null));

		// mListView = (ListView)
		// filmLayout.findViewById(R.id.listview_film);
		mListView = (AutoListView) filmLayout
				.findViewById(R.id.listview_film);
		mListView.setOnRefreshListener(this);
		mListView.setOnLoadListener(this);

		radioGroup = (RadioGroup) filmLayout
				.findViewById(R.id.film_title_group);
		releasing = (RadioButton) filmLayout
				.findViewById(R.id.releasingFilm); // 正在上映
		onReleasing = (RadioButton) filmLayout
				.findViewById(R.id.onreleasingFilm);// 即将上映
//		initListView();
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
				movieConfigure = getActivity().getSharedPreferences(
						"movieConfigure", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = movieConfigure.edit();
				String movieName;
				// 看是从正在上映还是从即将上映跳过去的
				if (radioGroup.getCheckedRadioButtonId() == releasing
						.getId()) {

					movieName = mFilmInfo.get(position - 1).getFilmName(); // 在这里使用autoListView,点击第一条的时候，position的值为1
					editor.putString("movieName", movieName);
					editor.putLong("movieId", movieIdsOn.get(position - 1)); // 电影id
					editor.putBoolean("movieOnCome", true); // 正在上映还是即将上映电影标识
				} else {
					movieName = mComeFilmInfo.get(position - 1)
							.getFilmName();
					editor.putString("movieName", movieName);
					editor.putLong("movieId",
							movieIdsCome.get(position - 1));
					editor.putBoolean("movieOnCome", false);
				}
				editor.commit();

				intent.setClass(mMainActivity, FilmDetailsActivity.class);
				startActivity(intent);
			}

		});
		radioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup radioGroup,
							int checkedId) {
						// 即将上映
						if (checkedId == onReleasing.getId()) {
							// Thread onReleasing = new
							// Thread(onReleasingRunnable);
							// onReleasing.start();
							// try {
							// onReleasing.join();
							// } catch (InterruptedException e) {
							// e.printStackTrace();
							// }
							// onLoad();
							initListView();

						} else if (checkedId == releasing.getId()) {
							// mFilmInfo.clear();
							if (mFilmInfo != null) {
								mFilmAdapter = new FilmAdapter(mFilmInfo,
										mMainActivity);
								mListView.setAdapter(mFilmAdapter);

							} else {
								// Thread releasing = new Thread(runnable);
								// releasing.start();
								// try {
								// releasing.join();
								// } catch (InterruptedException e) {
								// e.printStackTrace();
								// }
								initListView();
								// mFilmAdapter = new FilmAdapter(mFilmInfo,
								// mMainActivity);
								// mListView.setAdapter(mFilmAdapter);

							}
							// mFilmAdapter.notifyDataSetChanged();

						}
					}

				});

		

		if (cityId == null || cityName == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(mMainActivity);
			builder.setTitle("选择城市");
			builder.setMessage("请先选择城市");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(mMainActivity,
									CityListActivity.class);
							startActivityForResult(intent, 1);
						}
					});
			builder.show();
			// Intent intent = new Intent();
			// intent.setClass(mMainActivity, CityListActivity.class);
			// startActivityForResult(intent, 1);
		} else {
			initListView();
		}
		return filmLayout;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 从cityList返回的结果
		if (2 == resultCode) {
			String cityName = data.getExtras().getString("city");
			cityId = data.getExtras().getString("cityId");
			city.setText(cityName);
			// 切换城市时，相应的数据也得刷新
			// Thread filmOnThread = new Thread(runnable);
			// filmOnThread.start();
			// try {
			// filmOnThread.join();
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			initListView();
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
			case MOVIE_ON:
				mListView.onRefreshComplete();
				mListView.onLoadComplete();

				// mFilmAdapter = new FilmAdapter(mFilmInfo, mMainActivity);
				// mListView.setAdapter(mFilmAdapter);
				initListView();

				mFilmAdapter.notifyDataSetChanged();
				Bundle data = msg.getData();
				mListView.setResultSize(data.getInt("newMovieOn"));
				//
				break;
			case MOVIE_COMING:
				mListView.onRefreshComplete();
				mListView.onLoadComplete();
				upComingFilmAdapter = new UpComingFilmAdapter(mComeFilmInfo,
						mMainActivity);
				// mListView.setAdapter(upComingFilmAdapter);
				// upComingFilmAdapter.notifyDataSetChanged();
				initListView();

				Bundle b = msg.getData();
				mListView.setResultSize(b.getInt("newCount"));
				break;
			}
			return false;
		}
	});
	/**
	 * 数据请求 正在上映电影的电影信息
	 */

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			movieIdsOn = new ArrayList<Long>();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("action", "movie_on");

			long time_stamp = Util.getTimeStamp();
			params.put("time_stamp", time_stamp + "");
			SharedPreferences s = mMainActivity.getSharedPreferences("city",
					Context.MODE_PRIVATE);
			cityId = s.getString("cityId", null);
			params.put("city_id", cityId);
			params.put("count", count);

			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "cityId=" + cityId + "&"
					+ "&" + "enc=" + enc + "&" + "time_stamp=" + time_stamp);
			System.out.println(Constant.baseURL + "action="
					+ params.get("action") + "&" + "cityId=" + cityId + "&"
					+ "&" + "enc=" + enc + "&" + "time_stamp=" + time_stamp);
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
						long movieId;
						String movieScore = "";
						Boolean has2D;
						Boolean has3D;
						Boolean hasImax;
						String movieProperty;
						String actionTime = "无数据";
						String posterPath = null;
						String pathVerticalS = null;
						String pathSquare = null;
						String pathHorizonB = null;
						if (!(movies.get(i).getMovieName() == null)) {
							movieName = movies.get(i).getMovieName();
							film.setFilmName(movieName);
						}
						movieId = movies.get(i).getMovieId();
						if (!(movies.get(i).getScore() == null)) {
							movieScore = movies.get(i).getScore();

						} else {
							movieScore = "无评分";
						}
						film.setScore(movieScore);
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
						film.setiMax(movieProperty);
						if (!(movies.get(i).getPublishTime() == null)) {
							actionTime = movies.get(i).getPublishTime();
						}
						film.setActionTime(actionTime);
						// posterPath = movies.get(i).getPosterPath();
						byte[] image = null;
						if (!(movies.get(i).getPosterPath() == null)) {
							posterPath = movies.get(i).getPosterPath();
							Bitmap filmImage = Util.getBitmap(posterPath);
							film.setImgId(filmImage);
							image = Util.imgToByteArray(filmImage);
						} else if (!(movies.get(i).getPathVerticalS() == null)) {
							pathVerticalS = movies.get(i).getPathVerticalS();
							Bitmap filmImage = Util.getBitmap(pathVerticalS);
							film.setImgId(filmImage);
							image = Util.imgToByteArray(filmImage);
						} else if (!(movies.get(i).getPathSquare() == null)) {
							pathSquare = movies.get(i).getPathSquare();
							Bitmap filmImage = Util.getBitmap(pathSquare);
							film.setImgId(filmImage);
							image = Util.imgToByteArray(filmImage);
						} else if (!(movies.get(i).getPathHorizonB() == null)) {
							pathHorizonB = movies.get(i).getPathHorizonB();
							Bitmap filmImage = Util.getBitmap(pathHorizonB);
							film.setImgId(filmImage);
							image = Util.imgToByteArray(filmImage);
						}

						// movieIdsOn.add(movieId);
						// mFilmInfo.add(film);

						// 存库
						ContentValues cv = new ContentValues();
						cv.put("movieName", movieName);
						cv.put("movieId", movieId);
						cv.put("movieScore", movieScore);
						cv.put("has2D", has2D);
						cv.put("has3D", has3D);
						cv.put("hasImax", hasImax);
						cv.put("movieProperty", movieProperty);
						cv.put("publishTime", actionTime);
						cv.put("movieLength", movies.get(i).getMovieLength()); // 时长
						cv.put("movieType", movies.get(i).getMovieType());
						cv.put("actor", movies.get(i).getActor());
						cv.put("country", movies.get(i).getCountry());
						cv.put("director", movies.get(i).getDirector()); // 导演
						// cv.put("posterPath", posterPath);
						// cv.put("pathVerticalS", pathVerticalS);
						// cv.put("pathVerticalS", pathVerticalS);
						// cv.put("pathSquare", pathSquare);
						// cv.put("pathHorizonB", pathHorizonB);
						cv.put("movieImg", image);
						cv.put("cityId", cityId);
						cursor = dbRead.query("movieOnInfo", new String[] {
								"movieId", "cityId" },
								"movieId=? and cityId=?", new String[] {
										movieId + "", cityId }, null, null,
								null);
						if (cursor.moveToFirst() == false) { // 无此数据 插入之
							dbWrite.insert("movieOnInfo", null, cv);
						} else { // 有数据更新之
							dbWrite.update("movieOnInfo", cv,
									"movieId=? and cityId=?", new String[] {
											movieId + "", cityId });
						}
					}
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putInt("newMovieOn", movies.size());
					msg.setData(data);
					msg.what = MOVIE_ON;
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
			movieIdsCome = new ArrayList<Long>();
			HashMap<String, Object> params = new HashMap<String, Object>();
			// 动作
			params.put("action", "movie_coming");
			// 时间戳参数
			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp + "");
			// 数量参数

			params.put("count", count);
			// 开始位置，默认为0
			// int startTime = 0;

			params.put("startTime", start);
			// 获得enc参数
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "count=" + count + "&"
					+ "startTime=" + start + "&" + "enc=" + enc + "&"
					+ "time_stamp=" + time_stamp);
			System.out.println(Constant.baseURL + "action="
					+ params.get("action") + "&" + "count=" + count + "&"
					+ "startTime=" + start + "&" + "enc=" + enc + "&"
					+ "time_stamp=" + time_stamp);
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					Gson gson = new Gson();
					MovieComingResult movieComingResult = gson.fromJson(result,
							MovieComingResult.class);
					List<MovieComing> movies = movieComingResult.getMovies();
					mComeFilmInfo.clear();
					int newCount = movies.size();
					for (int i = 0; i < movies.size(); i++) {
						FilmInfo film = new FilmInfo();
						String movieName = "";
						long movieId;
						String movieScore = "";
						Boolean has2D;
						Boolean has3D;
						Boolean hasImax;
						String movieProperty;
						String actionTime = "无数据";
						String posterPath;
						String pathVerticalS = null;
						String pathSquare = null;
						// String pathHorizonB;
						if (!(movies.get(i).getMovieName().equals(null))) {
							movieName = movies.get(i).getMovieName();
							film.setFilmName(movieName);
						}
						movieId = movies.get(i).getMovieId();
						if (!(movies.get(i).getScore() == null)) {
							movieScore = movies.get(i).getScore();

						} else {
							movieScore = "无评分";
						}
						film.setScore(movieScore);
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
						film.setiMax(movieProperty);
						if (!(movies.get(i).getPublishTime() == null)) {
							actionTime = movies.get(i).getPublishTime();
						}
						film.setActionTime(actionTime);
						byte[] image = null;
						if (movies.get(i).getPathSquare() != null) {
							pathSquare = movies.get(i).getPathSquare();
							Bitmap filmImage = Util.getBitmap(pathSquare);
							film.setImgId(filmImage);
							image = Util.imgToByteArray(filmImage);
						} else if (!(movies.get(i).getPathVerticalS() == null)) {
							pathVerticalS = movies.get(i).getPathVerticalS();
							Bitmap filmImage = Util.getBitmap(pathVerticalS);
							film.setImgId(filmImage);
							image = Util.imgToByteArray(filmImage);
						}

						// else if (!(movies.get(i).getPathHorizonB() == null))
						// {
						// pathHorizonB = movies.get(i).getPathHorizonB();
						// Bitmap filmImage = Util.getBitmap(pathHorizonB);
						// film.setImgId(filmImage);
						// }

						mComeFilmInfo.add(film);
						movieIdsCome.add(movieId);
						// properties.add(movieProperty);
						// names.add(movieName);
						// scores.add(movieScore);

						// 存库
						ContentValues cv = new ContentValues();
						cv.put("movieName", movieName);
						cv.put("movieId", movieId);
						cv.put("score", movieScore);
						cv.put("has2D", has2D);
						cv.put("has3D", has3D);
						cv.put("hasImax", hasImax);
						cv.put("movieProperty", movieProperty);
						cv.put("publishTime", actionTime);
						cv.put("movieLength", movies.get(i).getMovieLength()); // 时长
						cv.put("movieType", movies.get(i).getMovieType());
						cv.put("actor", movies.get(i).getActor());
						cv.put("country", movies.get(i).getCountry());
						cv.put("director", movies.get(i).getDirector()); // 导演
						cv.put("hot", movies.get(i).getHot());
						// cv.put("posterPath", posterPath);
						// cv.put("pathVerticalS", pathVerticalS);
						// cv.put("pathSquare", pathSquare);
						cv.put("movieImg", image);
						// cv.put("pathHorizonB", pathHorizonB);
						cursor = dbRead
								.query("movieComingInfo",
										new String[] { "movieId" },
										"movieId=?", new String[] { movieId
												+ "" }, null, null, null);
						if (cursor.moveToFirst() == false) { // 无此数据 插入之
							dbWrite.insert("movieComingInfo", null, cv);
						} else {
							dbWrite.update("movieComingInfo", cv, "movieId=?",
									new String[] { movieId + "" });
						}
					}
					Message msg = new Message();
					msg.what = MOVIE_COMING;

					Bundle data = new Bundle();
					data.putInt("newCount", newCount);// 最新加载的条数
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

	private void loadData(final int what) {
		switch (what) {
		case AutoListView.LOAD:
			start = start + count;
			count = count + 10;
			if (radioGroup.getCheckedRadioButtonId() == releasing.getId()) {
				mListView.setLoadEnable(false);
			} else if (radioGroup.getCheckedRadioButtonId() == onReleasing
					.getId()) {
				mListView.setLoadEnable(true);
				Thread t = new Thread(onReleasingRunnable);
				t.start();
				// try {
				// t.join();
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
			}
			break;
		case AutoListView.REFRESH:
			// start = 0;
			if (radioGroup.getCheckedRadioButtonId() == releasing.getId()) {
				// Thread t = new Thread(runnable);
				// t.start();
				int count = dbWrite.delete("movieOnInfo", "cityId=?",
						new String[] { cityId });
				initListView();
				// try {
				// t.join();
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
			} else {
				// Thread t = new Thread(onReleasingRunnable);
				// t.start();
				// 删除所有记录
				int count = dbWrite.delete("movieComingInfo", null, null);
				initListView();
				// try {
				// t.join();
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
			}
			break;

		}

	}

	/**
	 * 展示数据
	 */
	private void initListView() {

		if (radioGroup.getCheckedRadioButtonId() == releasing.getId()) {
			movieIdsOn = new ArrayList<Long>();
			movieIdsOn.clear();
			mFilmInfo.clear();
			cursor = dbRead.query("movieOnInfo", null, "cityId=?",
					new String[] { cityId }, null, null, null);
			if (cursor.moveToFirst() == false) { // cursor为空
				Thread t = new Thread(runnable);
				t.start();
			} else {
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {
					FilmInfo film = new FilmInfo();
					Long id = cursor.getLong(cursor.getColumnIndex("movieId"));
					String name = cursor.getString(cursor
							.getColumnIndex("movieName"));
					String property = cursor.getString(cursor
							.getColumnIndex("movieProperty"));
					String score = cursor.getString(cursor
							.getColumnIndex("movieScore"));
					film.setFilmName(name);

					if (score == null) {
						score = "无评分";
					}
					film.setScore(score);

					film.setiMax(property);
					if (cursor.getString(cursor.getColumnIndex("publishTime")) != null) {
						film.setActionTime(cursor.getString(cursor
								.getColumnIndex("publishTime")));
					}
					Bitmap filmImage = null;
					// 取出数据转换成Bitmap
					byte[] in = cursor.getBlob(cursor
							.getColumnIndex("movieImg"));
					if (in != null) {
						filmImage = BitmapFactory.decodeByteArray(in, 0,
								in.length);
						film.setImgId(filmImage);
					}

					// if (cursor.getString(cursor.getColumnIndex("posterPath"))
					// != null) {
					//
					// try {
					// filmImage = Util.getBitmap(cursor.getString(cursor
					// .getColumnIndex("posterPath")));
					// } catch (IOException e) {
					// e.printStackTrace();
					// }
					//
					// film.setImgId(filmImage);
					// } else if (cursor.getString(cursor
					// .getColumnIndex("pathVerticalS")) != null) {
					// try {
					// filmImage = Util.getBitmap(cursor.getString(cursor
					// .getColumnIndex("pathVerticalS")));
					// } catch (IOException e) {
					// e.printStackTrace();
					// }
					// film.setImgId(filmImage);
					// } else if (cursor.getString(cursor
					// .getColumnIndex("pathSquare")) != null) {
					// try {
					// filmImage = Util.getBitmap(cursor.getString(cursor
					// .getColumnIndex("pathSquare")));
					// } catch (IOException e) {
					// e.printStackTrace();
					// }
					// film.setImgId(filmImage);
					// } else if (cursor.getString(cursor
					// .getColumnIndex("pathHorizonB")) != null) {
					// try {
					// filmImage = Util.getBitmap(cursor.getString(cursor
					// .getColumnIndex("pathHorizonB")));
					// } catch (IOException e) {
					// e.printStackTrace();
					// }
					// film.setImgId(filmImage);
					// }
					mFilmInfo.add(film);
					movieIdsOn.add(id);
				} // end for

			}
			mFilmAdapter = new FilmAdapter(mFilmInfo, mMainActivity);
			mListView.setAdapter(mFilmAdapter);
		} else {
			movieIdsCome = new ArrayList<Long>();
			movieIdsCome.clear();
			mComeFilmInfo.clear();
			cursor = dbRead.query("movieComingInfo", null, null, null, null,
					null, null);
			if (cursor.moveToFirst() == false) { // cursor为空
				Thread t = new Thread(onReleasingRunnable);
				t.start();
			} else {
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {
					FilmInfo film = new FilmInfo();
					Long id = cursor.getLong(cursor.getColumnIndex("movieId"));
					String name = cursor.getString(cursor
							.getColumnIndex("movieName"));
					String property = cursor.getString(cursor
							.getColumnIndex("movieProperty"));
					String score = cursor.getString(cursor
							.getColumnIndex("score"));
					film.setFilmName(name);

					if (score == null) {
						score = "无评分";
					}
					film.setScore(score);

					film.setiMax(property);
					if (cursor.getString(cursor.getColumnIndex("publishTime")) != null) {
						film.setActionTime(cursor.getString(cursor
								.getColumnIndex("publishTime")));
					}
					Bitmap filmImage = null;
					// 取出数据转换成Bitmap
					byte[] in = cursor.getBlob(cursor
							.getColumnIndex("movieImg"));
					if (in != null) {
						filmImage = BitmapFactory.decodeByteArray(in, 0,
								in.length);
						film.setImgId(filmImage);
					}
					mComeFilmInfo.add(film);
					movieIdsCome.add(id);
				} // end for

			}
			upComingFilmAdapter = new UpComingFilmAdapter(mComeFilmInfo,
					mMainActivity);
			mListView.setAdapter(upComingFilmAdapter);
		}

	}

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

	@Override
	public void onLoad() {
		loadData(AutoListView.LOAD);
	}

	@Override
	public void onRefresh() {
		loadData(AutoListView.REFRESH);

	}
}
