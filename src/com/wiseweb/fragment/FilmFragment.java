package com.wiseweb.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.wiseweb.fragment.adapter.FilmAdapter;
import com.wiseweb.fragment.adapter.UpComingFilmAdapter;
import com.wiseweb.movie.R;

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
		releasing = (RadioButton) filmLayout.findViewById(R.id.releasingFilm);   //
		onReleasing = (RadioButton) filmLayout             //
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

				startActivity(intent);
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
//				Toast.makeText(mMainActivity,
//						mFilmInfo.get(position).toString(), Toast.LENGTH_SHORT)
//						.show();
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
					upComingFilmAdapter = new UpComingFilmAdapter(mOnFilmInfo, mMainActivity);
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
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		mFilmInfo.add(new FilmInfo("奔跑吧，兄弟", "超级喜剧", "导演", "邓超，李晨",
				"大陆", "88分钟", "超级好看的电影", "很好看的电影啊", "2015-01-30",
				R.drawable.runman, "6.8分", "iMax3D", true, "今天127家影院上映102场"));

		mFilmInfo.add(new FilmInfo("奔跑吧，兄弟", "超级喜剧", "导演", "邓超，李晨",
				"大陆", "88分钟", "超级好看的电影", "'很好看的电影啊", "2015-01-30",
				R.drawable.runman, "6.8分", "3D", true, "今天127家影院上映102场"));

		mFilmInfo.add(new FilmInfo("奔跑吧，兄弟", "超级喜剧", "导演", "邓超，李晨",
				"大陆", "88分钟", "超级好看的电影", "很好看的电影啊", "2015-01-30",
				R.drawable.runman, "6.8分", "2D", true, "今天127家影院上映102场"));
		
		//������ӳ
		mOnFilmInfo.add(new FilmInfo("RUNNING", "超级喜剧", "导演", "邓超，李晨",
				"大陆", "88分钟", "超级好看的电影", "很好看的电影啊", "2015-01-30",
				R.drawable.runman, "6.8分", "iMax3D", true, "今天127家影院上映102场"));
		mOnFilmInfo.add(new FilmInfo("RUNNING", "超级喜剧", "导演", "邓超，李晨",
				"大陆", "88分钟", "超级好看的电影", "很好看的电影啊", "2015-01-30",
				R.drawable.runman, "6.8分", "iMax3D", true, "今天127家影院上映102场"));
		mOnFilmInfo.add(new FilmInfo("RUNNING", "超级喜剧", "导演", "邓超，李晨",
				"大陆", "88分钟", "超级好看的电影", "很好看的电影啊", "2015-01-30",
				R.drawable.runman, "6.8分", "", false, "今天127家影院上映102场"));
	}

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
