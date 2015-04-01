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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wiseweb.activity.CinemaSelectFilmActivity;
import com.wiseweb.activity.CinemaSearchActivity;
import com.wiseweb.activity.CityListActivity;
import com.wiseweb.activity.MainActivity;
import com.wiseweb.bean.CinemaInfo;
import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.CinemaAdapter;
import com.wiseweb.movie.R;

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
	//private RadioButton cinemaAll;
	private RadioButton selectSeat;
	private RadioButton groupPurchase;
	private View cinemaArea;
	private ImageView cinemaSearch;

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
		cityPreferences = mMainActivity.getSharedPreferences("city", Context.MODE_PRIVATE);
		cityTv = (TextView)cinemaLayout.findViewById(R.id.city_name);
		cityTv.setText(cityPreferences.getString("city", null));
		//cinemaAll = (RadioButton)cinemaLayout.findViewById(R.id.cinema_all);
		selectSeat = (RadioButton)cinemaLayout.findViewById(R.id.cinema_select_seat);
		groupPurchase = (RadioButton)cinemaLayout.findViewById(R.id.cinema_group_purchase);
		cinemaRadio = (RadioGroup)cinemaLayout.findViewById(R.id.cinema_radio);
		cinemaArea = (View)cinemaLayout.findViewById(R.id.cinema_area);
		cinemaArea.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass((MainActivity) getActivity(), CityListActivity.class);
				//Bundle mBundle = new Bundle();
				//mBundle.putString("className", "cinemaFragment");
				//intent.putExtra("flag", "cinemaFragment");
				startActivity(intent);
			}
			
		});
		cinemaSearch = (ImageView)cinemaLayout.findViewById(R.id.cinema_search);
		cinemaSearch.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(mMainActivity, CinemaSearchActivity.class);
				startActivity(intent);
			}
			
		});
		cinemaRadio.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
				// TODO Auto-generated method stub
				
				if(checkedId ==selectSeat.getId()){
					cinemaInfo_seat.clear();
					for(int i=0;i<cinemaInfo.size();i++){
						if(cinemaInfo.get(i).isSeat()==true){
							cinemaInfo_seat.add(cinemaInfo.get(i));
						}
					}
					cinemaAdapter=new CinemaAdapter(cinemaInfo_seat,mMainActivity);
					cinemaList.setAdapter(cinemaAdapter);
				}
				else if(checkedId == groupPurchase.getId()){
					cinemaInfo_group.clear();
					for(int i=0; i<cinemaInfo.size(); i++){
						if(cinemaInfo.get(i).isGroupPurchase() == true){
							cinemaInfo_group.add(cinemaInfo.get(i));
						}
					}
					cinemaAdapter = new CinemaAdapter(cinemaInfo_group, mMainActivity);
					cinemaList.setAdapter(cinemaAdapter);
				}else{
					cinemaAdapter = new CinemaAdapter(cinemaInfo,mMainActivity);
					cinemaList.setAdapter(cinemaAdapter);
				}
			}
			
		});
		cinemaList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				Toast.makeText(mMainActivity,
//						cinemaInfo.get(position).toString(), Toast.LENGTH_SHORT)
//						.show();
				Intent intent = new Intent();
				intent.setClass(mMainActivity, CinemaSelectFilmActivity.class);
				startActivity(intent);
			}

		});
		return cinemaLayout;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//����ݳ�ʼ��
		cinemaInfo.add(new CinemaInfo("UME国际影城", true, true, false, false,
				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));

		cinemaInfo.add(new CinemaInfo("UME国际影城", true, false, false, false,
				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));

		cinemaInfo.add(new CinemaInfo("UME国际影城", false, true, false, false, 19.9,
				"海淀区双榆树科学院南路44号", "1.5KM"));

		cinemaInfo.add(new CinemaInfo("UME国际影城", false, false, true, false,
				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));

		cinemaInfo.add(new CinemaInfo("UME国际影城", true, false, true, true, 19.9,
				"海淀区双榆树科学院南路44号", "1.5KM"));

		cinemaInfo.add(new CinemaInfo("UME国际影城", false, false, true, false,
				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));

		cinemaInfo.add(new CinemaInfo("UME国际影城", true, false, true, false,
				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));
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

		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_CINEMA;
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

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	// }

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
