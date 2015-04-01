package com.wiseweb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.wiseweb.movie.R;

public class CinemaDetailActivity extends Activity {
	private View cinemaDetailBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cinema_detail);
		initUI();
	}
	void initUI(){
		cinemaDetailBack = (View)findViewById(R.id.cinema_detail_back);
		cinemaDetailBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				finish();
			}
			
		});
	}
}
