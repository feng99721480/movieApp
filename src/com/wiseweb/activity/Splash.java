package com.wiseweb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import cn.sharesdk.framework.ShareSDK;

import com.wiseweb.movie.R;
import com.wiseweb.service.CheckUpdate;
import com.wiseweb.service.LocationService;

public class Splash extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 2000; //

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash1);
		// /////////
		//
		// startService(new Intent(this, CheckUpdate.class));
		// //////
		// 启动服务
//		Intent underService = new Intent(Splash.this,
//				LocationService.class);
//		underService.putExtra("TEXT_GPS", "haha");
//		underService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		startService(underService);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				/*
				 * Intent mainIntent = new Intent(Splash.this,
				 * MainActivity.class);
				 * mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 * Splash.this.startActivity(mainIntent);
				 */
				CheckUpdate update = new CheckUpdate(Splash.this);
				update.checkUpdate();
				// 会发生泄漏
				Splash.this.finish();
			}

		}, SPLASH_DISPLAY_LENGHT);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();

	}

}
