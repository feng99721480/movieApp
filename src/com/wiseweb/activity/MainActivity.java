package com.wiseweb.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.Window;
import android.widget.Toast;

import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.BaseFragment;
import com.wiseweb.movie.R;
import com.wiseweb.service.LocationService;
import com.wiseweb.ui.BottomControlPanel;
import com.wiseweb.ui.BottomControlPanel.BottomPanelCallback;
import com.wiseweb.ui.ImageText;

public class MainActivity extends Activity implements BottomPanelCallback {
	BottomControlPanel bottomPanel = null;
	// HeadControlPanel headPanel = null;

	private FragmentManager fragmentManager = null;
	private FragmentTransaction fragmentTransaction = null;
	private SharedPreferences mflag;
	private ImageText cinema;

	public static String currFragTag = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initUI();
		
		
		cinema = (ImageText)findViewById(R.id.btn_cinema);
		fragmentManager = getFragmentManager();
		//mflag = getSharedPreferences("fragmentFlag", Context.MODE_PRIVATE);
		setDefaultFirstFragment(Constant.FRAGMENT_FLAG_FILM);
		/*if (mflag.getString("flag", null) == null) {
			setDefaultFirstFragment(Constant.FRAGMENT_FLAG_FILM);
		} else {
			if (mflag.getString("flag", null).equals("filmFragment")) {
				setDefaultFirstFragment(Constant.FRAGMENT_FLAG_FILM);
			} else if (mflag.getString("flag", null).equals("cinemaFragment")) {
				setDefaultFirstFragment(Constant.FRAGMENT_FLAG_CINEMA);
				cinema.setChecked(R.id.btn_cinema);
			}
		}*/
	}

	/*@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mflag = getSharedPreferences("fragmentflag", Context.MODE_PRIVATE);
		System.out.println("aaaaaaaaa"+mflag.getString("flag",null));
		if (mflag.getString("flag", null) != null) {
			SharedPreferences.Editor editor = mflag.edit();
			editor.putString("flag", null);
			editor.commit();
		}
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initUI() {
		bottomPanel = (BottomControlPanel) findViewById(R.id.bottom_layout);
		if (bottomPanel != null) {
			bottomPanel.initBottomPanel();
			bottomPanel.setBottomCallback(this);
		}
		/*
		 * headPanel = (HeadControlPanel)findViewById(R.id.head_layout);
		 * if(headPanel != null){ headPanel.initHeadPanel(); }
		 */
	}

	/*
	 * 
	 */
	@Override
	public void onBottomPanelClick(int itemId) {
		// TODO Auto-generated method stub
		String tag = "";
		if ((itemId & Constant.BTN_FLAG_FILM) != 0) {
			tag = Constant.FRAGMENT_FLAG_FILM;
		} else if ((itemId & Constant.BTN_FLAG_CINEMA) != 0) {
			tag = Constant.FRAGMENT_FLAG_CINEMA;
		} else if ((itemId & Constant.BTN_FLAG_COMMUNITY) != 0) {
			tag = Constant.FRAGMENT_FLAG_COMMUNITY;
		} else if ((itemId & Constant.BTN_FLAG_MINE) != 0) {
			tag = Constant.FRAGMENT_FLAG_MINE;
		}
		setTabSelection(tag); // 
		// 
		// headPanel.setMiddleTitle(tag);//
	}

	private void setDefaultFirstFragment(String tag) {
		// Log.i("wiseweb", "setDefaultFirstFragment enter... currFragTag = " +
		// currFragTag);
		setTabSelection(tag);
		bottomPanel.defaultBtnChecked();
		// Log.i("wiseweb", "setDefaultFirstFragment exit...");
	}

	private void commitTransactions(String tag) {
		if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
			fragmentTransaction.commit();
			currFragTag = tag;
			fragmentTransaction = null;
		}
	}

	private FragmentTransaction ensureTransaction() {
		if (fragmentTransaction == null) {
			fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

		}
		return fragmentTransaction;

	}

	private void attachFragment(int layout, Fragment f, String tag) {
		if (f != null) {
			if (f.isDetached()) {
				ensureTransaction();
				fragmentTransaction.attach(f);

			} else if (!f.isAdded()) {
				ensureTransaction();
				fragmentTransaction.add(layout, f, tag);
			}
		}
	}

	private Fragment getFragment(String tag) {

		Fragment f = fragmentManager.findFragmentByTag(tag);

		if (f == null) {
			Toast.makeText(getApplicationContext(),
					"fragment = null tag = " + tag, Toast.LENGTH_SHORT).show();
			f = BaseFragment.newInstance(getApplicationContext(), tag);
		}
		return f;

	}

	private void detachFragment(Fragment f) {

		if (f != null && !f.isDetached()) {
			ensureTransaction();
			fragmentTransaction.detach(f);
		}
	}

	/**
	 * �л�fragment
	 * 
	 * @param tag
	 */
	private void switchFragment(String tag) {
		if (TextUtils.equals(tag, currFragTag)) {
			return;
		}
		// ����һ��fragment detach��
		if (currFragTag != null && !currFragTag.equals("")) {
			detachFragment(getFragment(currFragTag));
		}
		attachFragment(R.id.fragment_content, getFragment(tag), tag);
		commitTransactions(tag);
	}

	/**
	 * ����ѡ�е�Tag
	 * 
	 * @param tag
	 */
	public void setTabSelection(String tag) {
		//
		fragmentTransaction = fragmentManager.beginTransaction();
		/*
		 * if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_MESSAGE)){ if
		 * (messageFragment == null) { messageFragment = new MessageFragment();
		 * }
		 * 
		 * }else if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_CONTACTS)){ if
		 * (contactsFragment == null) { contactsFragment = new
		 * ContactsFragment(); }
		 * 
		 * }else if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_NEWS)){ if
		 * (newsFragment == null) { newsFragment = new NewsFragment(); }
		 * 
		 * }else if(TextUtils.equals(tag,Constant.FRAGMENT_FLAG_SETTING)){ if
		 * (settingFragment == null) { settingFragment = new SettingFragment();
		 * } }else if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_SIMPLE)){ if
		 * (simpleFragment == null) { simpleFragment = new SimpleFragment(); }
		 * 
		 * }
		 */
		switchFragment(tag);

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		currFragTag = "";
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
	}

}
