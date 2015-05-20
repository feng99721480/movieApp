package com.wiseweb.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

import com.wiseweb.movie.R;

public class LoginActivity extends Activity {
	private RelativeLayout loginTitleBack;
	private ImageView sinaLogin;
	private Button logout;
	private Platform sinaWeibo;
	private String userName;
	private Intent intent;
	private TextView registerTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initUI();
		ShareSDK.initSDK(this);
		sinaWeibo = ShareSDK.getPlatform(this, SinaWeibo.NAME);
	}
	public void initUI(){
		registerTv = (TextView) findViewById(R.id.register_tv);
		//跳转注册界面
		registerTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
		loginTitleBack = (RelativeLayout)findViewById(R.id.login_title_back);
		loginTitleBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				intent = new Intent();
				intent.putExtra("userName","登录");
				setResult(1,intent);
				LoginActivity.this.finish();
			}
			
		});
		sinaLogin = (ImageView)findViewById(R.id.sina_login);
		sinaLogin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// sinaWeibo.getDb().getUserId();
				sinaWeibo = ShareSDK.getPlatform(SinaWeibo.NAME);
				// weibo.getDb().getUserId(); //获取授权用户的userid
				sinaWeibo.SSOSetting(false); // 使用SSO方式授权
				sinaWeibo.authorize();
				sinaWeibo.showUser(null); // 执行登录，登录后在回调里面获取用户资料
				userName = sinaWeibo.getDb().getUserName(); // 获取授权用户的用户名

				// 将用户名，密码保存到sharedpreference里
				SharedPreferences user = getSharedPreferences(
						"user", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = user.edit();
				editor.putString("userName", userName);
				// editor.putString("userPass", arg1)
				editor.commit();
				// 不再使用sso授权
				// weibo.SSOSetting(true);
				
				//sinaLogin.setVisibility(View.GONE);
				intent = new Intent();
				intent.putExtra("userName", userName);
				setResult(1, intent);
				finish();
			}
			
		});
		logout = (Button)findViewById(R.id.logout);
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (sinaWeibo.isValid()) {
					sinaWeibo.removeAccount();
					SharedPreferences s = getSharedPreferences(
							"user", Context.MODE_PRIVATE);
					s.edit().clear().commit();
//					sinaLogin.setVisibility(View.VISIBLE);
					intent = new Intent();
					intent.putExtra("userName","登录");
					setResult(1,intent);
					finish();
				}
			}

		});
	}
}
