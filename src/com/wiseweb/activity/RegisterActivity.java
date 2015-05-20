package com.wiseweb.activity;

import com.wiseweb.movie.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

public class RegisterActivity extends Activity {
	private RelativeLayout mRegisterTitleBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout);
		init();
	}

	private void init() {
		mRegisterTitleBack = (RelativeLayout) findViewById(R.id.register_title_back);
		mRegisterTitleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
}
