package com.wiseweb.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;

import com.viewpagerindicator.TabPageIndicator;
import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.TabPageIndicatorAdapter;
import com.wiseweb.movie.R;

public class BuyTicketSelectCinema extends FragmentActivity {
	private TabPageIndicator indicator;
	private ViewPager pager;
	private FragmentPagerAdapter adapter;
	private ListView listview;
	private View buyTicketBack;

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

		// 
		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				Toast.makeText(getApplicationContext(), Constant.CONTENT[arg0],
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;

	}

	private void initUI() {
		//indicator = (TabPageIndicator) findViewById(R.id.page_indicator);
		//pager = (ViewPager) findViewById(R.id.view_pager);
		buyTicketBack = (View)findViewById(R.id.buy_ticket_back);
		buyTicketBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				finish();
			}
			
		});
	}
		
}