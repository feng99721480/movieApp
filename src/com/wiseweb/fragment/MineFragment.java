package com.wiseweb.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import com.wiseweb.activity.MainActivity;
import com.wiseweb.constant.Constant;
import com.wiseweb.movie.R;

public class MineFragment extends BaseFragment {
	private MainActivity mMainActivity;
	private Button sinaLogin;
	private Button sinaLogout;
	private Platform sinaWeibo;
	private TextView mineName;
	private String userName;
	private ImageView mineHeader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View mineLayout = inflater.inflate(R.layout.mine_layout, container,
				false);
		mMainActivity = (MainActivity) getActivity();
		mineName = (TextView) mineLayout.findViewById(R.id.mine_name);
		
		SharedPreferences s = mMainActivity.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		userName = s.getString("userName", "");
		if (userName.isEmpty() == false) {
			mineName.setText(userName);
		} else {
			mineName.setText("登录");
		}
		ShareSDK.initSDK(mMainActivity);
		mFragmentManager = getActivity().getFragmentManager();
		sinaLogin = (Button) mineLayout.findViewById(R.id.sina_login);
		sinaWeibo = ShareSDK.getPlatform(mMainActivity, SinaWeibo.NAME);

		mineHeader = (ImageView) mineLayout.findViewById(R.id.mine_header);
		sinaLogout = (Button) mineLayout.findViewById(R.id.sina_logout);
		sinaLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				// sinaWeibo.getDb().getUserId();
				sinaWeibo = ShareSDK.getPlatform(SinaWeibo.NAME);
				// weibo.getDb().getUserId(); //获取授权用户的userid
				sinaWeibo.SSOSetting(false); // 使用SSO方式授权
				sinaWeibo.authorize();
				sinaWeibo.showUser(null); // 执行登录，登录后在回调里面获取用户资料
				userName = sinaWeibo.getDb().getUserName(); // 获取授权用户的用户名
				mineName.setText(userName);
				

				// 将用户名，密码保存到sharedpreference里
				SharedPreferences user = getActivity().getSharedPreferences(
						"user", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = user.edit();
				editor.putString("userName", userName);
				// editor.putString("userPass", arg1)
				editor.commit();
				// 不再使用sso授权
				// weibo.SSOSetting(true);
//				final Handler handler = new Handler() {
//
//					@Override
//					public void handleMessage(Message msg) {
//						// TODO Auto-generated method stub
//						//super.handleMessage(msg);
//						if (msg.what == 0x123) {
//							updateUser(msg.obj.toString());
//						}
//					}
//
//				};
//				new Thread(){
//
//					@Override
//					public void run() {
//						super.run();
//						Message msg = new Message();
//						msg.what = 0x123;
//						msg.obj = userName;
//						handler.handleMessage(msg);
//					}
//					
//				}.start();
			}

		});
		
		sinaLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sinaWeibo.isValid()) {
					sinaWeibo.removeAccount();
					SharedPreferences s = mMainActivity.getSharedPreferences(
							"user", Context.MODE_PRIVATE);
					s.edit().clear().commit();

					mineName.setText("登录");
					sinaLogin.setVisibility(View.VISIBLE);
				}
			}

		});

		return mineLayout;
	}

	// ////////////////////////

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_MINE;

	}
}
