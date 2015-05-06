package com.wiseweb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.wiseweb.fragment.adapter.MyFilmScoreAdapter;
import com.wiseweb.fragment.adapter.MyFilmWantAdapter;
import com.wiseweb.movie.R;

public class MyFilmActivity extends Activity {
	private RelativeLayout myFilmBack;
	private GridView iWantGrid;
	private RadioGroup myFilmRadio;
	private RadioButton iWant;
	private RadioButton iScore;
	private int[] filmImages = { R.drawable.runman, R.drawable.runman,
			R.drawable.runman, R.drawable.runman, R.drawable.runman,
			R.drawable.runman, R.drawable.runman, R.drawable.runman };
	private String[] filmNames = { "奔跑吧,兄弟", "奔跑吧,兄弟", "奔跑吧,兄弟", "奔跑吧,兄弟",
			"奔跑吧,兄弟", "奔跑吧,兄弟", "奔跑吧,兄弟", "奔跑吧,兄弟" };
	private String[] actionTimes = { "2015-02-01", "2015-02-01", "2015-02-01",
			"2015-02-01", "2015-02-01", "2015-02-01", "2015-02-01",
			"2015-02-01" };
	private float[] ratings = {1.0f,2.0f,3.0f,4.0f,5.0f,6.0f,7.0f,8.0f};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_film);
		initUI();
		MyFilmWantAdapter filmAdapter = new MyFilmWantAdapter(filmImages,
				filmNames, actionTimes, this);
		iWantGrid.setAdapter(filmAdapter);
	}

	public void initUI() {
		myFilmBack = (RelativeLayout) findViewById(R.id.my_film_title);
		myFilmBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				MyFilmActivity.this.finish();
			}

		});
		iWantGrid = (GridView) findViewById(R.id.iwant_grid);
		myFilmRadio = (RadioGroup) findViewById(R.id.my_film_radio);
		iWant = (RadioButton) findViewById(R.id.iwant);
		iScore = (RadioButton) findViewById(R.id.iscore);
		myFilmRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

				if (checkedId == iWant.getId()) {
					MyFilmWantAdapter filmAdapter = new MyFilmWantAdapter(
							filmImages, filmNames, actionTimes,
							MyFilmActivity.this);
					iWantGrid.setAdapter(filmAdapter);
				} else if (checkedId == iScore.getId()) {
					MyFilmScoreAdapter scoreAdapter = new MyFilmScoreAdapter(
							filmImages, filmNames, ratings, MyFilmActivity.this);
					iWantGrid.setAdapter(scoreAdapter);
				}
			}

		});
	}

}
