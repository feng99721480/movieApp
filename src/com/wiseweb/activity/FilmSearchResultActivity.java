package com.wiseweb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.wiseweb.fragment.adapter.FilmSearchResultListAdapter;
import com.wiseweb.movie.R;

public class FilmSearchResultActivity extends Activity {
	private View filmSearchResultBack; 
	private TextView filmSearchResultBackName;
	private ListView resultList;
	private FilmSearchResultListAdapter resultAdapter;
	private int[] images = {R.drawable.runman,R.drawable.runman};
	private String[] filmNames = {"奔跑吧，兄弟","速度与激情"};
	private String[] filmTypes = {"喜剧  爱情 ","冒险  剧情"};
	private String[] actionTimes = {"2015-04-01","2015-04-15"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_film_search_result);
		initUI();
	}
	public void initUI(){
		filmSearchResultBack = (View)findViewById(R.id.film_search_result_back);
		filmSearchResultBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				FilmSearchResultActivity.this.finish();
			}
			
		});
		filmSearchResultBackName = (TextView)findViewById(R.id.film_search_result_back_name);
		Intent intent = getIntent();
		String filmType = intent.getStringExtra("filmType");
		filmSearchResultBackName.setText(filmType);
		resultList = (ListView)findViewById(R.id.film_search_result_list);
		resultAdapter = new FilmSearchResultListAdapter(images,filmNames,filmTypes,actionTimes,this);
		resultList.setAdapter(resultAdapter);
		resultList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent();
				intent.setClass(FilmSearchResultActivity.this, FilmDetailsActivity.class);
				startActivity(intent);
			}
		});
	}
}
