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
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

import com.wiseweb.activity.CityListActivity;
import com.wiseweb.activity.FilmDetailsActivity;
import com.wiseweb.activity.FilmSearchActivity;
import com.wiseweb.activity.MainActivity;
import com.wiseweb.bean.FilmInfo;
import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.CityListAdapter;
import com.wiseweb.fragment.adapter.FilmAdapter;
import com.wiseweb.fragment.adapter.UpComingFilmAdapter;
import com.wiseweb.movie.R;
import com.wiseweb.util.GetEnc;

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
		city.setText(cityPreferences.getString("city", null));

		mListView = (ListView) filmLayout.findViewById(R.id.listview_film);
		mFilmAdapter = new FilmAdapter(mFilmInfo, mMainActivity);
		mListView.setAdapter(mFilmAdapter);
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
				// TODO Auto-generated method stub
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
					mFilmAdapter = new FilmAdapter(mFilmInfo, mMainActivity);
					mListView.setAdapter(mFilmAdapter);

				}
			}

		});

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
		//

		mFilmInfo.add(new FilmInfo("奔跑吧，兄弟", "超级喜剧", "导演", "邓超，李晨", "大陆",
				"88分钟", "超级好看的电影", "很好看的电影啊", "2015-01-30", R.drawable.runman,
				"6.8分", "iMax3D", true, "今天127家影院上映102场"));

		mFilmInfo.add(new FilmInfo("奔跑吧，兄弟", "超级喜剧", "导演", "邓超，李晨", "大陆",
				"88分钟", "超级好看的电影", "'很好看的电影啊", "2015-01-30", R.drawable.runman,
				"6.8分", "3D", true, "今天127家影院上映102场"));

		mFilmInfo.add(new FilmInfo("奔跑吧，兄弟", "超级喜剧", "导演", "邓超，李晨", "大陆",
				"88分钟", "超级好看的电影", "很好看的电影啊", "2015-01-30", R.drawable.runman,
				"6.8分", "2D", true, "今天127家影院上映102场"));

		mOnFilmInfo.add(new FilmInfo("RUNNING", "超级喜剧", "导演", "邓超，李晨", "大陆",
				"88分钟", "超级好看的电影", "很好看的电影啊", "2015-01-30", R.drawable.runman,
				"6.8分", "iMax3D", true, "今天127家影院上映102场"));
		mOnFilmInfo.add(new FilmInfo("RUNNING", "超级喜剧", "导演", "邓超，李晨", "大陆",
				"88分钟", "超级好看的电影", "很好看的电影啊", "2015-01-30", R.drawable.runman,
				"6.8分", "iMax3D", true, "今天127家影院上映102场"));
		mOnFilmInfo.add(new FilmInfo("RUNNING", "超级喜剧", "导演", "邓超，李晨", "大陆",
				"88分钟", "超级好看的电影", "很好看的电影啊", "2015-01-30", R.drawable.runman,
				"6.8分", "", false, "今天127家影院上映102场"));
		// getData();
		new Thread(runnable).start();
	}
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String val = data.getString("result");
			System.out.println("请求结果-->" + val);
		}
		
	};
	Runnable runnable = new Runnable(){

		@Override
		public void run() {
			String baseURL = "http://192.168.0.141:4000/appAPI";
			HashMap<String,String> params = new HashMap<String,String>();
			params.put("action", "movie_Query");
			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp+"");
			SharedPreferences s = mMainActivity.getSharedPreferences("city",Context.MODE_PRIVATE);
			String cityId = s.getString("cityId", null);
			params.put("city_id", cityId);
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(baseURL + "?" + "action="
					+ params.get("action") + "&" + "city_id=" + cityId +"&" + "enc=" + enc + "&"
					+ "time_stamp=" + time_stamp);
			HttpResponse httpResponse;
			String result;
			try{
				httpResponse = httpClient.execute(getMethod);
				if(httpResponse.getStatusLine().getStatusCode() == 200){
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					Message msg = new Message();
					Bundle data = new Bundle();
					data.putString("result", result);
					msg.setData(data);
					handler.sendMessage(msg);
				}
			}catch(ClientProtocolException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
	};
	// 获取数据
	public String getData() {
		String city = "";
		String baseURL = "";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("city", city);
		params.put("releasing", "正在上映");
		String enc = GetEnc.getEnc(params, "key");
		HttpClient httpClient = new DefaultHttpClient();
		Date date = new Date();
		long time_stamp = date.getTime();
		// String param=URLEncodedUtils.format(p, "utf-8");
		String param = "";
		// 将URL与参数拼接
		HttpGet getMethod = new HttpGet(baseURL + "?" + param);
		HttpResponse httpResponse;
		String response = "";
		try {
			httpResponse = httpClient.execute(getMethod);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				response = EntityUtils.toString(entity, "utf-8");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;

	}

	// 解析数据

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_FILM;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();

	}
}
