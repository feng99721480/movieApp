package com.wiseweb.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;

import com.viewpagerindicator.TabPageIndicator;
import com.wiseweb.bean.CinemaInfo;
import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.BuyTicketSelectCinemaListAdapter;
import com.wiseweb.fragment.adapter.TabPageIndicatorAdapter;
import com.wiseweb.movie.R;

public class BuyTicketSelectCinema extends FragmentActivity {
	private TabPageIndicator indicator;
	private ViewPager pager;
	private FragmentPagerAdapter adapter;
	private ListView listview;
	private View buyTicketBack;
	private RadioGroup radio;// 选择影院的类型
	private RadioButton selectSeat;
	private RadioButton groupPurchase;
	private RadioButton all;
	private List<CinemaInfo> cinemaInfo = new ArrayList<CinemaInfo>();
	private List<CinemaInfo> cinemaInfo_seat = new ArrayList<CinemaInfo>();
	private List<CinemaInfo> cinemaInfo_group = new ArrayList<CinemaInfo>();
	private BuyTicketSelectCinemaListAdapter cinemaAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_buy_ticket_select_cinema);
		ShareSDK.initSDK(this);
		initUI();
		indicator = (TabPageIndicator) findViewById(R.id.page_indicator);

		pager = (ViewPager) findViewById(R.id.view_pager);
		//
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
				if(radio.getCheckedRadioButtonId() == R.id.cinema_all){
					cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
							cinemaInfo, BuyTicketSelectCinema.this);
					listview.setAdapter(cinemaAdapter);
				}
				else if(radio.getCheckedRadioButtonId() == R.id.cinema_select_seat){
					cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
							cinemaInfo_seat, BuyTicketSelectCinema.this);
					listview.setAdapter(cinemaAdapter);
				}
				else if(radio.getCheckedRadioButtonId() == R.id.cinema_group_purchase){
					cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
							cinemaInfo_group, BuyTicketSelectCinema.this);
					listview.setAdapter(cinemaAdapter);
				}
				String name = adapter.getPageTitle(position).toString();
				System.out.println("titlename-------" + name);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		Time t = new Time("GMT+8");
		t.setToNow();
		int month = t.month+1;
		int day = t.monthDay;
		
	}

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
		listview = (ListView) findViewById(R.id.buy_ticket_select_cinema_list);
		radio = (RadioGroup) findViewById(R.id.cinema_radio);
		selectSeat = (RadioButton) findViewById(R.id.cinema_select_seat);
		all = (RadioButton) findViewById(R.id.cinema_all);
		groupPurchase = (RadioButton) findViewById(R.id.cinema_group_purchase);
		radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == selectSeat.getId()) {
					cinemaInfo_seat.clear();
					for (int i = 0; i < cinemaInfo.size(); i++) {
						if (cinemaInfo.get(i).isSeat() == true) {
							cinemaInfo_seat.add(cinemaInfo.get(i));
						}
					}
					cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
							cinemaInfo_seat, BuyTicketSelectCinema.this);
					listview.setAdapter(cinemaAdapter);
				} else if (checkedId == groupPurchase.getId()) {
					cinemaInfo_group.clear();
					for (int i = 0; i < cinemaInfo.size(); i++) {
						if (cinemaInfo.get(i).isGroupPurchase() == true) {
							cinemaInfo_group.add(cinemaInfo.get(i));
						}
					}
					cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
							cinemaInfo_group, BuyTicketSelectCinema.this);
					listview.setAdapter(cinemaAdapter);
				} else {
					cinemaAdapter = new BuyTicketSelectCinemaListAdapter(
							cinemaInfo, BuyTicketSelectCinema.this);
					listview.setAdapter(cinemaAdapter);
				}
			}

		});
	}
}