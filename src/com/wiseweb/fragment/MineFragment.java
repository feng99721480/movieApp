package com.wiseweb.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

import com.wiseweb.activity.LoginActivity;
import com.wiseweb.activity.MainActivity;
import com.wiseweb.activity.MyAccountActivity;
import com.wiseweb.activity.MyCinemaTicketActivity;
import com.wiseweb.activity.MyFilmActivity;
import com.wiseweb.constant.Constant;
import com.wiseweb.movie.R;

public class MineFragment extends BaseFragment {
	private MainActivity mMainActivity;
	private TextView mineName;
	private String nickName;
	private ImageView mineHeader;
	private RelativeLayout myTicket;
	private RelativeLayout mineInfo;
	private LinearLayout myCinemaTicket;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mineLayout = inflater.inflate(R.layout.mine_layout, container,
				false);
		mMainActivity = (MainActivity) getActivity();
		mineName = (TextView) mineLayout.findViewById(R.id.mine_name);
		myCinemaTicket = (LinearLayout) mineLayout
				.findViewById(R.id.my_cinema_ticket_layout);

		SharedPreferences s = mMainActivity.getSharedPreferences("userInfo",
				Context.MODE_PRIVATE);
		nickName = s.getString("nickname", "");
		if (nickName.isEmpty() == false) {
			mineName.setText(nickName);
		} else {
			mineName.setText("立即登录");
		}
		myCinemaTicket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mMainActivity, MyCinemaTicketActivity.class);
				startActivity(intent);
			}
		});
		// ShareSDK.initSDK(mMainActivity);
		mFragmentManager = getActivity().getFragmentManager();
		// sinaWeibo = ShareSDK.getPlatform(mMainActivity, SinaWeibo.NAME);

		mineHeader = (ImageView) mineLayout.findViewById(R.id.mine_header);

		myTicket = (RelativeLayout) mineLayout.findViewById(R.id.my_ticket);
		myTicket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(mMainActivity, MyFilmActivity.class);
				startActivity(intent);
			}

		});
		mineInfo = (RelativeLayout) mineLayout.findViewById(R.id.mine_info);
		mineInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (mineName.getText().toString().trim().equals("立即登录")) {
					Intent intent = new Intent();
					intent.setClass(mMainActivity, LoginActivity.class);
					startActivityForResult(intent, 0);
				} else {
					Intent intent = new Intent();
					intent.setClass(mMainActivity, MyAccountActivity.class);
					startActivityForResult(intent, 0);
				}
			}

		});

		return mineLayout;
	}

	// ////////////////////////

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 0:
			// Bundle b = data.getExtras();
			// String name = b.getString("userName");
			String nickname = data.getStringExtra("nickname");
			mineName.setText(nickname);
			break;

		case 1:
			System.out.println("走这了吗？？？？？？？？？？？？？");
			
			mineName.setText("立即登录");
			break;

		default:
			break;

		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_MINE;

	}
}
