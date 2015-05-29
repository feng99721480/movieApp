package com.wiseweb.activity;

import com.wiseweb.movie.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class FindPasswordActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_password);
	}
}
